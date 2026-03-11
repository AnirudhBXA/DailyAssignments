package Day3_11_03;

import java.time.LocalDateTime;

public class TradeReceipt {

    private User user;
    private Stock stock;
    private int quantity;
    private double price;
    private LocalDateTime tradeTime;
    private boolean tradeSuccess;
    private String action;

    public TradeReceipt(User user, Stock stock, int quantity, double price, LocalDateTime tradeTime, boolean tradeSuccess,  String action) {
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.tradeTime = tradeTime;
        this.tradeSuccess = tradeSuccess;
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(LocalDateTime tradeTime) {
        this.tradeTime = tradeTime;
    }

    public boolean isTradeSuccess() {
        return tradeSuccess;
    }

    public void setTradeSuccess(boolean tradeSuccess) {
        this.tradeSuccess = tradeSuccess;
    }
}
