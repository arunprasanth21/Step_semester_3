class AccessChecker {

    static String classifyAccess(String fieldModifier, String accessorContext) {

        if (fieldModifier.equals("private")) {
            return accessorContext.equals("SAME_CLASS")
                    ? "ALLOWED"
                    : "DENIED";
        }

        if (fieldModifier.equals("default")) {
            return (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE"))
                    ? "ALLOWED"
                    : "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            return (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE"))
                    ? "ALLOWED"
                    : "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String summarizeBatch(String[][] attempts) {

        int allowed = 0;
        int denied = 0;

        for (String[] attempt : attempts) {

            String result = classifyAccess(
                    attempt[0],
                    attempt[1]
            );

            if (result.equals("ALLOWED")) {
                allowed++;
            } else {
                denied++;
            }
        }

        return "Allowed: " + allowed +
               " | Denied: " + denied;
    }
}

class MovieTicket {

    private String seatNumber;
    String screenId;
    protected double ticketPrice;
    public String movieTitle;
}

public class MovieTicketVisibilityChecker {

    public static void main(String[] args) {

        System.out.println(
                AccessChecker.classifyAccess(
                        "private",
                        "SAME_CLASS"
                )
        );

        System.out.println(
                AccessChecker.classifyAccess(
                        "protected",
                        "DIFFERENT_PACKAGE"
                )
        );

        String[][] attempts = {
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
                AccessChecker.summarizeBatch(attempts)
        );
    }
}