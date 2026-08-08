package com.agrilink.models;

import java.io.Serializable;

public class Order implements Serializable {
    private String orderId;
    private Customer customer;
    private Product product;
    private int quantity;
    private double totalAmount;
    private String status;

    public Order(String orderId, Customer customer, Product product, int quantity) {
        this.orderId = orderId;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.status = "PENDING";
        calculateTotal();
    }

    public void calculateTotal() {
        double unitPrice = product.getCustomerPrice() > 0 ? product.getCustomerPrice() : product.getPrice();
        this.totalAmount = unitPrice * quantity;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public void displayOrderSummary() {
        System.out.println("Order ID: " + orderId + " | Product: " + product.getName() + " | Qty: " + quantity + " | Total: $" + totalAmount + " | Status: " + status);
    }

    // Getters
    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
}