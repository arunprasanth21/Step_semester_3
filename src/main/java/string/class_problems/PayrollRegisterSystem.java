
import java.util.*;

public class PayrollRegisterSystem {
    static class Payslip {
        String name;
        String type;
        long pay;

        Payslip(String name, String type, long pay) {
            this.name = name;
            this.type = type;
            this.pay = pay;
        }

        public String toString() {
            return "Payslip[name=" + name + ", type=" + type + ", pay=" + pay + "]";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Payslip> payslips = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+");

            try {
                if (parts.length < 2) {
                    System.out.println("Invalid input");
                    continue;
                }

                String type = parts[0];
                String name = parts[1];
                long pay;

                switch (type) {
                    case "FullTime":
                        if (parts.length != 3) {
                            System.out.println("Invalid input");
                            continue;
                        }
                        pay = Long.parseLong(parts[2]);
                        break;

                    case "PartTime":
                        if (parts.length != 4) {
                            System.out.println("Invalid input");
                            continue;
                        }
                        long hours = Long.parseLong(parts[2]);
                        long rate = Long.parseLong(parts[3]);

                        if (hours < 0 || rate < 0) {
                            System.out.println("Invalid input");
                            continue;
                        }

                        pay = Math.multiplyExact(hours, rate);
                        break;

                    case "Intern":
                        if (parts.length != 3) {
                            System.out.println("Invalid input");
                            continue;
                        }
                        pay = Long.parseLong(parts[2]);
                        break;

                    default:
                        continue;
                }

                if (pay < 0) {
                    System.out.println("Invalid input");
                    continue;
                }

                payslips.add(new Payslip(name, type, pay));

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input");
            } catch (ArithmeticException e) {
                System.out.println("Invalid input");
            }
        }

        long total = 0;
        Payslip topEarner = null;

        for (Payslip payslip : payslips) {
            System.out.println(payslip);
            total = Math.addExact(total, payslip.pay);

            if (topEarner == null || payslip.pay > topEarner.pay) {
                topEarner = payslip;
            }
        }

        System.out.println("Total " + total);

        if (topEarner != null) {
            System.out.println("top earner " + topEarner.name);
        }

        sc.close();
    }
}


