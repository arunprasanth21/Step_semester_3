public class AttendanceAnnouncer {

    static class GymMember {
        private final String memberId;
        private final int monthlyFee;
        private int sessionsAttended;

        public GymMember(String memberId, int monthlyFee) {
            if (memberId == null || memberId.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid member ID: " + memberId);
            }
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
        }

        int getSessionsAttended() {
            return sessionsAttended;
        }

        String displayInfo() {
            return "Standard | Sessions: " + sessionsAttended;
        }
    }

    static class PremiumMember extends GymMember {
        private final String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        String getTrainerName() {
            return trainerName;
        }

        @Override
        String displayInfo() {
            return "Premium | Trainer: " + trainerName
                    + " | Sessions: " + getSessionsAttended();
        }
    }

    static String batchPrint(GymMember[] members) {
        StringBuilder announcement = new StringBuilder();

        for (GymMember member : members) {
            announcement.append(member.displayInfo());

            if (member instanceof PremiumMember) {
                PremiumMember premium = (PremiumMember) member;
                announcement.append(" [Trainer via downcast: ")
                        .append(premium.getTrainerName())
                        .append("]");
            }
            announcement.append(" | ");
        }
        return announcement.toString();
    }

    public static void main(String[] args) {
        GymMember[] members = {
                new GymMember("MEM6", 1000),
                new PremiumMember("MEM7", 2000, "Coach Riya")
        };
        System.out.println(batchPrint(members));

        GymMember plain = new GymMember("MEM8", 1000);
        try {
            PremiumMember bad = (PremiumMember) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}
