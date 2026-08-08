package com.agrilink.agrilink_web;

import java.util.List;

import org.springframework.stereotype.Component;

import com.agrilink.data.DataManager;
import com.agrilink.interfaces.INotificationService;
import com.agrilink.models.Admin;
import com.agrilink.models.CatalogItem;
import com.agrilink.models.Product;
import com.agrilink.models.TransportManager;
import com.agrilink.models.User;
import com.agrilink.services.AuthService;
import com.agrilink.services.OrderService;
import com.agrilink.services.ProductService;
import com.agrilink.services.SystemNotificationService;
import com.agrilink.services.TransportService;

@Component
public class AppData {
    private final AuthService authService;
    private final ProductService productService;
    private final OrderService orderService;
    private final TransportService transportService;
    private final List<CatalogItem> catalogItems;

    public AppData() {
        List<User> loadedUsers = DataManager.loadList("users.dat");
        if (loadedUsers.isEmpty()) {
            loadedUsers.add(new Admin("A1", "Super Admin", "admin@agrilink.com", "admin123"));
            loadedUsers.add(new TransportManager("T1", "Transport Driver", "driver@agrilink.com", "driver123"));
        }

        List<Product> loadedProducts = DataManager.loadList("products.dat");
        List<com.agrilink.models.Order> loadedOrders = DataManager.loadList("orders.dat");
        List<CatalogItem> loadedCatalog = DataManager.loadList("catalog.dat");

        INotificationService notificationService = new SystemNotificationService();
        this.authService = new AuthService(loadedUsers);
        this.productService = new ProductService(notificationService, loadedProducts);
        this.orderService = new OrderService(loadedOrders);
        this.transportService = new TransportService(orderService);
        this.catalogItems = loadedCatalog;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public TransportService getTransportService() {
        return transportService;
    }

    public List<CatalogItem> getCatalogItems() {
        return catalogItems;
    }

    public User getUserById(String id) {
        return authService.getUserById(id);
    }

    public void saveAll() {
        DataManager.saveList(authService.getAllUsers(), "users.dat");
        DataManager.saveList(productService.getAllProducts(), "products.dat");
        DataManager.saveList(orderService.getAllSystemOrders(), "orders.dat");
        DataManager.saveList(catalogItems, "catalog.dat");
    }
}
