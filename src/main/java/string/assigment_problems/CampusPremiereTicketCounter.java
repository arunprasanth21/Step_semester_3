import java.util.*;

interface Seat {

    String getSeatNumber();

    double getPrice();
}

class RegularSeat implements Seat {

    private String seatNumber;

    public RegularSeat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return 150.00;
    }
}

class PremiumSeat implements Seat {

    private String seatNumber;

    public PremiumSeat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return 250.00;
    }
}

class ReclinerSeat implements Seat {

    private String seatNumber;

    public ReclinerSeat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return 400.00;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}

class Customer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Show {

    private String showTime;

    private Map<String, Seat> seats;

    private Set<String> bookedSeats;

    public Show(String showTime) {

        this.showTime = showTime;

        seats = new LinkedHashMap<>();
        bookedSeats = new HashSet<>();
    }

    public String getShowTime() {
        return showTime;
    }

    public void addSeat(Seat seat) {
        seats.put(
            seat.getSeatNumber(),
            seat
        );
    }

    public boolean isSeatAvailable(String seatNumber) {

        return seats.containsKey(seatNumber)
            && !bookedSeats.contains(seatNumber);
    }

    public Seat getSeat(String seatNumber) {
        return seats.get(seatNumber);
    }

    public boolean bookSeat(String seatNumber) {

        if (!isSeatAvailable(seatNumber)) {
            return false;
        }

        bookedSeats.add(seatNumber);

        return true;
    }

    public void releaseSeat(String seatNumber) {
        bookedSeats.remove(seatNumber);
    }
}

class Booking {

    private Customer customer;
    private Show show;
    private List<Seat> bookedSeats;
    private boolean cancelled;

    public Booking(
            Customer customer,
            Show show,
            List<Seat> bookedSeats) {

        this.customer = customer;
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.cancelled = false;
    }

    public double calculateTotal() {

        double total = 0;

        for (Seat seat : bookedSeats) {
            total += seat.getPrice();
        }

        return total;
    }

    public void cancel(boolean showStarted) {

        if (cancelled) {
            System.out.println(
                "Booking is already cancelled."
            );
            return;
        }

        if (showStarted) {
            System.out.println(
                "Cannot cancel: Show has already started."
            );
            return;
        }

        for (Seat seat : bookedSeats) {
            show.releaseSeat(
                seat.getSeatNumber()
            );
        }

        cancelled = true;

        System.out.printf(
            "%s's booking cancelled.%n",
            customer.getName()
        );

        System.out.print(
            "Seats "
        );

        for (int i = 0; i < bookedSeats.size(); i++) {

            System.out.print(
                bookedSeats.get(i).getSeatNumber()
            );

            if (i < bookedSeats.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println(" released.");
    }
}

class TicketCounter {

    private static final int MAX_SEATS = 6;

    public Booking book(
            Customer customer,
            Show show,
            String... seatNumbers) {

        if (seatNumbers.length == 0) {

            System.out.println(
                "Booking failed: No seats selected."
            );

            return null;
        }

        if (seatNumbers.length > MAX_SEATS) {

            System.out.println(
                "Booking failed: Maximum 6 seats allowed."
            );

            return null;
        }

        for (String seatNumber : seatNumbers) {

            if (!show.isSeatAvailable(seatNumber)) {

                System.out.println(
                    "Seat " + seatNumber +
                    " is already booked for this show."
                );

                return null;
            }
        }

        List<Seat> selectedSeats =
            new ArrayList<>();

        for (String seatNumber : seatNumbers) {

            show.bookSeat(seatNumber);

            selectedSeats.add(
                show.getSeat(seatNumber)
            );
        }

        Booking booking =
            new Booking(
                customer,
                show,
                selectedSeats
            );

        System.out.print(
            "Booking confirmed for " +
            customer.getName() +
            ": "
        );

        for (int i = 0; i < selectedSeats.size(); i++) {

            System.out.print(
                selectedSeats.get(i).getSeatNumber()
            );

            if (i < selectedSeats.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(
            ". Total: ₹%.2f.%n",
            booking.calculateTotal()
        );

        return booking;
    }
}

public class CampusPremiereTicketCounter {

    public static void main(String[] args) {

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Show show =
            new Show("7 PM");

        show.addSeat(
            new RegularSeat("A1")
        );

        show.addSeat(
            new RegularSeat("A2")
        );

        show.addSeat(
            new PremiumSeat("F5")
        );

        show.addSeat(
            new ReclinerSeat("R1")
        );

        TicketCounter counter =
            new TicketCounter();

        Booking ashaBooking =
            counter.book(
                asha,
                show,
                "A1",
                "A2",
                "F5"
            );

        counter.book(
            ravi,
            show,
            "A2"
        );

        Booking raviBooking =
            counter.book(
                ravi,
                show,
                "R1"
            );

        ashaBooking.cancel(false);

        counter.book(
            neha,
            show,
            "A2"
        );
    }
}
