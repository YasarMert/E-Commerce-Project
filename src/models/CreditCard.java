package models;

import java.time.LocalDate;

public class CreditCard {

    private int cardId;
    private int cardOwnerId;
    private String cardNumber;
    private String cardOwner;
    private String securityCode;
    private LocalDate expirationDate;

    public CreditCard(int cardId, int cardOwnerId, String cardNumber, String cardOwner, String securityCode, LocalDate expirationDate) {
        this.cardId = cardId;
        this.cardOwnerId = cardOwnerId;
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.securityCode = securityCode;
        this.expirationDate = expirationDate;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCardOwnerId() {
        return cardOwnerId;
    }

    public void setCardOwnerId(int cardOwnerId) {
        this.cardOwnerId = cardOwnerId;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardOwner() {
        return this.cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getSecurityCode() {
        return this.securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

}
