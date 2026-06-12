package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Helpers.Session;
import models.CreditCard;
import models.Product;
import models.User;

public class UserService {

    static String DB_URL = "jdbc:sqlite:oop.db";

    public static boolean isProductFavorited(int productId) {
        int userId = Session.getCurrentUser() != null ? Session.getCurrentUser().getId() : 0;
        String sql = "SELECT 1 FROM favorite_products WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addFavoriteProduct(int productId) {
        int userId = Session.getCurrentUser().getId();

        String sql = "INSERT INTO favorite_products (user_id, product_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();

            Product product = Product.getProductById(productId);
            if (product != null) {
                Session.getCurrentUser().getFavoriteProducts().add(product);
            }
            System.out.println("Favoriye eklendi.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFavoriteProduct(int productId) {
        int userId = Session.getCurrentUser().getId();

        String sql = "DELETE FROM favorite_products WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
            System.out.println("Favoriden kaldırıldı.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateUserAddresses(int userId, String homeAddress, String workAddress) {
        String sql = "UPDATE users SET home_address = ?, work_address = ? WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, homeAddress);
            pstmt.setString(2, workAddress);
            pstmt.setInt(3, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addCreditCard(String cardNumber, String cardOwner, String securityCode, String expirationDate) {
        int user_id = Session.getCurrentUser().getId();
        String updateSql = "UPDATE credit_cards SET card_number = ?, card_owner = ?, security_code = ?, expiration_date = ? WHERE user_id = ?";
        String insertSql = "INSERT INTO credit_cards (user_id, card_number, card_owner, security_code, expiration_date) VALUES (?, ?, ?, ?, ?)";
       
        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, cardNumber);
                updateStmt.setString(2, cardOwner);
                updateStmt.setString(3, securityCode);
                updateStmt.setString(4, expirationDate);
                updateStmt.setInt(5, user_id);

                int affectedRows = updateStmt.executeUpdate();
                if (affectedRows > 0) {
                    return true;
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, user_id);
                insertStmt.setString(2, cardNumber);
                insertStmt.setString(3, cardOwner);
                insertStmt.setString(4, securityCode);
                insertStmt.setString(5, expirationDate);

                int insertedRows = insertStmt.executeUpdate();
                return insertedRows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public CreditCard getCreditCardForCurrentUser() {
        int currentUserId = Session.getCurrentUser().getId();
        String sql = "SELECT card_id, user_id, card_number, card_owner, security_code, expiration_date FROM credit_cards WHERE user_id = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, currentUserId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int cardId = rs.getInt("card_id");
                int cardOwnerId = rs.getInt("user_id");
                String cardNumber = rs.getString("card_number");
                String cardOwner = rs.getString("card_owner");
                String securityCode = rs.getString("security_code");
                String expirationDateStr = rs.getString("expiration_date");
                LocalDate expirationDate = LocalDate.parse(expirationDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                User currentUser = Session.getCurrentUser();

                currentUser.setCreditCard_id(cardId);
                return new CreditCard(cardId, cardOwnerId, cardNumber, cardOwner, securityCode, expirationDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
