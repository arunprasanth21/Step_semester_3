class FeeAccount {
    void pay(double amount) {
        System.out.println("Paid in one go (day-scholar account)");
    }
}

class HostelFeeAccount extends FeeAccount {
    void payInInstallments(double amount) {
        System.out.println("Paid in two installments (hostel account)");
    }
}

class PaymentProcessor {
    int hostelCount = 0;
    int dayScholarCount = 0;

    void processPayment(FeeAccount account, double amount) {
        if (account instanceof HostelFeeAccount) {
            ((HostelFeeAccount) account).payInInstallments(amount);
            hostelCount++;
        } else {
            account.pay(amount);
            dayScholarCount++;
        }
    }
}

public class AccountPayments {
    public static void main(String[] args) {
        FeeAccount[] accounts = {
            new HostelFeeAccount(),
            new HostelFeeAccount(),
            new FeeAccount(),
            new FeeAccount()
        };

        PaymentProcessor processor = new PaymentProcessor();

        for (FeeAccount account : accounts) {
            processor.processPayment(account, 60000);
        }

        System.out.println(
            "Hostel accounts processed: " + processor.hostelCount +
            " | Day-scholar accounts processed: " + processor.dayScholarCount
        );
    }
}