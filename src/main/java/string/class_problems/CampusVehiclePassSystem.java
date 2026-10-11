import java.util.*;

public class CampusVehiclePassSystem {
    interface Chargeable {
        boolean bookChargingBay();
    }

    static class Vehicle {
        String passNumber;
        String owner;

        Vehicle(String passNumber, String owner) {
            this.passNumber = passNumber;
            this.owner = owner;
        }

        int getPassFee() {
            return 0;
        }

        String getType() {
            return "";
        }
    }

    static class Bike extends Vehicle {
        Bike(String passNumber, String owner) {
            super(passNumber, owner);
        }

        int getPassFee() {
            return 300;
        }

        String getType() {
            return "Bike";
        }
    }

    static class Car extends Vehicle {
        Car(String passNumber, String owner) {
            super(passNumber, owner);
        }

        int getPassFee() {
            return 1000;
        }

        String getType() {
            return "Car";
        }
    }

    static class EBike extends Vehicle implements Chargeable {
        EBike(String passNumber, String owner) {
            super(passNumber, owner);
        }

        int getPassFee() {
            return 300;
        }

        String getType() {
            return "EBike";
        }

        public boolean bookChargingBay() {
            return true;
        }
    }

    static class ECar extends Vehicle implements Chargeable {
        ECar(String passNumber, String owner) {
            super(passNumber, owner);
        }

        int getPassFee() {
            return 1000;
        }

        String getType() {
            return "ECar";
        }

        public boolean bookChargingBay() {
            return true;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Vehicle> vehicles = new LinkedHashMap<>();

        while (sc.hasNext()) {
            String operation = sc.next();

            if (operation.equals("PASS")) {
                String type = sc.next();
                String passNumber = sc.next();
                String owner = sc.next();

                Vehicle vehicle;

                switch (type) {
                    case "Bike":
                        vehicle = new Bike(passNumber, owner);
                        break;
                    case "Car":
                        vehicle = new Car(passNumber, owner);
                        break;
                    case "EBike":
                    case "E-Bike":
                        vehicle = new EBike(passNumber, owner);
                        break;
                    case "ECar":
                    case "E-Car":
                        vehicle = new ECar(passNumber, owner);
                        break;
                    default:
                        continue;
                }

                vehicles.put(passNumber, vehicle);

                System.out.println(vehicle.passNumber + " (" + vehicle.getType()
                        + ") pass fee " + vehicle.getPassFee());
            } else if (operation.equals("CHARGE")) {
                String passNumber = sc.next();

                Vehicle vehicle = vehicles.get(passNumber);

                if (vehicle == null) {
                    System.out.println("Vehicle not found");
                } else if (vehicle instanceof Chargeable) {
                    ((Chargeable) vehicle).bookChargingBay();
                    System.out.println(passNumber + " charging bay allotted");
                } else {
                    System.out.println(passNumber + " rejected: charging unsupported");
                }
            }
        }

        sc.close();
    }
}