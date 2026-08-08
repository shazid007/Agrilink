package com.agrilink.models;

public class Farmer extends User {
    
    public Farmer(String userId, String name, String email, String password) {
        super(userId, name, email, password, "FARMER");
    }

    @Override
    public void viewDashboard() {
        System.out.println("\n=== Farmer Dashboard ===");
        System.out.println("1. Supply Product (From Catalog)");
        System.out.println("2. View My Earnings");
        System.out.println("3. View My Supply History & Status");
        System.out.println("4. Manage Wallet (Add / Withdraw Money)"); // নতুন অপশন
    }
}