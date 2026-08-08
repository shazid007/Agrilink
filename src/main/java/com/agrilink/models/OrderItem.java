package com.agrilink.models;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private Product product;
    private int quantity;
    private double priceAtTimeOfOrder;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtTimeOfOrder = product.getPrice();
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPriceAtTimeOfOrder() { return priceAtTimeOfOrder; }
    
    public double getSubTotal() {
        return priceAtTimeOfOrder * quantity;
    }
}