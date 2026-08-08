package com.agrilink.services;

import com.agrilink.models.Order;

public class TransportService {
    private OrderService orderService;

    // Dependency Injection: Transport service needs access to the orders
    public TransportService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void viewPendingPickups() {
        System.out.println("\n--- Pending Pickups & Deliveries ---");
        boolean found = false;
        
        for (Order order : orderService.getAllSystemOrders()) {
            if (order.getStatus().equals("CONFIRMED") || order.getStatus().equals("COLLECTED")) {
                System.out.println("Order ID: " + order.getOrderId() + " | Current Status: " + order.getStatus());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No pending deliveries at the moment.");
        }
    }

    public void updateOrderStatus(String orderId, String newStatus) {
        for (Order order : orderService.getAllSystemOrders()) {
            if (order.getOrderId().equals(orderId)) {
                order.updateStatus(newStatus);
                System.out.println("Order " + orderId + " status updated to: " + newStatus);
                return;
            }
        }
        System.out.println("Order not found!");
    }
}