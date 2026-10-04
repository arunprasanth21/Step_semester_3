import java.util.*;

abstract class Vehicle {
    private String id;
    private String type;
    private boolean available;

    public Vehicle(String id, String type) {
        this.id = id;
        this.type = type;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class Sedan extends Vehicle {
    public Sedan(String id) {
        super(id, "Sedan");
    }

    public double calculateCharge(int days) {
        return days * 50;
    }
}

class SUV extends Vehicle {
    public SUV(String id) {
        super(id, "SUV");
    }

    public double calculateCharge(int days) {
        return days * 80;
    }
}

class Truck extends Vehicle {
    public Truck(String id) {
        super(id, "Truck");
    }

    public double calculateCharge(int days) {
        return days * 100;
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

class Rental {
    private Vehicle vehicle;
    private Customer customer;
    private int days;
    private boolean active;

    public Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
        this.active = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getCharge() {
        return vehicle.calculateCharge(days);
    }

    public void closeRental() {
        active = false;
        vehicle.setAvailable(true);
    }

    public boolean isActive() {
        return active;
    }
}

class RentalService {
    private List<Rental> rentals = new ArrayList<>();

    public void rentVehicle(Customer customer, Vehicle vehicle, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getId() + " is currently unavailable.");
            return;
        }

        if (days <= 0) {
            System.out.println("Invalid rental duration.");
            return;
        }

        Rental rental = new Rental(vehicle, customer, days);
        rentals.add(rental);
        vehicle.setAvailable(false);

        System.out.println(vehicle.getId() + " rented successfully by " + customer.getName() + ".");
        System.out.printf("Rental charge: $%.2f%n", rental.getCharge());
    }

    public void returnVehicle(Customer customer, Vehicle vehicle) {
        for (Rental rental : rentals) {
            if (rental.getVehicle() == vehicle &&
                rental.getCustomer() == customer &&
                rental.isActive()) {

                rental.closeRental();
                System.out.println(vehicle.getId() + " returned by " + customer.getName() + ".");
                return;
            }
        }

        System.out.println("No active rental found.");
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService service = new RentalService();

        Customer customer1 = new Customer("Customer 1");
        Customer customer2 = new Customer("Customer 2");
        Customer customer3 = new Customer("Customer 3");

        Vehicle sedanA = new Sedan("Sedan A");
        Vehicle suvB = new SUV("SUV B");

        service.rentVehicle(customer1, sedanA, 3);
        service.rentVehicle(customer2, sedanA, 2);
        service.returnVehicle(customer1, sedanA);
        service.rentVehicle(customer3, suvB, 5);
    }
}