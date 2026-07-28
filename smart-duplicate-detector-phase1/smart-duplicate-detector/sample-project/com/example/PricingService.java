package com.example;

public class PricingService {

    public double applyPriceReduction(Order o) {
        if (o.getTotal() > 100) {
            System.out.println("Discount applied");
            return o.getTotal() * 0.1;
        }
        return 0;
    }

    public int add(int a, int b) {
        return a + b;
    }
}
