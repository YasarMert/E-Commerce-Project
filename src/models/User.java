package models;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {

    private int id;
    private String userName;
    private String name;
    private String surname;
    private LocalDate birthday;
    private String password;
    private String email;
    private String homeAddress;
    private String workAddress;
    private int creditCard_id = 0;

    private ArrayList<Product> favoriteProducts;
    private ArrayList<Product> orderedProducts;

    public User(int id, String userName, String name, String surname, LocalDate birthday, String password, String email, String homeAddress, String workAddress, ArrayList<Product> favoriteProducts, ArrayList<Product> orderedProducts, int creditCard_id) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.password = password;
        this.email = email;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
        this.favoriteProducts = favoriteProducts;
        this.orderedProducts = orderedProducts;
        this.creditCard_id = creditCard_id;
    }

    public int getCreditCard_id() {
        return this.creditCard_id;
    }

    public void setCreditCard_id(int creditCard_id) {
        this.creditCard_id = creditCard_id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkAddress() {
        return this.workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public ArrayList<Product> getFavoriteProducts() {
        return this.favoriteProducts;
    }

    public void setFavoriteProducts(ArrayList<Product> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    public ArrayList<Product> getOrderedProducts() {
        return this.orderedProducts;
    }

    public void setOrderedProducts(ArrayList<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

}
