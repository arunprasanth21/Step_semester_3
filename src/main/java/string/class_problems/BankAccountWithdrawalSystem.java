import java.util.*;

public class BankAccountWithdrawalSystem {
    static class Account {
        String type;
        int balance;

        Account(String type, int balance) {
            this.type = type;
            this.balance = balance;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Account> accounts = new LinkedHashMap<>();

        while (sc.hasNext()) {
            String operation = sc.next();

            if (operation.equals("Savings") || operation.equals("Current")) {
                String id = sc.next();
                int balance = sc.nextInt();
                accounts.put(id, new Account(operation, balance));
            } else if (operation.equals("WITHDRAW")) {
                String id = sc.next();
                int amount = sc.nextInt();

                if (!accounts.containsKey(id)) {
                    System.out.println("Account not found");
                    continue;
                }

                Account account = accounts.get(id);
                long newBalance = (long) account.balance - amount;

                if (account.type.equals("Savings") && newBalance < 1000) {
                    System.out.println(id + " rejected: minimum balance 1000");
                } else if (account.type.equals("Current") && newBalance < -5000) {
                    System.out.println(id + " rejected: overdraft limit 5000");
                } else {
                    account.balance = (int) newBalance;
                    System.out.println(id + " balance " + account.balance);
                }
            }
        }

        sc.close();
    }
}