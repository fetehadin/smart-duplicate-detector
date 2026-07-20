package com.yourteam.sdd.report;

import com.yourteam.sdd.model.DuplicatePair;

import java.util.List;

public class ConsoleReport extends AbstractReport {

    @Override
    public void reportScanStart(String path) {
        System.out.println("Scanning " + path + " ...");
    }

    @Override
    public void reportMethodsFound(int methodCount, int fileCount) {
        System.out.println("Found " + methodCount + " methods across " + fileCount + " files.");
    }

    @Override
    public void reportDuplicates(List<DuplicatePair> duplicates) {
        if (duplicates.isEmpty()) {
            System.out.println("Scan complete. 0 duplicate pairs found.");
            return;
        }

        for (DuplicatePair pair : duplicates) {
            System.out.println(pair);
        }
        System.out.println("Scan complete. " + duplicates.size() +
                (duplicates.size() == 1 ? " duplicate pair found." : " duplicate pairs found."));
    }
}
