package Helpers;

import models.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import models.CreditCard;

public class Session {

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void clearCurrentUser() {
        currentUser = null;
    }

    public static boolean refreshCurrentUser() {
        if (currentUser == null) {
            return false;
        }
        String url = "jdbc:sqlite:oop.db";
        String sqlUser = "SELECT * FROM users WHERE user_id = ?";
        String sqlCards = "SELECT * FROM credit_cards WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(url)) {

            try (PreparedStatement pstmtUser = conn.prepareStatement(sqlUser)) {
                pstmtUser.setInt(1, currentUser.getId());
                ResultSet rsUser = pstmtUser.executeQuery();

                if (rsUser.next()) {
                    currentUser.setHomeAddress(rsUser.getString("home_address"));
                    currentUser.setWorkAddress(rsUser.getString("work_address"));

                } else {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean refreshCurrentUserCreditCard() {
        if (currentUser == null) {
            return false;
        }

        String url = "jdbc:sqlite:oop.db";
        String sqlCard = "SELECT card_id, user_id, card_number, card_owner, security_code, expiration_date FROM credit_cards WHERE user_id = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmtCard = conn.prepareStatement(sqlCard)) {

            pstmtCard.setInt(1, currentUser.getId());
            ResultSet rsCard = pstmtCard.executeQuery();

            if (rsCard.next()) {
                int cardId = rsCard.getInt("card_id");
                int cardOwnerId = rsCard.getInt("user_id");
                String cardNumber = rsCard.getString("card_number");
                String cardOwner = rsCard.getString("card_owner");
                String securityCode = rsCard.getString("security_code");
                String expirationDateStr = rsCard.getString("expiration_date");

                // expirationDate String -> YearMonth dönüşümü
                LocalDate expirationDate = null;
                if (expirationDateStr != null && !expirationDateStr.isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                    YearMonth ym = YearMonth.parse(expirationDateStr, formatter);
                    expirationDate = ym.atDay(1); // Ayın 1. günü olarak LocalDate oluştur
                }

                CreditCard card = new CreditCard(cardId, cardOwnerId, cardNumber, cardOwner, securityCode, expirationDate);
                currentUser.setCreditCard_id(cardId);

                return true;
            } else {
                return false; // Kullanıcıya ait kart bulunamadı
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
