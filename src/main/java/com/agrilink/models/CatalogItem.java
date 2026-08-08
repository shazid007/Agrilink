package com.agrilink.models;

import java.io.Serializable;

public class CatalogItem implements Serializable {
    private String itemId;
    private String name;
    private String grade;
    private double farmerPrice; // ফার্মারকে আমরা কত টাকা দেবো তার লিস্ট

    public CatalogItem(String itemId, String name, String grade, double farmerPrice) {
        this.itemId = itemId;
        this.name = name;
        this.grade = grade;
        this.farmerPrice = farmerPrice;
    }

    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public String getGrade() { return grade; }
    public double getFarmerPrice() { return farmerPrice; }

    @Override
    public String toString() {
        return name + " (Grade: " + grade + ") - Our Price: $" + farmerPrice + "/unit";
    }
}