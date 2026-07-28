public class TestScenarios {

    // --- PAIR 1: True Behavioral Clones ---
    // These share the exact same control flow (IfStmt, ReturnStmt)
    // The engine should score these very highly (likely 95%+)
    public double calculateTaxUSA(double amount) {
        double taxRate = 0.07;
        if (amount > 100.0) {
            taxRate = 0.05;
        }
        return amount + (amount * taxRate);
    }

    public double calculateTaxUK(double amount) {
        double vat = 0.20;
        if (amount > 500.0) {
            vat = 0.15;
        }
        return amount + (amount * vat);
    }

    // --- PAIR 2: Boilerplate matches, but different control flow ---
    // Both declare variables and call a print method, but one uses 'if' and the other uses 'while'.
    // The new 3x weight on control flow should drop their score below the 80% threshold!
    public void checkUser(String name) {
        boolean isValid = false;
        if (name != null) {
            isValid = true;
        }
        System.out.println(isValid);
    }

    public void loopUser(String name) {
        boolean isProcessed = false;
        while (isProcessed == false) {
            isProcessed = true;
        }
        System.out.println(isProcessed);
    }
}