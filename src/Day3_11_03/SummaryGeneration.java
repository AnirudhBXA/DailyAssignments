package Day3_11_03;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SummaryGeneration {

    public static void tradingMarketSummary(TradingMarket tradingMarket, List<Stock> initialStocks) {

        System.out.println("---------------\nMarket Summary\n-------------");
        System.out.println("\ncurrent state : ");
        tradingMarket.stocks.stream().forEach(stock -> {
            System.out.println(stock.getSymbol() + " : " + stock.getQuantity());
        });

        Map<String, Long> tradingCount = tradingMarket.tradesHistory.stream()
                .filter( trade -> trade.isTradeSuccess())
                .collect(
                        Collectors.groupingBy(
                                trade -> trade.getStock().getSymbol(),
                                Collectors.counting()
                        )
                );
        System.out.println("\nHighest frequent stock : ");
        tradingCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println);

        System.out.println("\nLeast frequent stock : ");
        tradingCount.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .ifPresent(System.out::println);

        System.out.println("\nEach Stock summary : ");
        tradingMarket.stocks.stream().forEach(stock -> {
            System.out.println(stock.getSymbol() + " total exchanges -> " + tradingCount.get(stock.getSymbol()));
//            System.out.println();
        });

    }

    public static void usersSummary(List<User> users) {

        System.out.println("---------------\nMarket Summary\n-------------");
        users.stream().forEach(user -> {

            System.out.println(user.getName());

            System.out.println("\nfinal state : ");
            user.getInventory().entrySet().stream().forEach(entry -> {
                System.out.println(entry.getKey().getSymbol() + " -> " + entry.getValue());
            });

            System.out.println( "\nNo.of tradings tried : " + user.getTradingHistory().size());
//            user.getInventory().entrySet().stream().forEach(entry -> {
//                System.out.println(entry.getKey().getSymbol() + " : " + entry.getValue());
//            });
        });
    }

    public static void validateStockExchange(Map<String, Integer> initialStocks, List<Stock> marketInventory, List<User> users) {

        marketInventory.stream().forEach(stock -> {

            AtomicInteger activeValue = new AtomicInteger();

            activeValue.addAndGet(stock.getQuantity());

            users.stream()
                    .forEach(user -> activeValue.addAndGet(user.getInventory().get(stock)));

            int initialValue = initialStocks.get(stock.getSymbol());

            System.out.println(stock.getSymbol() + " : " + initialValue + " -> " + activeValue.get());
            if(activeValue.get() != initialValue) {
                System.out.println(stock.getSymbol() + " Validation failed! Exchange values altered");
            } else if(activeValue.get() == initialValue) {
                System.out.println(stock.getSymbol() + " Validation successful!");
            }

        });

    }
}
