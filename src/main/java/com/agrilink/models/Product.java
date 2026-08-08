package com.agrilink.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String productId;
    private String farmerId;
    private String name;
    private double price; // ফার্মার যে দাম পাবে
    private double customerPrice; // অ্যাডমিন কাস্টমারের জন্য যে দাম সেট করবে (Step 3 এর জন্য)
    private String grade;
    private int quantity;
    private boolean isApproved;
    private String status; // PENDING_APPROVAL, PENDING_PICKUP, IN_WAREHOUSE

    public Product(String productId, String farmerId, String name, double price, String grade, int quantity) {
        this.productId = productId;
        this.farmerId = farmerId;
        this.name = name;
        this.price = price;
        this.customerPrice = price;
        this.grade = grade;
        this.quantity = quantity;
        this.isApproved = false;
        this.status = "PENDING_APPROVAL"; // শুরুতে অ্যাপ্রুভালের অপেক্ষায় থাকবে
    }

    // Getters
    public String getProductId() { return productId; }
    public String getFarmerId() { return farmerId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getCustomerPrice() { return customerPrice; }
    public String getGrade() { return grade; }
    public int getQuantity() { return quantity; }
    public boolean isApproved() { return isApproved; }
    public String getStatus() { return status; }

    // Setters
    public void setApproved(boolean approved) { this.isApproved = approved; }
    public void setStatus(String status) { this.status = status; }
    public void setCustomerPrice(double price) { this.customerPrice = price; }
    public void setQuantity(int quantity) {
    this.quantity = quantity;}
    @Override
    public String toString() {
        return name + " (Grade " + grade + ") - Qty: " + quantity + " | Status: " + status;
    }
}
