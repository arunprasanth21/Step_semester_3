import java.util.Arrays;

public class LateFeeLedger {

    static class GymMember {
        private static final int MAX_FEES = 10;

        private final String memberId;
        private final int monthlyFee;
        private final int[] lateFeeHistory = new int[MAX_FEES];
        private int lateFeeCount;

        public GymMember(String memberId, int monthlyFee) {
            if (memberId == null || memberId.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid member ID: " + memberId);
            }
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
        }

        protected void chargeLateFee(int amount) {
            lateFeeHistory[lateFeeCount++] = amount;
        }

        int[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }

        int getTotalLateFees() {
            int total = 0;
            for (int i = 0; i < lateFeeCount; i++) {
                total += lateFeeHistory[i];
            }
            return total;
        }
    }

    static class PremiumMember extends GymMember {
        private final String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        @Override
        protected void chargeLateFee(int amount) {
            super.chargeLateFee(amount / 2);
        }
    }

    public static void main(String[] args) {
        PremiumMember p = new PremiumMember("MEM5", 2000, "Coach Riya");
        p.chargeLateFee(200);
        System.out.println("Total late fees: " + p.getTotalLateFees());

        int[] history = p.getLateFeeHistory();
        history[0] = 999;
        System.out.println("History: " + Arrays.toString(p.getLateFeeHistory()));

        GymMember standard = new GymMember("MEM6", 1000);
        standard.chargeLateFee(200);
        System.out.println("Standard total: " + standard.getTotalLateFees());
    }
}
