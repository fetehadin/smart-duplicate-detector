package com.yourteam.sdd.gui;

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
import java.io.File;
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
        setLocationRelativeTo(null); // Centers the window on screen

        // Try to use the native OS look and feel (makes it look like a modern Mac/Windows app)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback to default if native fails
        }

        initUI();
    }

    private void initUI() {
        // --- TOP PANEL (Controls) ---
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Directory Selector
        JPanel dirPanel = new JPanel(new BorderLayout(5, 0));
        dirPanel.add(new JLabel("Project Path:"), BorderLayout.WEST);
        pathField = new JTextField();
        dirPanel.add(pathField, BorderLayout.CENTER);
        
        JButton browseButton = new JButton("Browse...");
        browseButton.addActionListener(e -> chooseDirectory());
        dirPanel.add(browseButton, BorderLayout.EAST);

        // Threshold Slider (50% to 100%, defaults to 80%)
        JPanel sliderPanel = new JPanel(new BorderLayout(5, 0));
        sliderPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        thresholdValueLabel = new JLabel("Similarity Threshold: 80%");
        sliderPanel.add(thresholdValueLabel, BorderLayout.WEST);

        thresholdSlider = new JSlider(50, 100, 80);
        thresholdSlider.setMajorTickSpacing(10);
        thresholdSlider.setMinorTickSpacing(5);
        thresholdSlider.setPaintTicks(true);
        thresholdSlider.setPaintLabels(true);
        
        // Update label dynamically as user drags the slider
        thresholdSlider.addChangeListener(e -> 
            thresholdValueLabel.setText("Similarity Threshold: " + thresholdSlider.getValue() + "%")
        );
        sliderPanel.add(thresholdSlider, BorderLayout.CENTER);

        // Scan Button
        scanButton = new JButton("Run Scan");
        scanButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        scanButton.addActionListener(e -> runScan());
        sliderPanel.add(scanButton, BorderLayout.EAST);

        topPanel.add(dirPanel, BorderLayout.NORTH);
        topPanel.add(sliderPanel, BorderLayout.CENTER);

        // --- CENTER PANEL (Results) ---
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13)); // Monospaced for code-like output
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add to Frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void chooseDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        // Default to current directory
        chooser.setCurrentDirectory(new File(".")); 
        
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
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

        // Disable button while scanning so the user doesn't click it multiple times
        scanButton.setEnabled(false);
        resultArea.setText("Scanning path: " + path + "\n");
        resultArea.append("Using threshold: " + threshold + "\n");
        resultArea.append("=========================================\n\n");

        // SwingWorker keeps the GUI from freezing while the engine runs
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    publish("Scanning project files...\n");
                    
                    ProjectScanner scanner = new ProjectScanner();
                    AstMethodParser parser = new AstMethodParser();
                    
                    List<File> files = scanner.scan(path);
                    List<MethodModel> methods = parser.parseFiles(files);
                    
                    publish("Found " + methods.size() + " valid methods.\n");
                    publish("Running Levenshtein comparison engine...\n\n");

                    SimilarityAlgorithm algorithm = new LevenshteinSimilarity();
                    DuplicateDetector detector = new DuplicateDetector(algorithm);
                    List<DuplicatePair> duplicates = detector.findDuplicates(methods, threshold);

                    if (duplicates.isEmpty()) {
                        publish("✅ No duplicates found! Your codebase is clean.\n");
                    } else {
                        for (DuplicatePair result : duplicates) {
                            String matchPercent = String.format("%.0f", result.getSimilarityScore() * 100);
                            publish(String.format("DUPLICATE FOUND (%s%% similar)\n", matchPercent));
                            publish(" → " + result.getFirst().getSignatureLabel() + "\n");
                            publish(" → " + result.getSecond().getSignatureLabel() + "\n\n");
                        }
                        publish("Scan complete. " + duplicates.size() + " duplicate pairs found.\n");
                    }
                } catch (Exception ex) {
                    publish("Error during scan: " + ex.getMessage() + "\n");
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

    // Main method to launch the Swing GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}