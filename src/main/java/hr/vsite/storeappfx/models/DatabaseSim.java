package hr.vsite.storeappfx.models;

import javafx.collections.FXCollections;

import java.util.List;

public class DatabaseSim {
    private static List<Product> productList = FXCollections.observableArrayList(
        new Product(1, "Laptop", "Powerful laptop with high-performance features", 999.99, 15),
            new Product(2, "Smartphone", "Latest smartphone with advanced camera technology", 699.99, 20),
            new Product(3, "Headphones", "Wireless noise-canceling headphones for immersive audio", 149.99, 30),
            new Product(4, "Smartwatch", "Fitness tracker and smartwatch with health monitoring", 129.99, 25),
            new Product(5, "TV", "4K Ultra HD Smart TV with a sleek design", 799.99, 10),
            new Product(6, "Gaming Console", "Next-gen gaming console for an ultimate gaming experience", 499.99, 12),
            new Product(7, "Camera", "Professional DSLR camera with high-resolution sensor", 899.99, 8),
            new Product(8, "Tablet", "Compact and versatile tablet for productivity on the go", 299.99, 18),
            new Product(9, "Wireless Mouse", "Ergonomic wireless mouse for comfortable computing", 29.99, 50),
            new Product(10, "External Hard Drive", "1TB external hard drive for additional storage space", 79.99, 22),
            new Product(11, "Bluetooth Speaker", "Portable Bluetooth speaker for music enthusiasts", 49.99, 40),
            new Product(12, "Fitness Tracker", "Track your fitness goals with this advanced fitness tracker", 79.99, 15),
            new Product(13, "Coffee Maker", "Automatic coffee maker for brewing your favorite coffee", 89.99, 10),
            new Product(14, "Desk Chair", "Comfortable ergonomic desk chair for long working hours", 129.99, 5),
            new Product(15, "Backpack", "Stylish and spacious backpack for everyday use", 39.99, 30),
            new Product(16, "Printer", "All-in-one printer for printing, scanning, and copying", 149.99, 8),
            new Product(17, "Wireless Keyboard", "Compact and responsive wireless keyboard", 49.99, 25),
            new Product(18, "Security Camera", "Home security camera with motion detection", 79.99, 12),
            new Product(19, "Toaster", "Modern toaster for quick and efficient toasting", 34.99, 20),
            new Product(20, "External SSD", "Fast and reliable external SSD for data storage", 129.99, 15)
    );
    public static List<Product> getProductList() {
        return productList;
    }

    private static List<Product> cartList = FXCollections.observableArrayList();
    public static List<Product> getCartList() {
        return cartList;
    }

    private static List<User> userList = FXCollections.observableArrayList(
        new User("admin", "123Java_", "admin"),
        new User("amaletic", "123Java_", "user"),
        new User("phuljek", "123Java_", "user"),
        new User("sperkov", "123Java_", "user"),
        new User("tpavic", "123Java_", "user"),
        new User("mdebeljuh", "123Java_", "user")
    );
    public static List<User> getUserList() {
        return userList;
    }

}
