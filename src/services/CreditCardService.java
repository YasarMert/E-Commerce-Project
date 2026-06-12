/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import Helpers.Session;
import models.CreditCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreditCardService {

    public int cardId;
    public String cardNumber;
    public String cardOwner;
    public String securityCode;
    public String expirationDate;

    private static final String DB_URL = "jdbc:sqlite:oop.db";

    public void loadCreditCardInfoToVariables() {
        int currentUserId = Session.getCurrentUser().getId();
        String sql = "SELECT card_id, card_number, card_owner, security_code, expiration_date FROM credit_cards WHERE user_id = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, currentUserId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.cardId = rs.getInt("card_id");
                this.cardNumber = rs.getString("card_number");
                this.cardOwner = rs.getString("card_owner");
                this.securityCode = rs.getString("security_code");
                this.expirationDate = rs.getString("expiration_date"); // LocalDate değil String olarak çekiyoruz
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
