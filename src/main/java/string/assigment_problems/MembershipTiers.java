public class MembershipTiers {

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

        void attendSession() {
            sessionsAttended++;
        }

        int getSessionsAttended() {
            return sessionsAttended;
        }

        String displayInfo() {
            return "Standard Member | Sessions: " + sessionsAttended;
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
            return "Premium Member | Trainer: " + trainerName
                    + " | Sessions: " + getSessionsAttended();
        }
    }

    static class EliteMember extends PremiumMember {
        private final String lockerNumber;

        public EliteMember(String memberId, int monthlyFee, String trainerName, String lockerNumber) {
            super(memberId, monthlyFee, trainerName);
            this.lockerNumber = lockerNumber;
        }

        @Override
        String displayInfo() {
            return "Elite Member | Trainer: " + getTrainerName()
                    + " | Locker: " + lockerNumber
                    + " | Sessions: " + getSessionsAttended();
        }
    }

    static class GroupClassMember extends GymMember {
        private final String className;

        public GroupClassMember(String memberId, int monthlyFee, String className) {
            super(memberId, monthlyFee);
            this.className = className;
        }

        @Override
        String displayInfo() {
            return "Group Class Member | Class: " + className
                    + " | Sessions: " + getSessionsAttended();
        }
    }

    static String classifyGeneration(GymMember member) {
        if (member instanceof EliteMember) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (member instanceof PremiumMember) {
            return "Single inheritance child (2 generations deep)";
        }
        if (member instanceof GroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        }
        return "Base class (root of hierarchy)";
    }

    static int getTotalSessionsAttended(GymMember[] members) {
        int total = 0;
        for (GymMember member : members) {
            total += member.getSessionsAttended();
        }
        return total;
    }

    static void attend(GymMember member, int times) {
        for (int i = 0; i < times; i++) {
            member.attendSession();
        }
    }

    public static void main(String[] args) {
        GymMember standard = new GymMember("MEM1", 1000);
        PremiumMember premium = new PremiumMember("MEM2", 2000, "Coach Riya");
        EliteMember elite = new EliteMember("MEM3", 3000, "Coach Arjun", "L12");
        GroupClassMember group = new GroupClassMember("MEM4", 1500, "Zumba");

        GymMember[] all = {standard, premium, elite, group};

        for (GymMember member : all) {
            System.out.println(member.displayInfo());
        }

        for (GymMember member : all) {
            System.out.println(classifyGeneration(member));
        }

        attend(premium, 3);
        attend(elite, 2);
        attend(group, 4);

        GymMember[] attending = {premium, elite, group};
        System.out.println("Total sessions: " + getTotalSessionsAttended(attending));
    }
}