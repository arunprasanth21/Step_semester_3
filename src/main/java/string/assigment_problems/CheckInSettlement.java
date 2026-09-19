public class CheckInSettlement {

    static class GymMember {
        private static final int FIRST_NUMBER = 2000;
        private static int counter = FIRST_NUMBER;

        public final String membershipNumber;
        private final int monthlyFee;
        private int feesPaid;
        private String lastPaymentMode;

        public GymMember(int monthlyFee) {
            this.monthlyFee = monthlyFee;
            counter++;
            this.membershipNumber = "GYM-" + counter;
        }

        void payFee(int amount) {
            feesPaid += amount;
        }

        void payFee(int amount, String mode) {
            lastPaymentMode = mode;
            payFee(amount);
        }

        int getFeesPaid() {
            return feesPaid;
        }

        String getLastPaymentMode() {
            return lastPaymentMode;
        }

        static int getMembersEnrolled() {
            return counter - FIRST_NUMBER;
        }
    }

    static class GroupClassMember extends GymMember {
        private final String className;

        public GroupClassMember(int monthlyFee, String className) {
            super(monthlyFee);
            this.className = className;
        }

        String getClassName() {
            return className;
        }
    }

    static boolean isValidReferralCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }
        return code.charAt(0) == 'G'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isUpperCase(code.charAt(3));
    }

    static String processWeeklyCheckIn(GymMember[] members) {
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (GymMember member : members) {
            if (member == null) {
                nullSkipped++;
            } else if (member instanceof GroupClassMember) {
                group++;
            } else {
                individual++;
            }
        }
        return (group + individual) + " processed | " + nullSkipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        GymMember m1 = new GymMember(1000);
        System.out.println(m1.membershipNumber);
        System.out.println(GymMember.getMembersEnrolled());

        System.out.println(isValidReferralCode("G45B"));
        System.out.println(isValidReferralCode("G4B"));
        System.out.println(isValidReferralCode("X45B"));

        m1.payFee(500);
        m1.payFee(500, "UPI");
        System.out.println(m1.getFeesPaid());

        GymMember[] batch = {
                new GroupClassMember(1500, "Zumba"),
                null,
                new GymMember(1000)
        };
        System.out.println(processWeeklyCheckIn(batch));
    }
}
