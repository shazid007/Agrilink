package com.agrilink.services;

import java.util.ArrayList;
import java.util.List;

import com.agrilink.exceptions.AuthenticationException;
import com.agrilink.models.User;

public class AuthService {
    private List<User> users;
    private User currentUser;

    public AuthService(List<User> loadedUsers) {
        this.users = (loadedUsers != null) ? loadedUsers : new ArrayList<>();
    }

    public void registerUser(User user) {
        users.add(user);
        System.out.println("✅ Registration successful for: " + user.getName());
    }

    public User login(String email, String password) throws AuthenticationException {
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                currentUser = u;
                System.out.println("✅ Login successful. Welcome, " + u.getName() + "!");
                return currentUser;
            }
        }
        throw new AuthenticationException("Invalid email or password.");
    }

    public void logout() {
        System.out.println("Goodbye, " + (currentUser != null ? currentUser.getName() : "User") + "!");
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public boolean removeUser(String id) {
        return users.removeIf(u -> u.getUserId().equals(id));
    }

    // আইডি দিয়ে ইউজার বের করার নতুন মেথড (ফার্মারের ওয়ালেটে টাকা পাঠানোর জন্য)
    public User getUserById(String id) {
        for (User u : users) {
            if (u.getUserId().equals(id)) return u;
        }
        return null;
    }
}