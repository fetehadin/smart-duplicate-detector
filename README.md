# Smart Duplicate Detector
A Java tool that scans a codebase and flags methods with duplicate or highly similar logic before they become technical debt.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Build](https://img.shields.io/badge/build-Maven-blue)
![License](https://img.shields.io/badge/license-MIT-green)
![Status](https://img.shields.io/badge/status-in%20development-yellow)

## Overview
Developers often reuse or regenerate business logic without realizing an equivalent method already exists elsewhere in the codebase. Over time these duplicates drift apart, and bugs get fixed in one copy but not the other. Smart Duplicate Detector scans a project, compares every method, and reports matches above a similarity threshold — with file, line number, and score — so duplication is caught before it's merged.

## Features
- Recursive scan of a Java project for `.java` files
- Structural extraction of every method into a comparable token model (parameter types, return type, identifiers, method calls)
- Token-based similarity scoring via a pluggable `SimilarityAlgorithm` (Jaccard index implemented)
- Web app: REST API backend + browser-based frontend for entering a project path and viewing results in a table
- Command-line entry point, sharing the same detection engine as the web app
- Custom exception handling for invalid or empty project paths
- *(Planned)* Scan a public GitHub repository directly by URL instead of a local path

## Tech Stack
| Layer | Technology |
|---|---|
| Language | Java 17 |
| Build | Maven |
| Parsing | JavaParser |
| Backend / API | Javalin (REST, JSON via Jackson) |
| Frontend | HTML / CSS / vanilla JavaScript |
| Testing | JUnit 5 |

## Architecture
```
smart-duplicate-detector/
├── model/        MethodModel, DuplicatePair
├── core/         ProjectScanner, MethodParser, SimilarityAlgorithm, JaccardSimilarity, DuplicateDetector
├── report/       AbstractReport, ConsoleReport
├── exceptions/   InvalidProjectPathException, NoJavaFilesFoundException
├── api/          ApiServer (REST endpoints + serves the web frontend)
├── cli/          CliRunner
└── resources/public/   index.html, styles.css, app.js  (web frontend, static)
```
Both the web app and the CLI feed the same `ProjectScanner → MethodParser → DuplicateDetector` pipeline; only input/output differs.

## Getting Started
**Prerequisites:** Java 17+, Maven 3.8+

```bash
git clone https://github.com/<your-username>/smart-duplicate-detector.git
cd smart-duplicate-detector
mvn clean package
```

**Run — Web app (backend + frontend):**
```bash
java -jar target/smart-duplicate-detector.jar
```
Then open **http://localhost:7070** in a browser, enter a project path and a similarity threshold, and click Scan.

**Run — CLI:**
```bash
java -cp target/smart-duplicate-detector.jar com.yourteam.sdd.cli.CliRunner --path ./path/to/project --threshold 0.75
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

## Known Limitations
- Comparison is all-pairs (O(n²)) — fine for a single project, not intended for large monorepos.
- Jaccard similarity over identifier/call tokens can produce false positives for unrelated methods that share common boilerplate (`equals`, `toString`, loop bodies). A weighting scheme (e.g. down-weighting very common tokens) is a planned improvement.
- Only structural/token similarity is considered — no control-flow or semantic analysis.
- Currently scans a path accessible to the machine running the server; GitHub-repo scanning (clone + scan by URL) is planned to make the web app usable without local file access.

## Team
Fetehadin Negash · Peniel Melaku · Anwar Ibrahim · Yonathan Yilma

**Task assignments:**
| Member | Area |
|---|---|
| Fetehadin Negash | Core pipeline (`ProjectScanner`, `MethodParser`) |
| Peniel Melaku | Similarity engine (`SimilarityAlgorithm`, `JaccardSimilarity`, `DuplicateDetector`) |
| Anwar Ibrahim | Backend API (`ApiServer`) & CLI |
| Yonathan Yilma | Web frontend (`index.html`, `app.js`, `styles.css`) |

*(Adjust to match who's actually doing what.)*

## License
MIT — see [`LICENSE`](LICENSE)
