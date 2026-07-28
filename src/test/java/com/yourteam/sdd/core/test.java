public class test {

    public String printReportA() {
        return String.format("DUPLICATE FOUND (%d%% similar)%n→ %-25s %s:%d%n→ %-25s %s:%d",
                getSimilarityPercent(),
                first.getSignatureLabel(), first.getFilePath(), first.getLineNumber(),
                second.getSignatureLabel(), second.getFilePath(), second.getLineNumber());
    }

    public String printReportB() {
        return String.format("DUPLICATE FOUND (%d%% similar)%n→ %-25s %s:%d%n→ %-25s %s:%d",
                getSimilarityPercent(),
                first.getSignatureLabel(), first.getFilePath(), first.getLineNumber(),
                second.getSignatureLabel(), second.getFilePath(), second.getLineNumber());
    }
}