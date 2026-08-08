package com.agrilink.services;

import com.agrilink.models.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> allOrders;

    public OrderService(List<Order> loadedOrders) {
        this.allOrders = loadedOrders;
    }

    public void placeOrder(Customer customer, Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            System.out.println("Failed: Not enough stock.");
            return;
        }
        String newOrderId = "ORD" + (allOrders.size() + 100);
        Order order = new Order(newOrderId, customer, product, quantity);

        double totalCost = order.getTotalAmount();
        if (customer.withdrawMoney(totalCost)) {
            order.updateStatus("CONFIRMED");
            allOrders.add(order);
            product.setQuantity(product.getQuantity() - quantity);
            System.out.println("Payment Successful! Order " + newOrderId + " is CONFIRMED.");
        } else {
            System.out.println("Payment Failed: Insufficient funds.");
        }
    }

    public List<Order> getOrdersByCustomer(String customerId) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order o : allOrders) {
            if (o.getCustomer().getUserId().equals(customerId)) customerOrders.add(o);
        }
        return customerOrders;
    }
    
    public List<Order> getAllSystemOrders() { return allOrders; } // Needed for saving
}