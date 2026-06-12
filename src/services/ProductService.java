package services;

import java.util.List;
import models.Product;
import java.util.ArrayList;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductService {

    private static final String DB_URL = "jdbc:sqlite:oop.db";

    public static List<Product> getAllProducts() {
        String query = "SELECT product_id, product_name, color, category, stock_information, weight, description, price FROM products";
        List<Product> products = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

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
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void updateProductStock(int productId, int newStock) {
        String updateQuery = "UPDATE products SET stock_information = ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {

                Product.setProductList(ProductService.getAllProducts());
            } else {
                System.out.println("Stock could not be updated. Product ID not found: " + productId);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
