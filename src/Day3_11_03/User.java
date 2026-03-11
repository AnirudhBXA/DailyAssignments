package Day3_11_03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class User {

    private String name;
    private List<TradeReceipt> tradingHistory;
    private Map<Stock, Integer> inventory;

    public User(String name) {
        this.name = name;
        this.tradingHistory = new ArrayList<TradeReceipt>();
        this.inventory = new HashMap<Stock, Integer>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TradeReceipt> getTradingHistory() {
        return tradingHistory;
    }

    public void setTradingHistory(List<TradeReceipt> tradingHistory) {
        this.tradingHistory = tradingHistory;
    }

    public Map<Stock, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<Stock, Integer> inventory) {
        this.inventory = inventory;
    }

    public void startTrading(TradingMarket tradingMarket) {

        while(tradingMarket.isMarketOpen()){

//            deciding to buy or sell a stock
            boolean buy = ThreadLocalRandom.current().nextBoolean();

            if(buy){
                List<Stock> stocks = tradingMarket.getStocks();
//                deciding which stock to buy with random
                int buyingStockIndex = ThreadLocalRandom.current().nextInt(stocks.size());
                Stock buyingStock = stocks.get(buyingStockIndex);

                if(buyingStock.getQuantity() == 0){
                    continue;
                }

                int buyingQuantity = ThreadLocalRandom.current().nextInt(1, Math.min(10, buyingStock.getQuantity() + 1));

                TradeReceipt currentTrade = tradingMarket.buyStock(this, buyingStock, buyingQuantity);

                if(currentTrade == null){
                    System.out.println("NO TRADE RECIPIENT RECEIVED");
                    continue;
                }

                if(currentTrade.isTradeSuccess()){
                    tradingHistory.add(currentTrade);
                    System.out.println(this.name + " BOUGHT " + currentTrade.getQuantity() + " OF " + currentTrade.getStock().getSymbol() + " STOCK");
                    if (inventory.containsKey(currentTrade.getStock())) {
                        inventory.put(currentTrade.getStock(), inventory.get(currentTrade.getStock()) + currentTrade.getQuantity());
                    } else {
                        inventory.put(currentTrade.getStock(), currentTrade.getQuantity());
                    }
                } else{
                    System.out.println("NO TRADE HAPPENED");
                }
            } else{

                if(inventory.isEmpty()){
                    continue;
                }
                List<Stock> stocks = inventory.keySet().stream().toList();

                int sellingStockIndex = ThreadLocalRandom.current().nextInt(stocks.size());

                Stock sellingStock = stocks.get(sellingStockIndex);

                if(inventory.get(sellingStock) == 0 ){
                    System.out.println("NO STOCKS TO SELL");
                    continue;
                }

                int sellingStockQuantity = ThreadLocalRandom.current().nextInt(1, Math.min(10, inventory.get(sellingStock)) + 1);

                TradeReceipt currentTrade = tradingMarket.sellStock(this, sellingStock, sellingStockQuantity);

                if(currentTrade == null){
                    System.out.println("NO TRADE RECIPIENT RECEIVED");
                }

                if(currentTrade.isTradeSuccess()){
                    tradingHistory.add(currentTrade);
                    System.out.println(this.name + " SOLD " + currentTrade.getQuantity() + " OF " + currentTrade.getStock().getSymbol() + " STOCK");

                    inventory.put(currentTrade.getStock(), inventory.get(currentTrade.getStock()) - currentTrade.getQuantity());

                } else {
                    System.out.println("NO TRADE HAPPENED");
                }
            }

            try{
                Thread.sleep(50);
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }

        }

    }
}
