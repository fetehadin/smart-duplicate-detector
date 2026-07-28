# Smart Duplicate Detector

![Smart Duplicate Detector GUI](image.png)

## Overview

A Java tool that scans a codebase and flags methods with duplicate or highly similar logic before they become technical debt.

Developers often reuse or regenerate business logic without realizing an equivalent method already exists elsewhere in the codebase. Over time these duplicates drift apart, and bugs get fixed in one copy but not the other. Smart Duplicate Detector scans a project, compares every method, and reports matches above a similarity threshold—with file, line number, and score—so duplication is caught before it's merged.

---

## The "Why": Technical Approach

Traditional duplicate detectors often rely on raw string matching or tokenization. This leads to a massive amount of "false positive" noise—flagging standard boilerplate, imports, or basic getters and setters as duplicated code.

**Smart Duplicate Detector** solves this using a two-step semantic approach:

1. **AST Method Parsing:** By leveraging JavaParser, the engine parses the codebase into an Abstract Syntax Tree (AST) and isolates functional methods. It completely ignores class-level boilerplate, fields, and imports.

2. **Weighted Levenshtein Algorithm:** Instead of basic string comparison, SDD compares the logic and structure of the methods using a custom Weighted Levenshtein distance algorithm. This accurately calculates similarity percentages, ensuring that only genuine logic clones are flagged while ignoring minor whitespace or variable renaming differences.

---

## Features

- **Global CLI Tool:** Wrapped in NPM for a frictionless 1-step installation and a global `sdd` command.
- **Modern Desktop App:** Sleek, dark-themed Java Swing GUI powered by FlatLaf with real-time background scanning (`SwingWorker`).
- **High Precision:** Structural extraction of every method into a comparable AST model.
- **Smart Scoring:** Highly accurate similarity scoring via `LevenshteinSimilarity`.
- Custom exception handling for invalid or empty project paths.
- *(Planned)* Scan a public GitHub repository directly by URL instead of a local path.

---

## Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | Java 17 |
| **Build & Distribution** | Maven, Node.js / NPM (CLI Wrapper) |
| **Parsing** | JavaParser |
| **Desktop GUI** | Java Swing + FlatLaf (Modern Dark Theme) |
| **Backend / API** | Javalin (REST, JSON via Jackson) |
| **Testing** | JUnit 5 |

---

## Architecture

```text
smart-duplicate-detector/
├── package.json & index.js   # NPM Bridge for global 'sdd' command
├── model/                    # MethodModel, DuplicatePair
├── core/                     # ProjectScanner, AstMethodParser, SimilarityAlgorithm...
├── gui/                      # MainFrame (Swing Desktop App)
├── report/                   # AbstractReport, ConsoleReport
├── exceptions/               # InvalidProjectPathException, NoJavaFilesFoundException
├── api/                      # ApiServer (REST endpoints)
├── cli/                      # CliRunner
└── resources/                # (web frontend / static files)
```

---

## 🚀 Getting Started (End Users)

The easiest way to use Smart Duplicate Detector is via our NPM package, which automatically links the `sdd` command to your system.

### Prerequisites

- Java 17+
- Node.js & NPM

### Install Globally

```bash
npm install -g smart-duplicate-detector
```

### Launch the UI

To launch the modern FlatLaf desktop dashboard, open your terminal anywhere and type:

```bash
sdd
```

### Run via CLI (Headless)

To run a silent scan in your terminal without opening the GUI:

```bash
sdd --path ./path/to/project --threshold 0.80
```

---

## 🛠️ Developer Setup (Build from Source)

If you want to modify the Java engine or build the Fat-JAR yourself:

### 1. Clone the Repository

```bash
git clone https://github.com/fetehadin/smart-duplicate-detector.git
cd smart-duplicate-detector
```

### 2. Build the Engine

```bash
mvn clean package
```

### 3. Run Locally

```bash
java -jar target/smart-duplicate-detector.jar
```

*Alternatively, run `npm link` in the root folder to test the global `sdd` command locally!*

---

## Usage Example (CLI & GUI Output)

```text
Scanning project files...
Found 27 valid methods.
Running Levenshtein comparison engine...

DUPLICATE FOUND (92% similar)
 → EngineTest.java:14 -> testIdenticalSequenceLevenshteinScore
 → EngineTest.java:29 -> testDifferentSequenceLevenshteinScore

DUPLICATE FOUND (100% similar)
 → TestScenarios.java:6 -> calculateTaxUSA
 → TestScenarios.java:14 -> calculateTaxUK

Scan complete. 2 duplicate pairs found.
```

---

## Known Limitations

- Comparison is all-pairs (`O(n²)`): Fine for a single project, but not intended for massive, multi-gigabyte monorepos.
- Only structural/token similarity is considered: No control-flow or semantic analysis yet.
- GitHub-repo scanning (clone + scan by URL) is planned for a future release.

---

## License

MIT — see `LICENSE`.