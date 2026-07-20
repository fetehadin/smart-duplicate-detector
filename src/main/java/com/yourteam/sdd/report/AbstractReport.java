package com.yourteam.sdd.report;

import com.yourteam.sdd.model.DuplicatePair;

import java.util.List;

/**
 * Common shape for anything that turns scan results into output
 * (console text now; could be HTML/JSON later without touching
 * the detection pipeline).
 */
public abstract class AbstractReport {

    public abstract void reportScanStart(String path);

    public abstract void reportMethodsFound(int methodCount, int fileCount);

    public abstract void reportDuplicates(List<DuplicatePair> duplicates);

    /** Template method: subclasses just implement the three hooks above. */
    public final void generate(String path, int methodCount, int fileCount, List<DuplicatePair> duplicates) {
        reportScanStart(path);
        reportMethodsFound(methodCount, fileCount);
        reportDuplicates(duplicates);
    }
}
