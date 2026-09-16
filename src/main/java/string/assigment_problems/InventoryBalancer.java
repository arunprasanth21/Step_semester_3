public class InventoryBalancer {

    static void analyzeInventory(
            int[] sectionA,
            int[] sectionB) {

        int totalA = 0;
        int totalB = 0;

        int highestQuantity = Integer.MIN_VALUE;
        String highestSection = "";
        int highestIndex = -1;

        for (int i = 0; i < sectionA.length; i++) {

            // Calculate totals
            totalA += sectionA[i];
            totalB += sectionB[i];

            // Check Section A
            if (sectionA[i] > highestQuantity) {

                highestQuantity = sectionA[i];
                highestSection = "Section A";
                highestIndex = i;
            }

            // Check Section B
            if (sectionB[i] > highestQuantity) {

                highestQuantity = sectionB[i];
                highestSection = "Section B";
                highestIndex = i;
            }
        }

        String status;

        if (totalA == totalB) {
            status = "Balanced";
        } else {
            status = "Not Balanced";
        }

        System.out.println(
                "Section A Total: " + totalA +
                " | Section B Total: " + totalB +
                " | Status: " + status +
                " | Highest Quantity: " + highestQuantity +
                " (" + highestSection +
                ", Item " + (highestIndex + 1) + ")"
        );
    }

    public static void main(String[] args) {

        int[] sectionA = {20, 15, 30};
        int[] sectionB = {25, 10, 30};

        analyzeInventory(sectionA, sectionB);
    }
}