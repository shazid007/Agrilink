package com.agrilink.models;

public class Customer extends User {
    
    public Customer(String userId, String name, String email, String password) {
        super(userId, name, email, password, "CUSTOMER");
    }

    @Override
    public void viewDashboard() {
        System.out.println("\n=== Customer Dashboard ===");
        System.out.println("1. View Available Products & Buy");
        System.out.println("2. View My Orders & Status");
        System.out.println("3. Pre-order Items (From Catalog)");
        System.out.println("4. Manage Wallet (Add / Withdraw Money)"); // নতুন অপশন
    }
}