import java.util.*;

public class MostPopularCanteenOrder {
    public static String[] mostPopular(String[] orders) {
        HashMap<String, Integer> count = new HashMap<>();

        for (String order : orders) {
            count.put(order, count.getOrDefault(order, 0) + 1);
        }

        String bestItem = orders[0];
        int bestCount = count.get(bestItem);

        for (String order : orders) {
            if (count.get(order) > bestCount) {
                bestItem = order;
                bestCount = count.get(order);
            }
        }

        return new String[]{bestItem, String.valueOf(bestCount)};
    }

    public static void main(String[] args) {
        String[] orders = {
            "dosa",
            "idli",
            "vada",
            "dosa",
            "idli",
            "dosa",
            "tea"
        };

        String[] result = mostPopular(orders);

        System.out.println("(\"" + result[0] + "\", " + result[1] + ")");
    }
}
