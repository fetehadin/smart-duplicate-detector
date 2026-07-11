# Smart Duplicate Detector

A Java desktop tool that scans a codebase and flags methods with duplicate or highly similar logic before they become technical debt.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Build](https://img.shields.io/badge/build-Maven-blue)
![License](https://img.shields.io/badge/license-MIT-green)
![Status](https://img.shields.io/badge/status-in%20development-yellow)

## Overview

Developers often reuse or regenerate business logic without realizing an equivalent method already exists elsewhere in the codebase. Over time these duplicates drift apart, and bugs get fixed in one copy but not the other. Smart Duplicate Detector scans a project, compares every method, and reports matches above a similarity threshold — with file, line number, and score — so duplication is caught before it's merged.

## Features

- Recursive scan of a Java project for `.java` files
- Structural extraction of every method into a comparable model
- Token-based similarity scoring
- Swing GUI and command-line entry points, same engine underneath
- Custom exception handling for invalid or empty project paths

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Build | Maven |
| Parsing | JavaParser |
| GUI | Java Swing |
| Testing | JUnit 5 |
| Docs site | HTML / CSS / JavaScript |

## Architecture

```
smart-duplicate-detector/
├── model/        MethodModel, DuplicatePair
├── core/         ProjectScanner, MethodParser, SimilarityAlgorithm, JaccardSimilarity, DuplicateDetector
├── report/       AbstractReport, ConsoleReport
├── exceptions/   InvalidProjectPathException, NoJavaFilesFoundException
├── gui/          MainFrame, ResultsPanel
└── cli/          CliRunner
```

Both entry points feed the same `ProjectScanner → MethodParser → DuplicateDetector` pipeline; only input/output differs.

## Getting Started

**Prerequisites:** Java 17+, Maven 3.8+

```bash
git clone https://github.com/<your-username>/smart-duplicate-detector.git
cd smart-duplicate-detector
mvn clean package
```

**Run — GUI:**
```bash
java -jar target/smart-duplicate-detector.jar
```

**Run — CLI:**
```bash
java -jar target/smart-duplicate-detector.jar --path ./path/to/project
```

## Usage

```
Scanning ./src ...
Found 42 methods across 8 files.

DUPLICATE FOUND (87% similar)
→ calculateDiscount(Order)     OrderService.java:42
→ applyPriceReduction(Order)   PricingService.java:118

Scan complete. 1 duplicate pair found.
```

## Team

Fetehadin Negash · Peniel Melaku · Anwar Ibrahim · Yonathan

*(Task assignments to be added.)*

## License

MIT — see [`LICENSE`](LICENSE)