package com.example;

public class OrderService {

    public double calculateDiscount(Order order) {
        if (order.getTotal() > 100) {
            return order.getTotal() * 0.1;
        }
        return 0;
    }

    public boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
}
