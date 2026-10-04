
interface MembershipPlan {

    String getPlanName();

    int getMonths();

    double calculateFee(double baseRate);
}

class MonthlyPlan implements MembershipPlan {

    public String getPlanName() {
        return "Monthly";
    }

    public int getMonths() {
        return 1;
    }

    public double calculateFee(double baseRate) {
        return baseRate;
    }
}

class QuarterlyPlan implements MembershipPlan {

    public String getPlanName() {
        return "Quarterly";
    }

    public int getMonths() {
        return 3;
    }

    public double calculateFee(double baseRate) {

        double total = baseRate * 3;

        return total * 0.90;
    }
}

class AnnualPlan implements MembershipPlan {

    public String getPlanName() {
        return "Annual";
    }

    public int getMonths() {
        return 12;
    }

    public double calculateFee(double baseRate) {

        double total = baseRate * 12;

        return total * 0.75;
    }
}

class HalfYearlyPlan implements MembershipPlan {

    public String getPlanName() {
        return "Half-Yearly";
    }

    public int getMonths() {
        return 6;
    }

    public double calculateFee(double baseRate) {

        double total = baseRate * 6;

        return total * 0.85;
    }
}

class Member {

    private String name;

    public Member(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum MembershipStatus {
    ACTIVE,
    FROZEN,
    EXPIRED
}

class Membership {

    private static final double BASE_RATE = 1000.00;

    private Member member;
    private MembershipPlan plan;
    private double fee;
    private MembershipStatus status;

    public Membership(
            Member member,
            MembershipPlan plan) {

        this.member = member;
        this.plan = plan;

        this.fee =
            plan.calculateFee(BASE_RATE);

        this.status =
            MembershipStatus.ACTIVE;
    }

    public void checkIn() {

        if (status == MembershipStatus.ACTIVE) {

            System.out.println(
                member.getName() +
                " checked in successfully."
            );

        } else {

            System.out.println(
                "Check-in denied: " +
                member.getName() +
                "'s membership is " +
                formatStatus(status) +
                "."
            );
        }
    }

    public void freeze() {

        if (status == MembershipStatus.EXPIRED) {

            System.out.println(
                "Cannot freeze an Expired membership."
            );

            return;
        }

        if (status == MembershipStatus.FROZEN) {

            System.out.println(
                member.getName() +
                "'s membership is already frozen."
            );

            return;
        }

        status = MembershipStatus.FROZEN;

        System.out.println(
            member.getName() +
            "'s membership frozen."
        );

        System.out.println(
            "Status: Frozen."
        );
    }

    public void unfreeze() {

        if (status == MembershipStatus.EXPIRED) {

            System.out.println(
                "Cannot unfreeze an Expired membership."
            );

            return;
        }

        if (status == MembershipStatus.ACTIVE) {

            System.out.println(
                member.getName() +
                "'s membership is already active."
            );

            return;
        }

        status = MembershipStatus.ACTIVE;

        System.out.println(
            member.getName() +
            "'s membership unfrozen."
        );

        System.out.println(
            "Status: Active."
        );
    }

    public void expire() {

        status = MembershipStatus.EXPIRED;

        System.out.println(
            member.getName() +
            "'s membership expired."
        );

        System.out.println(
            "Status: Expired."
        );
    }

    private String formatStatus(
            MembershipStatus status) {

        String value =
            status.toString().toLowerCase();

        return value.substring(0, 1).toUpperCase()
             + value.substring(1);
    }

    public double getFee() {
        return fee;
    }

    public MembershipStatus getStatus() {
        return status;
    }
}

class MembershipDesk {

    public Membership buyMembership(
            Member member,
            MembershipPlan plan) {

        Membership membership =
            new Membership(member, plan);

        System.out.printf(
            "%s membership created for %s.%n",
            plan.getPlanName(),
            member.getName()
        );

        System.out.printf(
            "Fee: ₹%.2f.%n",
            membership.getFee()
        );

        System.out.println(
            "Status: Active."
        );

        return membership;
    }
}

public class FitZoneMembershipDesk {

    public static void main(String[] args) {

        Member asha =
            new Member("Asha");

        Member ravi =
            new Member("Ravi");

        MembershipDesk desk =
            new MembershipDesk();

        Membership ashaMembership =
            desk.buyMembership(
                asha,
                new QuarterlyPlan()
            );

        Membership raviMembership =
            desk.buyMembership(
                ravi,
                new MonthlyPlan()
            );

        ashaMembership.checkIn();

        ashaMembership.freeze();

        ashaMembership.checkIn();

        raviMembership.expire();

        raviMembership.freeze();
    }
}