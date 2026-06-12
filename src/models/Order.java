package models;

import java.util.List;
import services.OrderService;

public class Order {

    private User orderingUser;
    private Product orderedProduct;
    private CreditCard paymentCard;
    private int productAmount;
    public int orderId;
    public int totalPrice;
    private static List<Order> allOrders;

    public Order(int productAmount, User orderingUser, Product orderedProduct, CreditCard paymentCard, int orderId, int totalPrice) {
        this.productAmount = productAmount;
        this.orderingUser = orderingUser;
        this.orderedProduct = orderedProduct;
        this.paymentCard = paymentCard;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public User getOrderingUser() {
        return this.orderingUser;
    }

    public void setOrderingUser(User orderingUser) {
        this.orderingUser = orderingUser;
    }

    public Product getOrderedProduct() {
        return this.orderedProduct;
    }

    public void setOrderedProduct(Product orderedProduct) {
        this.orderedProduct = orderedProduct;
    }

    public CreditCard getPaymentCard() {
        return this.paymentCard;
    }

    public void setPaymentCard(CreditCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static List<Order> getAllOrders() {
        allOrders = OrderService.getOrdersByUserId();
        return allOrders;
    }

    public static void setAllOrders(List<Order> allOrders2) {
        allOrders = allOrders2;
    }

}
