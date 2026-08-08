package com.agrilink.services;

import java.util.ArrayList;
import java.util.List;

import com.agrilink.interfaces.INotificationService;
import com.agrilink.models.Product;

public class ProductService {
    private List<Product> products;
    private INotificationService notificationService;

    public ProductService(INotificationService notificationService, List<Product> loadedProducts) {
        this.notificationService = notificationService;
        this.products = (loadedProducts != null) ? loadedProducts : new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
        notificationService.sendNotification("Admin", "New product added: " + product.getName() + " (Pending Approval)");
    }

    public void approveProduct(String productId) {
        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                p.setApproved(true);
                p.setStatus("PENDING_PICKUP");
                notificationService.sendNotification(p.getFarmerId(), "Your product " + p.getName() + " has been approved for pickup!");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    public List<Product> getAvailableProducts() {
        List<Product> available = new ArrayList<>();
        for (Product p : products) {
            // কাস্টমার শুধু রেডি থাকা পণ্য দেখবে
            if (p.isApproved() && p.getStatus().equals("READY_FOR_SALE") && p.getQuantity() > 0) {
                available.add(p);
            }
        }
        return available;
    }

    public List<Product> getPendingApprovalProducts() {
        List<Product> pending = new ArrayList<>();
        for (Product p : products) {
            if (!p.isApproved() && "PENDING_APPROVAL".equals(p.getStatus())) {
                pending.add(p);
            }
        }
        return pending;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    // কাস্টমারের জন্য নতুন দাম সেট করা
    public void setCustomerPriceForBatch(String batchId, double newPrice) {
        for (Product p : products) {
            if (p.getProductId().equals(batchId) && p.getStatus().equals("IN_WAREHOUSE")) {
                p.setCustomerPrice(newPrice);
                p.setStatus("READY_FOR_SALE");
                System.out.println("✅ Customer price set to $" + newPrice + " for Batch " + batchId);
                return;
            }
        }
        System.out.println("❌ ERROR: Batch not found or not in warehouse yet.");
    }
}