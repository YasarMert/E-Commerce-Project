package services;

import Helpers.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.CreditCard;
import models.Order;
import models.Product;
import models.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JTable;

public class OrderService {

    private static final String DB_URL = "jdbc:sqlite:oop.db";

    public static void saveOrderToDatabase(int userId, JTable cartTable) {

        String insertSQL = "INSERT INTO orders (user_id, product_id, product_amount, order_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            conn.setAutoCommit(false);

            for (int i = 0; i < cartTable.getRowCount(); i++) {
                Object amountObj = cartTable.getValueAt(i, 0);
                Object productNameObj = cartTable.getValueAt(i, 1);
                Object priceObj = cartTable.getValueAt(i, 2);

                if (amountObj == null || productNameObj == null || priceObj == null) {
                    continue;  // Boş satırları atla
                }

                int amount;
                try {
                    amount = Integer.parseInt(amountObj.toString());
                } catch (NumberFormatException e) {
                    continue; // Geçersiz miktar atla
                }

                String productName = productNameObj.toString();

                String priceString;
                int price;
                try {
                    priceString = priceObj.toString().replace("$", "").trim();
                    price = Integer.parseInt(priceString);
                } catch (NumberFormatException e) {
                    continue; // Geçersiz fiyat atla
                }

                int productId = getProductIdByName(productName);
                if (productId == -1) {
                    System.out.println("Ürün bulunamadı: " + productName);
                    continue;
                }

                int totalPrice = amount * price;

                pstmt.setInt(1, userId);
                pstmt.setInt(2, productId);
                pstmt.setInt(3, amount);
                pstmt.setInt(4, totalPrice);

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();

            System.out.println("Sipariş başarıyla kaydedildi.");

        } catch (SQLException e) {
            System.out.println("Veritabanı kaydetme hatası: " + e.getMessage());
        }
    }

    private static int getProductIdByName(String productName) {
        for (Product p : Product.getProductList()) {
            if (p.getProductName().equalsIgnoreCase(productName)) {
                return p.getProduct_id();
            }
        }
        return -1;
    }

    public static List<Order> getOrdersByUserId() {
        User currentUser = Session.getCurrentUser();
        if (currentUser == null) {
            return Collections.emptyList();  // Eğer oturumda kullanıcı yoksa boş liste döndür
        }
        int userId = currentUser.getId();

        String sql = "SELECT "
                + "orders.order_id, orders.order_price, orders.product_amount, "
                + "users.user_id, users.user_name, users.name, users.surname, users.birthday, users.password, users.email, users.home_address, users.work_address, "
                + "products.product_id, products.product_name, products.color, products.category, products.stock_information, products.weight, products.description, products.price, "
                + "credit_cards.card_id, credit_cards.card_number, credit_cards.card_owner, credit_cards.security_code, credit_cards.expiration_date "
                + "FROM orders "
                + "JOIN users ON orders.user_id = users.user_id "
                + "JOIN products ON orders.product_id = products.product_id "
                + "LEFT JOIN credit_cards ON credit_cards.user_id = users.user_id "
                + "WHERE orders.user_id = ?";

        ArrayList<Order> orderList = new ArrayList<>();

        if (currentUser.getOrderedProducts() == null) {
            currentUser.setOrderedProducts(new ArrayList<>());
        }

        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("color"),
                        rs.getString("category"),
                        rs.getInt("stock_information"),
                        rs.getString("weight"),
                        rs.getString("description"),
                        rs.getInt("price")
                );
                int productAmount = rs.getInt("product_amount");
                product.setAmountInCart(productAmount);

                CreditCard card = null;
                int cardId = rs.getInt("card_id");
                if (!rs.wasNull()) {
                    String expDateStr = rs.getString("expiration_date");
                    LocalDate expirationDate = null;
                    if (expDateStr != null && !expDateStr.isEmpty()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                        YearMonth ym = YearMonth.parse(expDateStr, formatter);
                        expirationDate = ym.atDay(1);
                    }

                    card = new CreditCard(
                            cardId,
                            rs.getInt("user_id"),
                            rs.getString("card_number"),
                            rs.getString("card_owner"),
                            rs.getString("security_code"),
                            expirationDate
                    );
                }

                // Sipariş oluştur
                Order order = new Order(
                        rs.getInt("product_amount"),
                        currentUser,
                        product,
                        card,
                        rs.getInt("order_id"),
                        rs.getInt("order_price")
                );

                // Kullanıcı orderedProducts listesine ürünü ekle
                currentUser.getOrderedProducts().add(product);
                
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

}
