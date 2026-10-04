import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class Room {
    private int roomNumber;
    private String type;
    private List<Reservation> reservations = new ArrayList<>();

    public Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public abstract double calculatePrice(long nights);

    public boolean isAvailable(
            LocalDate startDate,
            LocalDate endDate) {

        for (Reservation reservation : reservations) {
            if (reservation.isActive() &&
                startDate.isBefore(reservation.getEndDate()) &&
                endDate.isAfter(reservation.getStartDate())) {

                return false;
            }
        }

        return true;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }
}

class StandardRoom extends Room {
    public StandardRoom(int roomNumber) {
        super(roomNumber, "Standard Room");
    }

    public double calculatePrice(long nights) {
        return nights * 100;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(int roomNumber) {
        super(roomNumber, "Deluxe Room");
    }

    public double calculatePrice(long nights) {
        return nights * 180;
    }
}

class Suite extends Room {
    public Suite(int roomNumber) {
        super(roomNumber, "Suite");
    }

    public double calculatePrice(long nights) {
        return nights * 300;
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

class Reservation {
    private Customer customer;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate cancellationDeadline;
    private boolean active;

    public Reservation(
            Customer customer,
            Room room,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate cancellationDeadline) {

        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancellationDeadline = cancellationDeadline;
        this.active = true;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public double getPrice() {
        long nights =
                ChronoUnit.DAYS.between(startDate, endDate);

        return room.calculatePrice(nights);
    }

    public boolean cancel(LocalDate currentDate) {
        if (!active) {
            return false;
        }

        if (currentDate.isAfter(cancellationDeadline)) {
            return false;
        }

        active = false;
        return true;
    }
}

class HotelBookingService {
    private List<Reservation> reservations =
            new ArrayList<>();

    public void checkAvailability(
            Room room,
            LocalDate startDate,
            LocalDate endDate) {

        if (room.isAvailable(startDate, endDate)) {
            System.out.println(
                    room.getType() +
                    " " +
                    room.getRoomNumber() +
                    " is available from " +
                    startDate +
                    " to " +
                    endDate +
                    ".");
        } else {
            System.out.println(
                    room.getType() +
                    " " +
                    room.getRoomNumber() +
                    " is not available from " +
                    startDate +
                    " to " +
                    endDate +
                    ".");
        }
    }

    public Reservation reserve(
            Customer customer,
            Room room,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate cancellationDeadline) {

        if (!startDate.isBefore(endDate)) {
            System.out.println("Invalid reservation dates.");
            return null;
        }

        if (!room.isAvailable(startDate, endDate)) {
            System.out.println(
                    room.getType() +
                    " " +
                    room.getRoomNumber() +
                    " is not available.");
            return null;
        }

        Reservation reservation =
                new Reservation(
                        customer,
                        room,
                        startDate,
                        endDate,
                        cancellationDeadline);

        reservations.add(reservation);
        room.addReservation(reservation);

        System.out.println(
                "Reservation confirmed for " +
                customer.getName() +
                ", " +
                room.getType() +
                " " +
                room.getRoomNumber() +
                " (" +
                startDate +
                " to " +
                endDate +
                ").");

        System.out.printf(
                "Price: $%.2f%n",
                reservation.getPrice());

        return reservation;
    }

    public void cancel(
            Reservation reservation,
            LocalDate currentDate) {

        if (reservation.cancel(currentDate)) {
            System.out.println(
                    "Reservation for " +
                    reservation.getCustomer().getName() +
                    ", " +
                    reservation.getRoom().getType() +
                    " " +
                    reservation.getRoom().getRoomNumber() +
                    " (" +
                    reservation.getStartDate() +
                    " to " +
                    reservation.getEndDate() +
                    ") cancelled successfully.");
        } else {
            System.out.println(
                    "Reservation cannot be cancelled.");
        }
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        HotelBookingService service =
                new HotelBookingService();

        Customer customerA =
                new Customer("Customer A");

        Customer customerB =
                new Customer("Customer B");

        Customer customerC =
                new Customer("Customer C");

        Room standard101 =
                new StandardRoom(101);

        Room deluxe201 =
                new DeluxeRoom(201);

        LocalDate jan1 =
                LocalDate.of(2027, 1, 1);

        LocalDate jan5 =
                LocalDate.of(2027, 1, 5);

        LocalDate jan3 =
                LocalDate.of(2027, 1, 3);

        LocalDate jan7 =
                LocalDate.of(2027, 1, 7);

        service.checkAvailability(
                standard101,
                jan1,
                jan5);

        Reservation reservationA =
                service.reserve(
                        customerA,
                        standard101,
                        jan1,
                        jan5,
                        LocalDate.of(2026, 12, 30));

        service.reserve(
                customerB,
                standard101,
                jan3,
                jan7,
                LocalDate.of(2027, 1, 1));

        service.cancel(
                reservationA,
                LocalDate.of(2026, 12, 29));

        service.reserve(
                customerC,
                deluxe201,
                LocalDate.of(2027, 2, 10),
                LocalDate.of(2027, 2, 12),
                LocalDate.of(2027, 2, 5));
    }
}
