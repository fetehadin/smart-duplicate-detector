#!/usr/bin/env node

const { spawnSync } = require('child_process');
const path = require('path');

// 1. Find the exact location of the JAR file relative to this script
const jarPath = path.join(__dirname, 'target', 'smart-duplicate-detector.jar');

// 2. Capture any arguments the user typed (e.g., --path . --threshold 0.8)
const args = process.argv.slice(2);

// 3. Check if Java is installed
const javaCheck = spawnSync('java', ['-version']);
if (javaCheck.error) {
    console.error('❌ Error: Java is not installed or not in your PATH.');
    console.error('Please install Java 17 or higher to run Smart Duplicate Detector.');
    process.exit(1);
}

// 4. Execute the JAR file
const result = spawnSync('java', ['-jar', jarPath, ...args], {
    stdio: 'inherit' // This ensures the terminal output AND the GUI window work perfectly
});

// 5. Exit with the same status code the Java app returned
process.exit(result.status);
