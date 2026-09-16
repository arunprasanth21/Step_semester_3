public class TrafficSignalStreak {

    static void findLongestStreak(String signalLog) {

        if (signalLog == null || signalLog.isEmpty()) {
            System.out.println("No Signal Data");
            return;
        }

        int currentStreak = 1;
        int longestStreak = 1;

        char currentColor = signalLog.charAt(0);
        char longestColor = currentColor;

        for (int i = 1; i < signalLog.length(); i++) {

            char current = signalLog.charAt(i);

            if (current == currentColor) {

                currentStreak++;

            } else {

                currentColor = current;
                currentStreak = 1;
            }

            if (currentStreak > longestStreak) {

                longestStreak = currentStreak;
                longestColor = currentColor;
            }
        }

        System.out.println(
                "Longest Streak: '" +
                longestColor +
                "' repeated " +
                longestStreak +
                " times"
        );
    }

    public static void main(String[] args) {

        String signalLog = "RRGGGYRR";

        findLongestStreak(signalLog);
    }
}
