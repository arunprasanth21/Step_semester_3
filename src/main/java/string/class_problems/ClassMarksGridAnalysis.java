
import java.util.*;

public class ClassMarksGridAnalysis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> names = new ArrayList<>();
        List<int[]> marksList = new ArrayList<>();

        while (sc.hasNext()) {
            String name = sc.next();
            String line = sc.nextLine().trim();
            line = line.replace("[", "").replace("]", "").replace(",", "");

            String[] values = line.trim().split("\\s+");

            if (values.length != 3) {
                continue;
            }

            int[] marks = new int[3];

            for (int i = 0; i < 3; i++) {
                marks[i] = Integer.parseInt(values[i]);
            }

            names.add(name);
            marksList.add(marks);
        }

        if (names.isEmpty()) {
            sc.close();
            return;
        }

        int[] subjectTotals = new int[3];
        int[] totals = new int[names.size()];
        int topperIndex = 0;

        StringBuilder totalOutput = new StringBuilder("Totals ");

        for (int i = 0; i < names.size(); i++) {
            int[] marks = marksList.get(i);

            for (int j = 0; j < 3; j++) {
                totals[i] += marks[j];
                subjectTotals[j] += marks[j];
            }

            if (totals[i] > totals[topperIndex]) {
                topperIndex = i;
            }

            if (i > 0) {
                totalOutput.append(", ");
            }

            totalOutput.append(names.get(i)).append(" ").append(totals[i]);
        }

        System.out.println(totalOutput);

        System.out.printf(
                Locale.US,
                "averages %.2f, %.2f, %.2f%n",
                subjectTotals[0] / (double) names.size(),
                subjectTotals[1] / (double) names.size(),
                subjectTotals[2] / (double) names.size()
        );

        System.out.println(
                "topper " + names.get(topperIndex) + " (" + totals[topperIndex] + ")"
        );

        sc.close();
    }
}