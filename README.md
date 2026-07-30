# Smart Duplicate Detector (SDD)

<div align="center">

# Smart Duplicate Detector

**AST-powered duplicate code detection for Java projects**

Detect duplicate and highly similar methods before they become technical debt.

[![Website](https://img.shields.io/badge/Website-Live-success?style=for-the-badge)](https://smart-duplicate-detector.vercel.app/)
[![Java 17](https://img.shields.io/badge/Java-17-orange?style=for-the-badge)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

**Website:** https://smart-duplicate-detector.vercel.app/

</div>

---

## Overview

Smart Duplicate Detector (SDD) is an AST-powered static analysis tool that scans Java codebases to detect duplicate or highly similar methods before they become technical debt.

Unlike traditional duplicate detection tools that rely on text or token matching, SDD analyzes the structure of Java methods to identify duplicated business logic while minimizing false positives.

As projects grow, duplicate implementations often appear through copy-paste programming, AI-assisted code generation, or independent implementations of the same functionality. Over time these methods diverge, leading to inconsistent bug fixes and increased maintenance costs.

Smart Duplicate Detector compares every method in a project and reports duplicate pairs with:

- Similarity score
- File location
- Line number
- Method name

---

## Features

- Global CLI distributed through NPM
- Modern desktop GUI built with Java Swing
- Dark theme powered by FlatLaf
- AST-based Java method extraction
- Weighted Levenshtein similarity scoring
- Configurable similarity threshold
- Project-wide duplicate scanning
- Background processing using `SwingWorker`
- Robust exception handling
- Console reporting

---

## How It Works

Traditional duplicate detectors typically rely on raw string matching or token comparison. This often produces false positives by flagging imports, getters, setters, or formatting differences.

Smart Duplicate Detector follows a two-stage semantic analysis pipeline.

### 1. AST Parsing

Using JavaParser, the source code is parsed into an Abstract Syntax Tree (AST).

Only meaningful Java methods are extracted while ignoring:

- Imports
- Package declarations
- Fields
- Constructors
- Boilerplate code

This produces a clean collection of methods for comparison.

### 2. Similarity Analysis

Each extracted method is compared against every other method using a customized Weighted Levenshtein Similarity algorithm.

The algorithm measures structural similarity while remaining resilient to:

- Whitespace differences
- Formatting changes
- Minor variable renaming

The final output is a similarity percentage representing how closely two methods match.

---

## Architecture

```text
smart-duplicate-detector/
│
├── package.json
├── index.js
│
├── model/
│   ├── MethodModel
│   └── DuplicatePair
│
├── core/
│   ├── ProjectScanner
│   ├── AstMethodParser
│   └── SimilarityAlgorithm
│
├── gui/
│   └── MainFrame
│
├── report/
│   ├── AbstractReport
│   └── ConsoleReport
│
├── exceptions/
│   ├── InvalidProjectPathException
│   └── NoJavaFilesFoundException
│
├── api/
│   └── ApiServer
│
├── cli/
│   └── CliRunner
│
└── resources/
```

---

## Tech Stack

| Layer | Technology |
|--------|------------|
| Language | Java 17 |
| Parsing Engine | JavaParser |
| Similarity Engine | Weighted Levenshtein |
| Desktop UI | Java Swing + FlatLaf |
| REST API | Javalin |
| JSON Processing | Jackson |
| Build Tool | Maven |
| CLI Distribution | Node.js + NPM |
| Testing | JUnit 5 |
| Documentation | Next.js 16 |
| Styling | Tailwind CSS |
| Deployment | Vercel |

---

## Getting Started

### Prerequisites

- Java 17 or later
- Node.js
- NPM

### Install Globally

```bash
npm install -g smart-duplicate-detector
```

### Launch the Desktop Application

```bash
sdd
```

### Run a CLI Scan

```bash
sdd --path ./path/to/java/project --threshold 0.80
```

---

## Build From Source

### Clone the Repository

```bash
git clone https://github.com/fetehadin/smart-duplicate-detector.git

cd smart-duplicate-detector
```

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/smart-duplicate-detector.jar
```

### Test the Global CLI

```bash
npm link
```

---

## Example Output

```text
Scanning project files...

Found 27 valid methods.

Running Levenshtein comparison engine...

DUPLICATE FOUND (92% similar)

EngineTest.java:14
→ testIdenticalSequenceLevenshteinScore

EngineTest.java:29
→ testDifferentSequenceLevenshteinScore

------------------------------------------------

DUPLICATE FOUND (100% similar)

TestScenarios.java:6
→ calculateTaxUSA

TestScenarios.java:14
→ calculateTaxUK

------------------------------------------------

Scan complete.

2 duplicate pairs found.
```

---

## Why Smart Duplicate Detector?

- Detects duplicate business logic instead of simple text matches
- AST-aware parsing significantly reduces false positives
- Configurable similarity threshold
- Available as both a desktop application and CLI
- Distributed through NPM for easy installation
- Built for future IDE and CI/CD integration

---

## Roadmap

- GitHub repository scanning (`sdd --url`)
- Parallel comparison engine for large codebases
- VS Code extension
- GitHub Actions integration
- HTML and PDF report generation
- AI-assisted duplicate recommendations
- Incremental project scanning
- Multi-language support

---

## License

This project is licensed under the MIT License.

See the `LICENSE` file for details.

---

<div align="center">

If you find Smart Duplicate Detector useful, consider giving the repository a star.

https://smart-duplicate-detector.vercel.app/

</div>