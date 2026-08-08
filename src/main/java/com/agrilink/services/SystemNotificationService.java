package com.agrilink.services;

import com.agrilink.interfaces.INotificationService;

public class SystemNotificationService implements INotificationService {
    
    @Override
    public void sendNotification(String userId, String message) {
        // In a real app, this might send an SMS or Email.
        // For now, we simulate it in the console.
        System.out.println("\n[NOTIFICATION to " + userId + "]: " + message + "\n");
    }
}