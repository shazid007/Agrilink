package com.agrilink.models;

public class Admin extends User {
    
    public Admin(String userId, String name, String email, String password) {
        super(userId, name, email, password, "ADMIN");
    }

    @Override
    public void viewDashboard() {
        System.out.println("\n=== Admin Control Panel ===");
        System.out.println("1. Approve Pending Products");
        System.out.println("2. Manage Warehouse Logistics");
        System.out.println("3. View Sales & Analytics Dashboard");
        System.out.println("4. Manage Product Catalog (Set Prices for Farmers)");
        System.out.println("5. Set Customer Price for Warehouse Items"); // নতুন অপশন
    }
}