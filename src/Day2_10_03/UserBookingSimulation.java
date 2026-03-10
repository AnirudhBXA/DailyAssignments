package Day2_10_03;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class UserBookingSimulation {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(6);


        BookingManager bookingManager = new BookingManager(80);
        List<Future<TicketBookingResponse>> futureList = new ArrayList<>();
        for(int i = 1; i <= 200; i++){
            final String user = "user_"+i;
            futureList.add(
                    executorService.submit( () -> {
                        int randomTickets = ThreadLocalRandom.current().nextInt(1,4);
                        return bookingManager.bookTicket(user ,randomTickets);
                    })
            );
        }

        executorService.shutdown();

        for(Future<TicketBookingResponse> future : futureList){
            TicketBookingResponse result = future.get();
            System.out.println(result.getMessage());
        }


        System.out.println("Total tickets booked: " + bookingManager.getTotalTicketsBooked());

        long endTime = System.currentTimeMillis();

        System.out.println("Total time taken : " + (endTime - startTime) + " ms");
    }
}
