import java.util.*;

public class Stop_Word_Frequency_Report {

    static void printFilteredWordFrequency(String feedback) {

        String cleaned = feedback
                .toLowerCase()
                .replace(".", "")
                .replace(",", "");

        String[] words = cleaned.split("\\s+");

        HashSet<String> stopWords = new HashSet<>(
                Arrays.asList(
                        "the", "was", "and",
                        "a", "is", "of", "in"
                )
        );

        HashMap<String, Integer> frequency = new HashMap<>();

        for (String word : words) {

            if (stopWords.contains(word)) {
                continue;
            }

            frequency.put(
                    word,
                    frequency.getOrDefault(word, 0) + 1
            );
        }

        List<Map.Entry<String, Integer>> entries =
                new ArrayList<>(frequency.entrySet());

        entries.sort(
                (a, b) -> b.getValue() - a.getValue()
        );

        for (Map.Entry<String, Integer> entry : entries) {

            System.out.println(
                    entry.getKey() + ": " + entry.getValue()
            );
        }
    }

    public static void main(String[] args) {

        String feedback =
                "The mentor was great, the session was great and clear.";

        printFilteredWordFrequency(feedback);
    }
}