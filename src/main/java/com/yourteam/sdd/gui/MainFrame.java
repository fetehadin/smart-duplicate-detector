package com.yourteam.sdd.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import com.yourteam.sdd.core.AstMethodParser;
import com.yourteam.sdd.core.DuplicateDetector;
import com.yourteam.sdd.core.LevenshteinSimilarity;
import com.yourteam.sdd.core.ProjectScanner;
import com.yourteam.sdd.core.SimilarityAlgorithm;
import com.yourteam.sdd.model.DuplicatePair;
import com.yourteam.sdd.model.MethodModel;
import java.util.List;

public class MainFrame extends JFrame {

    private JTextField pathField;
    private JSlider thresholdSlider;
    private JLabel thresholdValueLabel;
    private JTextArea resultArea;
    private JButton scanButton;

    public MainFrame() {
        setTitle("Smart Duplicate Detector");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        initUI();
    }

    private void initUI() {
        // --- TOP PANEL (Controls) ---
        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Directory Selector
        JPanel dirPanel = new JPanel(new BorderLayout(10, 0));
        JLabel pathLabel = new JLabel("Project Path:");
        pathLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dirPanel.add(pathLabel, BorderLayout.WEST);
        
        pathField = new JTextField();
        pathField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dirPanel.add(pathField, BorderLayout.CENTER);
        
        JButton browseButton = new JButton("Browse...");
        browseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        browseButton.addActionListener(e -> chooseDirectory());
        dirPanel.add(browseButton, BorderLayout.EAST);

        // Threshold Slider
        JPanel sliderPanel = new JPanel(new BorderLayout(15, 0));
        sliderPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        thresholdValueLabel = new JLabel("Similarity: 80%");
        thresholdValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sliderPanel.add(thresholdValueLabel, BorderLayout.WEST);

        thresholdSlider = new JSlider(50, 100, 80);
        thresholdSlider.setMajorTickSpacing(10);
        thresholdSlider.setPaintTicks(true);
        thresholdSlider.setPaintLabels(true);
        thresholdSlider.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        thresholdSlider.addChangeListener(e -> 
            thresholdValueLabel.setText("Similarity: " + thresholdSlider.getValue() + "%")
        );
        sliderPanel.add(thresholdSlider, BorderLayout.CENTER);

        // Scan Button
        scanButton = new JButton("Run Scan");
        scanButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        scanButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        scanButton.addActionListener(e -> runScan());
        sliderPanel.add(scanButton, BorderLayout.EAST);

        topPanel.add(dirPanel, BorderLayout.NORTH);
        topPanel.add(sliderPanel, BorderLayout.CENTER);

        // --- CENTER PANEL (Results) ---
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14)); // Standard modern code font
        resultArea.setMargin(new Insets(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void chooseDirectory() {
        // Just standard, clean code. FlatLaf handles making it look modern automatically.
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(new File(".")); 
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            pathField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void runScan() {
        String path = pathField.getText();
        if (path == null || path.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a directory to scan.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double threshold = thresholdSlider.getValue() / 100.0;
        scanButton.setEnabled(false);
        resultArea.setText("Initializing static analysis engine...\n");
        resultArea.append("Target: " + path + "\n");
        resultArea.append("Threshold: " + threshold + "\n");
        resultArea.append("--------------------------------------------------\n\n");

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    publish("Parsing AST nodes...\n");
                    ProjectScanner scanner = new ProjectScanner();
                    AstMethodParser parser = new AstMethodParser();
                    
                    List<File> files = scanner.scan(path);
                    List<MethodModel> methods = parser.parseFiles(files);
                    
                    publish("Identified " + methods.size() + " method signatures.\n");
                    publish("Executing Levenshtein structural comparison...\n\n");

                    SimilarityAlgorithm algorithm = new LevenshteinSimilarity();
                    DuplicateDetector detector = new DuplicateDetector(algorithm);
                    List<DuplicatePair> duplicates = detector.findDuplicates(methods, threshold);

                    if (duplicates.isEmpty()) {
                        publish("SUCCESS: No structural duplicates detected.\n");
                    } else {
                        for (DuplicatePair result : duplicates) {
                            String matchPercent = String.format("%.0f", result.getSimilarityScore() * 100);
                            publish(String.format("[WARNING] %s%% Structural Similarity Detected\n", matchPercent));
                            publish("  └─ " + result.getFirst().getSignatureLabel() + "\n");
                            publish("  └─ " + result.getSecond().getSignatureLabel() + "\n\n");
                        }
                        publish("Analysis complete. " + duplicates.size() + " vulnerabilities found.\n");
                    }
                } catch (Exception ex) {
                    publish("Error during execution: " + ex.getMessage() + "\n");
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String chunk : chunks) {
                    resultArea.append(chunk);
                }
            }

            @Override
            protected void done() {
                scanButton.setEnabled(true);
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        // THIS ONE LINE replaces all the hacky UI code. 
        // It injects a modern, elegant dark theme into the entire app.
        FlatDarkLaf.setup();
        
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}