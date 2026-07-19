# Smart Duplicate Detector — Master Project Document

**Smart Duplicate Detector — Master Project Document**  
**Course:** Object-Oriented Programming with Java

**Status:** Instructor-approved

**Document type:** Single source of truth — SRS, Architecture Guide, Development Roadmap, and Team Handbook combined  
_**How to use this document:**_ _ Section 19 (Scope Definition) is the most important section in this entire document. When in doubt about whether something belongs in the graded submission, that section is the answer, not this note._  
![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OQQmAABRAsSeYxKS/kJkED6bwYAVvImwJtszMVu0BAPAXx1rd1fn1BACA164HHDwF+DpPyKwAAAAASUVORK5CYII=)  
**Table of Contents**

1. [Project Overview](#anchor-1 "#anchor-1")
2. [Problem Analysis](#anchor-2 "#anchor-2")
3. [Project Goals](#anchor-3 "#anchor-3")
4. [Features](#anchor-4 "#anchor-4")
5. [User Workflow](#anchor-5 "#anchor-5")
6. [System Architecture](#anchor-6 "#anchor-6")
7. [OOP Design](#anchor-7 "#anchor-7")
8. [Technology Stack](#anchor-8 "#anchor-8")
9. [Project Folder Structure](#anchor-9 "#anchor-9")
10. [Development Phases](#anchor-10 "#anchor-10")
11. [Sprint Planning](#anchor-11 "#anchor-11")
12. [Implementation Guide](#anchor-12 "#anchor-12")
13. [Coding Standards](#anchor-13 "#anchor-13")
14. [Git Workflow](#anchor-14 "#anchor-14")
15. [Team Responsibilities](#anchor-15 "#anchor-15")
16. [Testing Strategy](#anchor-16 "#anchor-16")
17. [Risks](#anchor-17 "#anchor-17")
18. [Future Improvements](#anchor-18 "#anchor-18")
19. [Scope Definition](#anchor-19 "#anchor-19")
20. [Deliverables](#anchor-20 "#anchor-20")
21. [Final Architecture Diagram](#anchor-21 "#anchor-21")
22. [Appendix](#anchor-22 "#anchor-22")  
    ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OMQ2AABAAsSNhZscZXlheJwqQgQU2QtIq6DIze3UGAMBf3Gu1VcfXEwAAXrseop8EQrmJduIAAAAASUVORK5CYII=)  
    **1. Project Overview**  
    **Project name:** Smart Duplicate Detector  
    **Vision:** A Java codebase where duplicate logic is caught before it's merged, not discovered months later as a bug that was fixed in one copy and forgotten in another.  
    **Mission:** Build a tool, fully understood and fully owned by the team, that scans a Java project, scores how similar each method is to every other method, and gives the developer a way to act on what it finds — not just a report to read.  
    **Problem statement:** Developers frequently reuse or regenerate business logic — manually or through AI coding assistants — without realizing an equivalent method already exists elsewhere in the codebase. Over time these duplicates drift apart: a bug gets fixed in one copy, not the other.  
    **Why this project exists:** This is a real, documented, and currently worsening problem in software engineering (see Section 2), it has well-understood prior art to learn from (Section 2), and it maps cleanly onto every module of this course's syllabus (Section 7).  
    **Target users:** Java developers and code reviewers checking a pull request before merge.  
    **Expected outcome:** A working, fully-explainable Java application (command line and desktop GUI) that detects duplicate methods with a percentage similarity score, plus a documented, realistic roadmap for extending it into a web and editor-integrated tool.  
    ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANElEQVR4nO3OQQmAABRAsSdYxKY/jMFMIZ7ECt5E2BJsmZmt2gMA4C+Otbqr8+sJAACvXQ85QgYXd/O+eQAAAABJRU5ErkJggg==)  
    **2. Problem Analysis**  
    **What developers currently face**  
    Code duplication increases maintenance cost because a change to shared logic must be made in every copy, and if a copy is missed, the two versions silently drift apart. This is a long-documented category of technical debt, and it is getting worse: rising use of AI coding assistants is measurably increasing how much duplicate code enters real projects, because generating new code is now faster than searching for and reusing existing code.

**How this project solves the gap**  
Smart Duplicate Detector scores every method pair as a percentage, tied to a specific, named method — not an arbitrary line range — and gives the developer an explicit way to record what they decided to do about each match.  
**Real-world example**  
// OrderService.java, line 42  
 public double calculateDiscount(Order order) {  
     if (order.getTotal() > 100) {  
         return order.getTotal() \* 0.1;  
     }  
     return 0;  
 }

   
 // PricingService.java, line 118 — written 6 months later, different name  
 public double applyPriceReduction(Order o) {  
     if (o.getTotal() > 100) {  
         return o.getTotal() \* 0.1;  
     }  
     return 0;  
 }

   
A text search for calculateDiscount will never surface applyPriceReduction. A tool that understands method structure will.  
![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OsQ1AABRAwSdRaPXGMOCv7WkPK+hEcjfBLTNzVFcAAPzFvVZbdX49AQDgtf0BSpoDXv5TGXgAAAAASUVORK5CYII=)  
**3. Project Goals**  
**Primary goals (Phase 1–2)**

- Detect duplicate/similar Java methods with a percentage similarity score
- Provide both a command-line and a Swing desktop interface
- Demonstrate every OOP principle in the course syllabus, fully self-implemented
- Handle invalid input gracefully via custom exceptions  
  **Secondary goals (attempted only if Phase 1–2 is solid)**
- A REST API layer exposing the same engine (Phase 3)
- A documentation + live-demo website (Phase 4)  
  **Future goals (Version 2)**
- A VS Code extension with an interactive review workflow (Phase 5)
- A "keep / mark for replacement" decision system (Command pattern)
- Additional similarity algorithms (structural/AST-based comparison)  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANklEQVR4nO3OMQ2AABAAsSNBCkJfFEIwwIgHRiywEZJWQZeZ2ao9AAD+4lyruzq+ngAA8Nr1AOHsBegrsOrIAAAAAElFTkSuQmCC)  
  **4. Features**  
  **Core Features**  
  _**F1 — Project Scanner**_
- **Description:** Recursively finds every .java file under a given folder.
- **User story:** As a developer, I want to point the tool at my project root so I don't have to list files manually.
- **Inputs:** A folder path (string)
- **Outputs:** A list of .java file paths
- **OOP concepts used:** Encapsulation (internal file-walking logic hidden behind a simple method), custom exceptions for invalid paths
- **Complexity:** Low
- **Priority:** High  
  _**F2 — Method Extractor**_
- **Description:** Parses each file and extracts every method into a structured, comparable object.
- **User story:** As the detection engine, I need every method represented consistently so I can compare any two of them the same way.
- **Inputs:** File contents
- **Outputs:** A list of MethodModel objects (name, file, line number, body tokens)
- **OOP concepts used:** Encapsulation (immutable model with private fields)
- **Complexity:** Medium
- **Priority:** High  
  _**F3 — Similarity Scorer**_
- **Description:** Tokenizes each method body, normalizes variable names, and scores every method pair using Jaccard similarity (shared tokens ÷ total distinct tokens).
- **User story:** As a developer, I want a percentage score, not just a yes/no duplicate flag, so I can judge how serious a match is.
- **Inputs:** Two MethodModel objects
- **Outputs:** A similarity score (0–100%)
- **OOP concepts used:** Abstraction (SimilarityAlgorithm interface), Polymorphism (swappable implementation)
- **Complexity:** Medium
- **Priority:** High  
  _**F4 — Duplicate Detector**_
- **Description:** Compares every method against every other method, using the similarity scorer, and keeps pairs above a configurable threshold.
- **User story:** As a developer, I only want to see matches that are actually worth my attention, not every faint similarity.
- **Inputs:** List of MethodModel objects, a threshold value
- **Outputs:** List of DuplicatePair objects
- **OOP concepts used:** Composition (uses SimilarityAlgorithm without knowing its implementation)
- **Complexity:** Medium
- **Priority:** High  
  _**F5 — Reporting**_
- **Description:** Formats and prints the list of duplicate pairs.
- **User story:** As a developer, I want to see the file, line number, and score for every match.
- **Inputs:** List of DuplicatePair objects
- **Outputs:** Console text (Phase 2); other formats are future work
- **OOP concepts used:** Inheritance (AbstractReport → ConsoleReport)
- **Complexity:** Low
- **Priority:** High  
  _**F6 — Error Handling**_
- **Description:** Raises specific, self-defined exceptions for invalid or empty project paths instead of crashing.
- **User story:** As a developer, if I point the tool at the wrong folder, I want a clear message, not a stack trace.
- **Inputs:** An invalid or empty folder path
- **Outputs:** A caught, user-readable error message
- **OOP concepts used:** Custom checked exceptions (InvalidProjectPathException, NoJavaFilesFoundException)
- **Complexity:** Low
- **Priority:** High  
  **Optional Features (attempted if time allows within Phase 1–2)**  
  _**O1 — Swing Desktop GUI**_
- **Description:** Folder picker, Scan button, results table — same engine as the CLI.
- **User story:** As a developer who prefers a visual tool, I want to click through a scan instead of using a terminal.
- **Inputs:** Folder selected via JFileChooser
- **Outputs:** A JTable of duplicate pairs
- **OOP concepts used:** Components, layout managers, delegation event model, listeners (course Module 4)
- **Complexity:** Medium
- **Priority:** High _(required by the course syllabus, not truly optional — listed here because it is a separate feature from the core engine)_  
  **Future Features (explicitly out of this submission — see Section 19)**  
  _**X1 — REST API (Phase 3)**_
- **Description:** A thin Javalin layer exposing the engine over HTTP so other clients can call it.
- **OOP concepts used:** None new — this is intentionally plumbing, not logic.
- **Complexity:** Medium
- **Priority:** Low (post-submission)  
  _**X2 — Documentation & Live-Demo Website (Phase 4)**_
- **Description:** Static site explaining the tool, plus a drag-and-drop page that calls the Phase 3 API for an in-browser demo.
- **Complexity:** Medium
- **Priority:** Low (post-submission)  
  _**X3 — VS Code Extension (Phase 5)**_
- **Description:** In-editor scanning and review, calling the Phase 3 API.
- **Complexity:** High
- **Priority:** Low (post-submission)  
  _**X4 — Keep / Replace Decision Workflow**_
- **Description:** Record a developer's decision per duplicate pair using the Command pattern (KeepBothDecision, MarkForReplacementDecision).
- **Complexity:** Medium
- **Priority:** Low (post-submission)  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAAM0lEQVR4nO3OUQmAQBBAwSdcjsu6HYxoDsEK/okwk2COmdnVGQAAf3GtalX76wkAAK/dDxFWBDkFf6+SAAAAAElFTkSuQmCC)  
  **5. User Workflow**  
  flowchart TD  
       A[Developer starts tool] --> B{CLI or GUI?}  
       B -->|CLI| C[Run with --path argument]  
       B -->|GUI| D[Pick folder in Swing window]  
       C --> E[Engine scans folder for .java files]  
       D --> E  
       E --> F[Extract every method]  
       F --> G[Tokenize and normalize method bodies]  
       G --> H[Score every method pair]  
       H --> I{Score above threshold?}  
       I -->|No| J[Discard pair]  
       I -->|Yes| K[Report duplicate pair]  
       K --> L[Developer reviews file, line, score]
     
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANklEQVR4nO3OQQmAABRAsSfYxZo/jVEMYQLPJrCCNxG2BFtmZquOAAD4i3Ot7mr/egIAwGvXA4rLBc059ysnAAAAAElFTkSuQmCC)  
  **6. System Architecture**  
  **High-level architecture**  
  graph TD  
       Core["Java Core Engine<br/>Scanner / Parser / Detector / Scorer"]  
       CLI["CLI<br/>Phase 2"]  
       GUI["Swing GUI<br/>Phase 2"]  
       API["REST API — Javalin<br/>Phase 3"]  
       Web["Website<br/>Phase 4"]  
       Ext["VS Code Extension<br/>Phase 5"]
     
       Core --> CLI  
       Core --> GUI  
       Core --> API  
       API --> Web  
       API --> Ext
     
  _**Note:**_ _ Every box outside the Core Engine is a thin adapter. None of them contain detection or scoring logic — that lives in exactly one place._  
  **Component diagram (Phase 1–2 scope)**  
  graph LR  
       Scanner[ProjectScanner] --> Parser[MethodParser]  
       Parser --> Model[MethodModel]  
       Model --> Detector[DuplicateDetector]  
       Detector --> Algo[SimilarityAlgorithm interface]  
       Algo --> Jaccard[JaccardSimilarity]  
       Detector --> Pair[DuplicatePair]  
       Pair --> Report[AbstractReport]  
       Report --> Console[ConsoleReport]  
       Scanner -.throws.-> Exc1[InvalidProjectPathException]  
       Scanner -.throws.-> Exc2[NoJavaFilesFoundException]
     
  **Relationships between components**
  | | | |
  | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------- | ------------------------------------------------------- |
  | **Component** | **Depends on** | **Reason** |
  | MethodParser | MethodModel | Produces model objects from parsed source |
  | DuplicateDetector | SimilarityAlgorithm, MethodModel | Uses scoring through an interface, not a concrete class |
  | ConsoleReport | AbstractReport, DuplicatePair | Inherits shared formatting logic |
  | CliRunner / MainFrame | ProjectScanner, MethodParser, DuplicateDetector, a Report | Entry points call the same pipeline |
  |   |
  | ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANklEQVR4nO3OQQmAABRAsSfYxZo/khWsYQLPJrCCNxG2BFtmZquOAAD4i3Ot7mr/egIAwGvXA4qjBdKlX6OKAAAAAElFTkSuQmCC) |
  | **7. OOP Design** |
  | **Encapsulation** |
  | MethodModel and DuplicatePair expose their data only through getters; fields are private and set once through the constructor. This prevents any part of the system from silently mutating a method's recorded data after it has been scanned. |
  | **Abstraction** |
  | SimilarityAlgorithm is an interface with a single method, compare(MethodModel a, MethodModel b): double. DuplicateDetector depends only on this interface, never on a specific scoring implementation — it does not need to know _how_ similarity is computed to use it. |
  | **Inheritance** |
  | AbstractReport holds report logic shared by every output format (a header, a footer, iterating the duplicate list); ConsoleReport extends it and supplies only the console-specific formatting. Any future report format (JSON, HTML) extends the same base class instead of duplicating shared logic. |
  | **Polymorphism** |
  | DuplicateDetector calls similarityAlgorithm.compare(...) through the interface reference. At runtime, this can resolve to JaccardSimilarity or any future implementation, without a single line of DuplicateDetector changing. |
  | **Interfaces** |
  | SimilarityAlgorithm is the only interface in Phase 1–2, deliberately — one clean, well-justified use of an interface is stronger for a course demo than several interfaces added just to check a box. |
  | **Class diagram** |
  | classDiagram |
  |      class MethodModel { |
  |          -String methodName |
  |          -String filePath |
  |          -int lineNumber |
  |          -List~~String~~ bodyTokens |
  |          +getMethodName() String |
  |          +getFilePath() String |
  |          +getLineNumber() int |
  |          +getBodyTokens() List~~String~~ |
  |      } |
  |   |
  |      class DuplicatePair { |
  |          -MethodModel first |
  |          -MethodModel second |
  |          -double similarityScore |
  |          +getFirst() MethodModel |
  |          +getSecond() MethodModel |
  |          +getSimilarityScore() double |
  |      } |
  |   |
  |      class SimilarityAlgorithm { |
  |          <<interface>> |
  |          +compare(MethodModel a, MethodModel b) double |
  |      } |
  |   |
  |      class JaccardSimilarity { |
  |          +compare(MethodModel a, MethodModel b) double |
  |      } |
  |   |
  |      class DuplicateDetector { |
  |          -SimilarityAlgorithm algorithm |
  |          -double threshold |
  |          +findDuplicates(List~~MethodModel~~) List~~DuplicatePair~~ |
  |      } |
  |   |
  |      class AbstractReport { |
  |          <<abstract>> |
  |          #List~~DuplicatePair~~ results |
  |          +generate()\* |
  |          #printHeader() |
  |      } |
  |   |
  |      class ConsoleReport { |
  |          +generate() |
  |      } |
  |   |
  |      class InvalidProjectPathException { |
  |          +InvalidProjectPathException(String message) |
  |      } |
  |   |
  |      class NoJavaFilesFoundException { |
  |          +NoJavaFilesFoundException(String message) |
  |      } |
  |   |
  |      SimilarityAlgorithm < | .. JaccardSimilarity |
  |      AbstractReport < | -- ConsoleReport |
  |      DuplicateDetector --> SimilarityAlgorithm |
  |      DuplicateDetector --> DuplicatePair |
  |      DuplicatePair --> MethodModel |
  |      Exception < | -- InvalidProjectPathException |
  |      Exception < | -- NoJavaFilesFoundException |
  |   |
  | **Why these decisions were made:** |
- One interface (SimilarityAlgorithm), not several, because the project only has one axis of variation (how similarity is scored) — adding interfaces for things that never vary would be complexity without purpose.
- AbstractReport is abstract, not an interface, because report types share actual implemented behavior (the header), not just a method contract.
- Custom exceptions extend the standard checked Exception, not RuntimeException, so callers are forced to handle them explicitly — appropriate for input errors the caller should always anticipate.  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OMQ2AABAAsSNBCUrfDqrYGVDAgAU2QtIq6DIzW7UHAMBfHGt1V+fXEwAAXrseHCQGBEuErVgAAAAASUVORK5CYII=)  
  **8. Technology Stack**
  | | | |
  | -------------------- | --------------------------------- | --------------------------------------------------------------------------------------------------------------------------- |
  | **Layer** | **Technology** | **Why chosen** |
  | Language | Java 17 | Course requirement; modern long-term-support release |
  | Build tool | Maven | Standard, widely documented, simple dependency management for a small team |
  | GUI (Phase 2) | Java Swing | no external GUI framework needed |
  | REST layer (Phase 3) | Javalin | Minimal, unopinionated — a request handler that calls the engine, not a framework that generates logic on the team's behalf |
  | Website (Phase 4) | HTML / CSS / JavaScript | Static, no build tooling needed, satisfies the course's interest in JS-based GUI concepts |
  | Extension (Phase 5) | TypeScript, VS Code Extension API | Required language for this platform — no alternative exists |
  | Testing | JUnit 5 | Standard Java testing framework |
  | Version control | Git + GitHub | Team collaboration, branching, PR review |
  |   |

![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANklEQVR4nO3OQQmAABRAsScYxpg/h5VMYARvRrCCNxG2BFtmZquOAAD4i3Ot7mr/egIAwGvXA224BcUMk6pDAAAAAElFTkSuQmCC)  
**9. Project Folder Structure**  
smart-duplicate-detector/  
 ├── README.md  
 ├── CONTRIBUTING.md  
 ├── LICENSE  
 ├── pom.xml  
 ├── docs/  
 │   ├── SRS.md  
 │  └── MASTER_PROJECT_DOCUMENT.md  
 ├── src/  
 │   ├── main/java/sdd/  
 │   │   ├── model/  
 │   │   │   ├── MethodModel.java  
 │   │   │   └── DuplicatePair.java  
 │   │   ├── core/  
 │   │   │   ├── ProjectScanner.java  
 │   │   │   ├── MethodParser.java  
 │   │   │   ├── SimilarityAlgorithm.java  
 │   │   │   ├── JaccardSimilarity.java  
 │   │   │   └── DuplicateDetector.java  
 │   │   ├── report/  
 │   │   │   ├── AbstractReport.java  
 │   │   │   └── ConsoleReport.java  
 │   │   ├── exceptions/  
 │   │   │   ├── InvalidProjectPathException.java  
 │   │   │   └── NoJavaFilesFoundException.java  
 │   │   ├── gui/  
 │   │   │   ├── MainFrame.java  
 │   │   │   └── ResultsPanel.java  
 │   │   └── cli/  
 │   │       ├── CliRunner.java  
 │   │       └── Main.java  
 │   └── test/java/sdd/  
 │       ├── core/  
 │       └── model/  
 ├── website/               (Phase 4 — separate, independent codebase)  
 │   ├── index.html  
 │   ├── docs.html  
 │   ├── about.html  
 │   └── demo.html  
 └── vscode-extension/      (Phase 5 — separate, independent codebase)  
     └── src/extension.ts

   
**Purpose of each package:**

- model — data-only classes representing a method and a duplicate pair
- core — the entire detection pipeline; this is where the graded OOP logic lives
- report — output formatting, kept separate so new formats don't touch detection logic
- exceptions — custom error types
- gui — Swing desktop entry point
- cli — command-line entry point
- website / vscode-extension — Phase 4/5, deliberately outside the Java module boundary since they are separate languages and separate concerns  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANklEQVR4nO3OUQmAABBAsSeYxZyXSzCJASxgACv4J8KWYMvMbNURAAB/ca7VXe1fTwAAeO16AKe+BdmJqrPdAAAAAElFTkSuQmCC)  
  **10. Development Phases**

**Phase 1 — Core Engine Setup**  
**Tasks:**

- Create Maven project, configure pom.xml
- Set up Git repository, branch protection, initial skeleton commit
- Implement MethodModel, DuplicatePair
- Implement ProjectScanner
- Implement MethodParser
- Implement custom exceptions  
  **Deliverables:** A working scanner + parser that can list every method in a sample project
   
     
   **Dependencies:** None  
  **Phase 2 — Detection Logic**  
  **Tasks:**
- Implement SimilarityAlgorithm interface
- Implement JaccardSimilarity
- Implement DuplicateDetector
- Implement AbstractReport / ConsoleReport
- Implement CliRunner and Main entry dispatch  
  **Deliverables:** A CLI tool that finds and prints real duplicates on a test project
   
     
   **Dependencies:** Phase 1 complete  
  **Phase 3 — Swing GUI**  
  **Tasks:**
- Build MainFrame (folder picker, Scan button)
- Build ResultsPanel (JTable of results)
- Wire GUI to the same core engine via SwingWorker (keeps UI responsive)  
  **Deliverables:** A working desktop application
       
   **Dependencies:** Phase 2 complete  
  **Phase 4 — Testing & Submission Polish**  
  **Tasks:**
- JUnit tests for core and model
- End-to-end test on a realistic sample project with a planted duplicate
- Finalize README, SRS, this document
   
     
   **Dependencies:** Phase 3 complete  
  **Phase 5 — REST API \***  
  **Tasks:** Javalin server, /scan endpoint, JSON serialization of results
     
   **Dependencies:** Phase 4 complete and demo-ready  
  **Phase 6 — Website \***
  **Tasks:** Home, Documentation, About pages; drag-and-drop demo page calling Phase 5's API
     
   **Dependencies:** Phase 5 complete  
  **Phase 7 — VS Code Extension \***  
  **Tasks:** TypeScript extension, manual "Scan for Duplicates" command, calls Phase 5's API
     
   **Dependencies:** Phase 5 complete  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANklEQVR4nO3OYQ1AABSAwc8mi5wvkwZyCKCAACr4Z7a7BLfMzFYdAQDwF+da3dX+9QQAgNeuB6feBdUJcyS2AAAAAElFTkSuQmCC)  
  **11. Sprint Planning**
  | | | | | | |
  | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------- | ----------------- | -------------------------------------------- | --------------------------- | --------------------------------------------------- |
  | **Sprint** | **Objectives** | **Tasks** | **Expected Output** | **Who** | **Definition of Done** |
  | Sprint 1 | Core data model + scanning | Phase 1 tasks | Scanner lists every method in a test project | Any available active member | Code merged to dev, unit tested |
  | Sprint 2 | Detection logic | Phase 2 tasks | CLI prints real duplicate pairs with scores | Any available active member | CLI runs end-to-end on a test project |
  | Sprint 3 | Desktop GUI | Phase 3 tasks | Swing app matches CLI output | Any available active member | GUI does not freeze during scan |
  | Sprint 4 | Testing & docs | Phase 4 tasks | Submission-ready repository | Whole team | Every member can explain every class in a mock demo |
  |   |
  | **12. Implementation Guide** |
  | This guide assumes no team member has built this exact kind of tool before. Follow it in order — each step depends on the previous one working. |
  | **Step 1 — Project setup** |
  | **Goal:** A Maven project that compiles |
  |   |
  |  **Files to create:** pom.xml |
  |   |
  |  **Classes:** None yet |
  |   |
  |  **Testing:** mvn compile succeeds |
  |   |
  |  **Expected result:** An empty but valid Java project |
  | **Step 2 — Model classes** |
  | **Goal:** A way to represent a method in code |
  |   |
  |  **Files to create:** model/MethodModel.java, model/DuplicatePair.java |
  |   |
  |  **Classes/methods:** |
  | public class MethodModel { |
  |      public MethodModel(String methodName, String filePath, int lineNumber, List<String> bodyTokens) { ... } |
  |      public String getMethodName() { ... } |
  |      public String getFilePath() { ... } |
  |      public int getLineNumber() { ... } |
  |      public List<String> getBodyTokens() { ... } |
  |  } |
  |   |
  | **Testing:** A JUnit test constructs a MethodModel and checks every getter returns what was passed in |
  |   |
  |  **Expected result:** A compiling, immutable data class |
  | **Step 3 — Custom exceptions** |
  | **Goal:** Meaningful errors instead of crashes |
  |   |
  |  **Files to create:** exceptions/InvalidProjectPathException.java, exceptions/NoJavaFilesFoundException.java |
  |   |
  |  **Classes/methods:** Each extends Exception, with a constructor taking a String message |
  |   |
  |  **Testing:** A JUnit test asserts the exception is thrown for a non-existent path |
  |   |
  |  **Expected result:** Two working custom exception types |
  | **Step 4 — Project scanner** |
  | **Goal:** Find every .java file under a folder |
  |   |
  |  **Files to create:** core/ProjectScanner.java |
  |   |
  |  **Classes/methods:** public List<File> scan(String rootPath) throws InvalidProjectPathException, NoJavaFilesFoundException |
  |   |
  |  **Testing:** Run against a folder with 3 known .java files, assert 3 are found |
  |   |
  |  **Expected result:** A working recursive file finder |
  | **Step 5 — Method parser** |
  | **Goal:** Turn file contents into MethodModel objects |
  |   |
  |  **Files to create:** core/MethodParser.java |
  |   |
  |  **Classes/methods:** public List<MethodModel> parse(File javaFile) |
  |   |
  |  **Testing:** Run against a file with 2 known methods, assert 2 MethodModel objects are produced with correct line numbers |
  |   |
  |  **Expected result:** A working method extractor |
  | **Step 6 — Similarity algorithm** |
  | **Goal:** Score how similar two methods are |
  |   |
  |  **Files to create:** core/SimilarityAlgorithm.java (interface), core/JaccardSimilarity.java |
  |   |
  |  **Classes/methods:** double compare(MethodModel a, MethodModel b) |
  |   |
  |  **Testing:** Compare two identical methods (expect ~100%) and two unrelated methods (expect a low score) |
  |   |
  |  **Expected result:** A working, testable scoring function |
  | **Step 7 — Duplicate detector** |
  | **Goal:** Find all pairs above a threshold |
  |   |
  |  **Files to create:** core/DuplicateDetector.java |
  |   |
  |  **Classes/methods:** public List<DuplicatePair> findDuplicates(List<MethodModel> methods, double threshold) |
  |   |
  |  **Testing:** Run against a known set of methods containing one planted duplicate pair, assert exactly one pair is returned |
  |   |
  |  **Expected result:** The full detection pipeline working end-to-end |
  | **Step 8 — Reporting** |
  | **Goal:** Print results |
  |   |
  |  **Files to create:** report/AbstractReport.java, report/ConsoleReport.java |
  |   |
  |  **Testing:** Run against known DuplicatePair objects, check console output contains the expected file names and scores |
  |   |
  |  **Expected result:** Human-readable output |
  | **Step 9 — CLI entry point** |
  | **Goal:** Tie everything together |
  |   |
  |  **Files to create:** cli/CliRunner.java, cli/Main.java |
  |   |
  |  **Testing:** Run java -jar target/sdd.jar --path ./test-project against a real folder |
  |   |
  |  **Expected result:** A working command-line tool |
  | **Step 10 — Swing GUI** |
  | **Goal:** A visual alternative to the CLI |
  |   |
  |  **Files to create:** gui/MainFrame.java, gui/ResultsPanel.java |
  |   |
  |  **Testing:** Manual — pick a folder, click Scan, confirm results match the CLI's output for the same folder |
  |   |
  |  **Expected result:** A working desktop application |
  | ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OMQ2AABAAsSPBCj5fFyM6mJHAjAU2QtIq6DIzW7UHAMBfnGt1V8fXEwAAXrsexOEF35f1aEgAAAAASUVORK5CYII=) |
  | **13. Coding Standards** |
  | | | |
  | - | - | - |
  | **Element** | **Convention** | **Example** |
  | Classes | PascalCase | DuplicateDetector |
  | Methods, variables | camelCase | findDuplicates() |
  | Constants | UPPER_SNAKE_CASE | DEFAULT_THRESHOLD |
  | Packages | all lowercase | sdd.core |
  |   |
  | **Comments & documentation:** Every public class and public method gets a short Javadoc comment stating what it does — not restating the method name, but explaining intent where it isn't obvious. |
  | **Error handling:** Never catch an exception silently. Every catch block either handles the error meaningfully (a user-facing message) or rethrows it — never an empty catch block. |
  | **Exception handling:** Use the project's custom exceptions for domain-specific failures (bad path, no files found). Use standard exceptions only for genuinely generic failures. |
  | ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OQQ2AQBAAsSE5CbzRujLwhwQMYIEfIWkVdJuZozoDAOAvrlWtav96AgDAa/cDEXQEKquakOYAAAAASUVORK5CYII=) |
  | **14. Git Workflow** |
- **Branches:**main (protected, no direct commits) → dev (integration branch) → feature/<package>-<short-description> (individual work)
- **Commit messages:** describe _what changed_, e.g. Add MethodModel with immutable fields and constructor — never just update or fix
- **Pull requests:** every PR requires at least one other team member's approval before merging
- **Code review:** reviewer checks the code compiles, has a test if it's a core/model class, and matches the architecture in Section 6
- **Merge process:** feature branch → dev (after review) → main (only at a stable milestone, e.g. end of a Phase)  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OQQmAABRAsSd4NIGhrOTvaQBrWMGbCFuCLTOzV2cAAPzFvVZbdXw9AQDgtesBhYQEO+64Y8AAAAAASUVORK5CYII=)  
  **15. Team Responsibilities**  
  Given uneven participation across the team, roles are described as **areas of ownership**, not rigid full-time assignments — anyone active picks up the next unclaimed task in their area.
  | | | |
  | ------------------------ | ------------------------------------------------------------------------- | ---------------- |
  | **Role** | **Responsibility** | **Maps to** |
  | Architecture Lead | Owns Section 6/7 decisions, reviews PRs for structural consistency | core, model |
  | Backend/Engine Developer | Implements scanning, parsing, detection | core, exceptions |
  | GUI Developer | Implements the Swing interface | gui |
  | Documentation Lead | Keeps this document, the README, and the SRS in sync with the actual code | docs/ |
  |   |

![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OMQ2AABAAsSPBCUZfEnoYmFDBhAU2QtIq6DIzW7UHAMBfnGt1V8fXEwAAXrse/wcF74lXkIsAAAAASUVORK5CYII=)  
**16. Testing Strategy**

- **Unit testing:** every class in core and model has JUnit 5 tests — especially JaccardSimilarity (known-similar and known-different inputs) and DuplicateDetector (a known planted duplicate is found)
- **Integration testing:** run the full CLI pipeline against a small real sample project, confirm the reported duplicates match what's actually there
- **Manual testing:** GUI is tested by hand — folder picker works, Scan button doesn't freeze the window, results match CLI output for the same input
- **Acceptance testing:** the whole team runs the tool against the same sample project before the demo and confirms the same, correct result  
  **Testing checklist:**
- Scanning an empty folder throws NoJavaFilesFoundException
- Scanning a non-existent path throws InvalidProjectPathException
- Two identical methods score at or near 100%
- Two unrelated methods score low
- A planted duplicate pair is correctly found in a multi-file test project
- GUI and CLI produce the same result for the same input  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OMQ2AABAAsSNhwgJOUPcjIpnRgQU2QtIq6DIze3UGAMBf3Gu1VcfXEwAAXrseaJEEL8XMiYMAAAAASUVORK5CYII=)  
  **17. Risks**
  | | |
  | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- |
  | **Risk** | **Mitigation** |
  | Scope creep (website/extension distracting from graded core) | Section 19's IN/OUT scope boundary is treated as final; later phases are gated behind Phase 4 completion |
  | Integration conflicts from parallel work | Interfaces defined early so members can code against a contract before an implementation exists; daily merges to dev |
  | False positives/negatives in similarity scoring | Configurable threshold, tested against known cases before relying on it in the demo |
  |   |
  | ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANklEQVR4nO3OQQmAABRAsSfYxZq/kR2MYQLPJrCCNxG2BFtmZquOAAD4i3Ot7mr/egIAwGvXA5RoBdJGGuuWAAAAAElFTkSuQmCC) |
  | **18. Future Improvements** |
- REST API, website, and VS Code extension (Phases 5–7, Section 10)
- Keep/Replace decision workflow using the Command pattern
- A second similarity algorithm (structural comparison) selectable via the existing SimilarityAlgorithm interface
- Additional report formats (JSON, HTML) via the existing AbstractReport base class  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OQQmAABRAsSfYxKK/kJXEkyE8WcGbCFuCLTOzVXsAAPzFsVZ3dX4cAQDgvesB/vEF9H9odtUAAAAASUVORK5CYII=)  
  **19. Scope Definition**  
  **IN SCOPE (this submission)**
- Scanning a local Java project folder
- Extracting methods and scoring similarity as a percentage
- CLI and Swing GUI, sharing one engine
- Custom exception handling
- JUnit test suite

**FUTURE SCOPE (realistic, but explicitly not part of this submission)**

- REST API (Phase 5)
- Documentation and live-demo website (Phase 6)
- VS Code extension (Phase 7)
- Keep/Replace decision workflow
- Automatically modifying, deleting, or rewriting a developer's source code
- Supporting any language other than Java
  _**This section exists specifically to prevent feature creep.**_ _ If a task doesn't appear under "IN SCOPE," it does not get worked on before Phase 4 is complete and demo-ready, no matter how good the idea is._  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANklEQVR4nO3OMQ2AABAAsSNBACPykMH4NpGACyywEZJWQZeZ2aszAAD+4l6rrTo+jgAA8N71AL/CBEiG5xPoAAAAAElFTkSuQmCC)  
  **20. Deliverables**  
  When Phase 4 (Section 10) is complete, the following must exist:
- A working, runnable .jar file supporting both --path (CLI) and no-argument (GUI) modes
- Full source code in the GitHub repository, organized per Section 9
- This master document, the SRS, and the README, all consistent with the actual code
- A JUnit test suite covering core and model
- A sample test project with at least one planted duplicate, used for the demo
- Every team member able to explain any class in the codebase on request  
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANUlEQVR4nO3OMQ2AABAAsSNBCkJfE1pYGfHAiAU2QtIq6DIzW7UHAMBfnGt1V8fXEwAAXrse4dwF6o2O55YAAAAASUVORK5CYII=)  
  **21. Final Architecture Diagram**  
  graph TB  
       subgraph Engine["Java Core Engine — fully custom, minimal dependencies"]  
           Scanner[ProjectScanner]  
           Parser[MethodParser]  
           Model[MethodModel / DuplicatePair]  
           Algo[SimilarityAlgorithm interface]  
           Jaccard[JaccardSimilarity]  
           Detector[DuplicateDetector]  
           Report[AbstractReport / ConsoleReport]  
           Exc[Custom Exceptions]  
       end
     
       subgraph Submission["Graded Submission — Phase 1-4"]  
           CLI[CLI — CliRunner]  
           GUI[Swing GUI — MainFrame]  
       end
     
       subgraph Future["Future Work — Phase 5-7, not graded"]  
           API[REST API — Javalin]  
           Web[Website]  
           Ext[VS Code Extension]  
       end
     
       Scanner --> Parser --> Model  
       Model --> Detector  
       Algo --> Jaccard  
       Detector --> Algo  
       Detector --> Report  
       Scanner -.-> Exc
     
       Engine --> CLI  
       Engine --> GUI  
       Engine -.future.-> API  
       API -.-> Web  
       API -.-> Ext
     
  ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAnEAAAACCAYAAAA3pIp+AAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAANklEQVR4nO3OQQmAABRAsSfYxZo/kSGMYQLPJrCCNxG2BFtmZquOAAD4i3Ot7mr/egIAwGvXA4qrBdGuSdJuAAAAAElFTkSuQmCC)  
  **22. Appendix**  
  **Glossary**
  | | |
  | -------------------------- | -------------------------------------------------------------------------------------------------------------------- |
  | **Term** | **Meaning** |
  | Token | A single unit of source code (a keyword, identifier, symbol) produced by breaking a method body apart for comparison |
  | Similarity score | A percentage representing how much two methods' token sets overlap |
  | Clone detection | The general field of finding duplicated code in software, of which this project is one implementation |
  | Threshold | The minimum similarity score required for a pair to be reported as a duplicate |
  | Adapter (in this document) | A thin component (CLI, GUI, API) that calls the core engine without containing its own detection logic |
  |   |
  | **Resources** |
- JUnit 5 documentation — https://junit.org/junit5/docs/current/user-guide/
- Java Swing tutorial (Oracle) — https://docs.oracle.com/javase/tutorial/uiswing/  
  **Useful links**
- Project repository: https://github.com/fetehadin/smart-duplicate-detector
- SRS: docs/SRS.md
- This document: docs/MASTER_PROJECT_DOCUMENT.md
