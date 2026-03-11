package Day3_11_03;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TradingMarket {

    List<Stock> stocks;
    long tradingWindowTime;
    Queue<TradeReceipt> tradesHistory = new ConcurrentLinkedQueue<>();
    AtomicBoolean open = new AtomicBoolean(false);

    public TradingMarket(List<Stock> stocks, long tradingWindowTime) {
        this.stocks = stocks;
        this.tradingWindowTime = tradingWindowTime;
    }

    public boolean isMarketOpen() {
        return open.get();
    }


    public List<Stock> getStocks() {
        return stocks;
    }

    public TradeReceipt buyStock(User user, Stock stock, int buyQuantity){
        if (!open.get() || !stocks.contains(stock)) {
            return null;
        }

        int tries = 0;
        int maxTries = 10;

        while( tries<maxTries){
            int current = stock.getQuantity();

            if(current < buyQuantity){
                break;
            }

            int updatedQuantity = current - buyQuantity;
            if(stock.compareAndSetQuantity(current, updatedQuantity)){
                TradeReceipt tradeReceipt = new TradeReceipt(user, stock, buyQuantity, buyQuantity*stock.getPricePerStock() , LocalDateTime.now(), true, "BUY");
                tradesHistory.add(tradeReceipt);
                return tradeReceipt;
            }

            tries++;

        }

        return new TradeReceipt(user, stock, buyQuantity, 0, LocalDateTime.now(), false, "BUY");
    }

    public TradeReceipt sellStock(User user, Stock stock, int sellQuantity){

        int tries = 0;
        int maxTries = 10;

        while(tries<maxTries){
            int current = stock.getQuantity();

            int updatedQuantity = current + sellQuantity;
            if(stock.compareAndSetQuantity(current, updatedQuantity)){
                TradeReceipt tradeReceipt = new TradeReceipt(user, stock, sellQuantity, sellQuantity*stock.getPricePerStock() , LocalDateTime.now(), true, "SELL");
                tradesHistory.add(tradeReceipt);
                return tradeReceipt;
            }
            tries++;
        }
        return new TradeReceipt(user, stock, sellQuantity, 0, LocalDateTime.now(), false, "SELL");
    }

    public void open(){
        open.set(true);

        Thread thread = new Thread( () -> {
            try {
                Thread.sleep(tradingWindowTime*1000);
                open.set(false);
                System.out.println("----------------------------\n   TradingMarket Closed\n----------------------------");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();
    }
}
