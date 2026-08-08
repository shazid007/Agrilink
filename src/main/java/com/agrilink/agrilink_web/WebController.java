package com.agrilink.agrilink_web;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agrilink.exceptions.AuthenticationException;
import com.agrilink.models.CatalogItem;
import com.agrilink.models.Product;
import com.agrilink.models.User;
import com.agrilink.services.AuthService;
import com.agrilink.services.OrderService;
import com.agrilink.services.ProductService;
import com.agrilink.services.TransportService;

@RestController
public class WebController {
    private final AppData appData;
    private final AuthService authService;
    private final ProductService productService;
    private final OrderService orderService;
    private final TransportService transportService;
    private final List<CatalogItem> catalogItems;

    public WebController(AppData appData) {
        this.appData = appData;
        this.authService = appData.getAuthService();
        this.productService = appData.getProductService();
        this.orderService = appData.getOrderService();
        this.transportService = appData.getTransportService();
        this.catalogItems = appData.getCatalogItems();
    }

    @GetMapping("/api/catalog")
    public List<CatalogItem> catalog() {
        return catalogItems;
    }

    @GetMapping("/api/products")
    public List<Product> products() {
        return productService.getAvailableProducts();
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String id = body.get("id");
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password");
        String role = body.get("role");
        if (name == null || email == null || password == null || role == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing fields"));
        }
        if (id == null || id.isBlank()) {
            long existingCount = authService.getAllUsers().stream()
                    .filter(u -> u.getRole().equalsIgnoreCase(role))
                    .count();
            String rolePrefix = role.length() > 0 ? role.substring(0, 1).toUpperCase() : "U";
            id = rolePrefix + (existingCount + 1);
        }
        User user;
        switch (role.toUpperCase()) {
            case "FARMER":
                user = new com.agrilink.models.Farmer(id, name, email, password);
                break;
            case "CUSTOMER":
                user = new com.agrilink.models.Customer(id, name, email, password);
                break;
            default:
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid role"));
        }
        authService.registerUser(user);
        appData.saveAll();
        return ResponseEntity.ok(Map.of("status", "registered", "id", id));
    }

    @GetMapping("/api/pending-products")
    public List<Product> pendingProducts() {
        return productService.getPendingApprovalProducts();
    }

    @GetMapping("/api/stats")
    public Map<String, Object> stats() {
        return Map.of(
            "totalUsers", authService.getAllUsers().size(),
            "pendingApprovals", productService.getPendingApprovalProducts().size(),
            "totalTransactions", orderService.getAllSystemOrders().size()
        );
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        try {
            var user = authService.login(email, password);
            return ResponseEntity.ok(Map.of(
                    "userId", user.getUserId(),
                    "name", user.getName(),
                    "email", user.getEmail(),
                    "role", user.getRole()
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/api/users")
    public List<User> users() {
        return authService.getAllUsers();
    }

    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        User user = appData.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }
        if ("ADMIN".equalsIgnoreCase(user.getRole()) || "TRANSPORT".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cannot delete admin or transport users"));
        }
        boolean removed = authService.removeUser(userId);
        if (!removed) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to delete user"));
        }
        appData.saveAll();
        return ResponseEntity.ok(Map.of("status", "deleted", "userId", userId));
    }

    @GetMapping("/api/orders")
    public List<com.agrilink.models.Order> orders(@RequestParam(required = false) String customerId) {
        if (customerId != null) {
            return orderService.getOrdersByCustomer(customerId);
        }
        return orderService.getAllSystemOrders();
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@RequestParam String userId) {
        User user = appData.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }
        return ResponseEntity.ok(Map.of(
                "userId", user.getUserId(),
                "name", user.getName(),
                "email", user.getEmail(),
                "role", user.getRole(),
                "walletBalance", user.getWalletBalance()
        ));
    }

    @PostMapping("/api/user/wallet")
    public ResponseEntity<?> updateWallet(@RequestBody Map<String, Object> body) {
        String userId = (String) body.get("userId");
        Object amountObj = body.get("amount");
        String action = (String) body.get("action");

        if (userId == null || amountObj == null || action == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing wallet parameters"));
        }

        User user = appData.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        double amount;
        try {
            amount = Double.parseDouble(amountObj.toString());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid amount"));
        }

        if (amount <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Amount must be greater than zero"));
        }

        if (action.equalsIgnoreCase("WITHDRAW")) {
            if (!user.withdrawMoney(amount)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Insufficient wallet balance"));
            }
        } else {
            user.addMoney(amount);
        }

        appData.saveAll();
        return ResponseEntity.ok(Map.of("status", "updated", "walletBalance", user.getWalletBalance()));
    }

    @GetMapping("/api/ping")
    public Map<String, String> ping() {
        return Map.of("status", "ok");
    }
}
