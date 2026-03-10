package Day2_10_03;

public class TicketBookingResponse {

    private boolean booked = false;
    private String user;
    private String message;

    public TicketBookingResponse(boolean booked, String user, String message) {
        this.booked = booked;
        this.user = user;
        this.message = message;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
