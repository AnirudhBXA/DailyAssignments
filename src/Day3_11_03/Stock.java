package Day3_11_03;

import java.util.concurrent.atomic.AtomicInteger;

public class Stock {
    private String stockName;
    private AtomicInteger quantity;
    private double pricePerStock;
    private String symbol;
    public Stock(String stockName, int quantity, double pricePerStock, String symbol) {
        this.stockName = stockName;
        this.quantity = new AtomicInteger(quantity);
        this.pricePerStock = pricePerStock;
        this.symbol = symbol;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public boolean compareAndSetQuantity(int oldQuantity, int newQuantity){
        return this.quantity.compareAndSet(oldQuantity, newQuantity);
    }

    public double getPricePerStock() {
        return pricePerStock;
    }

    public void setPricePerStock(double pricePerStock) {
        this.pricePerStock = pricePerStock;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;

        return stockName.equals(stock.stockName) && symbol.equals(stock.symbol);
    }
}
