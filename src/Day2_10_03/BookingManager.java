package Day2_10_03;

import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingManager {

    private AtomicInteger ticketsBooked = new AtomicInteger(0);
    private int totalTickets;
    private Semaphore bookingResource;

    public BookingManager(int totalTickets) {
        this.totalTickets = totalTickets;
        bookingResource = new Semaphore(totalTickets);
    }

    private boolean payment() throws InterruptedException {
        Thread.sleep(5);

        int rand = ThreadLocalRandom.current().nextInt(1, 11);

        if(rand == 5){
            return false;
        }
        return true;
    }

    public TicketBookingResponse bookTicket(String user ,int noOfTickets) {

        if(noOfTickets > 3) {
            return new TicketBookingResponse(false,user,"Should select less than 3 tickets!");
        }

        boolean areSeatsAvailable = false;

        try{
            areSeatsAvailable = bookingResource.tryAcquire(noOfTickets, 10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new TicketBookingResponse(false, user, "Booking interrupted");
        }

        if(areSeatsAvailable) {

            boolean isPaymentDone = false;
            try{
                isPaymentDone = payment();
            } catch (InterruptedException e){
                bookingResource.release(noOfTickets);
                Thread.currentThread().interrupt();
                return new TicketBookingResponse(false, user, "Booking interrupted");
            }

            if(!isPaymentDone) {
                bookingResource.release(noOfTickets);
                return new TicketBookingResponse(false,user,user+" : Payment failed!");
            }
            try{
                ticketsBooked.addAndGet(noOfTickets);
                return new TicketBookingResponse(true,user,user+" has booked "+noOfTickets+" tickets Successfully!");
            } catch (Exception e){
                bookingResource.release(noOfTickets);
                return new TicketBookingResponse(false, user, "Booking interrupted");
            }
        }
        else {
            if(bookingResource.availablePermits() < noOfTickets && bookingResource.availablePermits()!=0) {
                return new TicketBookingResponse(false,user,user+" Not enough tickets!");
            }

            return new TicketBookingResponse(false,user,user+" :( House-Full! All the tickets and sold out");
        }
    }

    public int getTotalTicketsBooked() {
        return ticketsBooked.get();
    }
}
