package com.agrilink.models;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String userId;
    protected String name;
    protected String email;
    protected String password;
    protected String role;
    protected double walletBalance; // নতুন ওয়ালেট ব্যালেন্স

    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.walletBalance = 0.0; // শুরুতে ব্যালেন্স ০ থাকবে
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public double getWalletBalance() { return walletBalance; }

    // টাকা অ্যাড করার মেথড
    public void addMoney(double amount) {
        if (amount > 0) this.walletBalance += amount;
    }

    // টাকা উইথড্র করার মেথড
    public boolean withdrawMoney(double amount) {
        if (amount > 0 && this.walletBalance >= amount) {
            this.walletBalance -= amount;
            return true;
        }
        return false;
    }

    public abstract void viewDashboard();
}