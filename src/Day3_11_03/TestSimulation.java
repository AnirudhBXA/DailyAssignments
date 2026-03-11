package Day3_11_03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TestSimulation {

    public static List<User> users = new ArrayList<>();

    public static void createUsers() {
        for (int i = 1; i <= 7; i++) {
            users.add(new User("User_" + i));
        }
    }

    public static List<Stock> createStocks(){
        List<Stock> stocks = new ArrayList<>();
        stocks.add( new Stock("Apple", 200, 4343.23, "APL"));
        stocks.add( new Stock("Samsung", 250, 4187.43, "SMSG"));
        stocks.add( new Stock("RealMe", 400, 2343.23, "RLM"));
        stocks.add( new Stock("OnePlus", 350, 3343.23, "PLS"));
        stocks.add( new Stock("Nothing", 150, 1843.23, "NTG"));

        return stocks;
    }



    public static void main(String[] args) {

        createUsers();
        List<Stock> initialStocks = createStocks();
        Map<String, Integer> initialStockQuantity = initialStocks.stream()
                .collect(
                        Collectors.toMap(
                                Stock::getSymbol,
                                Stock::getQuantity
                        )
                );


        TradingMarket tradingMarket = new TradingMarket(initialStocks, 30);
        tradingMarket.open();
        ExecutorService executor = Executors.newFixedThreadPool(7);

        for (User user : users) {
            executor.submit( () -> {
                user.startTrading(tradingMarket);
            });
        }

        executor.shutdown();

        try{
            executor.awaitTermination(5, TimeUnit.MINUTES);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        SummaryGeneration.tradingMarketSummary(tradingMarket, initialStocks);

        SummaryGeneration.usersSummary(users);

        SummaryGeneration.validateStockExchange(initialStockQuantity, tradingMarket.stocks, users);

    }
}
