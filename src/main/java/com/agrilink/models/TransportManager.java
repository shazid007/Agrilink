package com.agrilink.models;

public class TransportManager extends User {
    
    public TransportManager(String userId, String name, String email, String password) {
        // সুপার ক্লাসে (User) ডেটা পাঠানো হচ্ছে এবং রোল সেট করা হচ্ছে "TRANSPORT"
        super(userId, name, email, password, "TRANSPORT");
    }

    @Override
    public void viewDashboard() {
        System.out.println("\n=== Transport Manager Dashboard ===");
        System.out.println("1. View Pending Pickups (From Farmers)");
        System.out.println("2. Update Pickup Status (Deliver to Warehouse)");
    }
}