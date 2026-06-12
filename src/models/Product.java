package models;

import services.ProductService;
import java.util.List;

public class Product {

    private int product_id;
    private String productName;
    private String color;
    private String category;
    private int stockInformation;
    private String weight;
    private String description;
    private int price;
    private static List<Product> productList;
    private int amountInCart;

    public Product(int product_id, String productName, String color, String category, int stockInformation, String weight, String description, int price) {
        this.product_id = product_id;
        this.productName = productName;
        this.color = color;
        this.category = category;
        this.stockInformation = stockInformation;
        this.weight = weight;
        this.description = description;
        this.price = price;
        amountInCart = 0;
    }

    public static Product getProductById(int id) {
        for (Product p : productList) {
            if (p.getProduct_id()== id) {
                return p;
            }
        }
        return null;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockInformation() {
        return this.stockInformation;
    }

    public void setStockInformation(int stockInformation) {
        this.stockInformation = stockInformation;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void createProductList() {
        productList = ProductService.getAllProducts();
    }

    public static List<Product> getProductList() {
        return productList;
    }

    public static void setProductList(List<Product> productLists) {
        productList = productLists;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAmountInCart() {
        return this.amountInCart;
    }

    public void setAmountInCart(int amountInCart) {
        this.amountInCart = amountInCart;
    }

    public void printAllAttributes() {
        System.out.println(this.productName);
        System.out.println(this.color);
        System.out.println(this.category);
        System.out.println(this.stockInformation);
        System.out.println(this.weight);
        System.out.println(this.description);
    }

}
