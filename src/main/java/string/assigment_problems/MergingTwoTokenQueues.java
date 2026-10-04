
import java.util.*;

public class MergingTwoTokenQueues {
    public static int[] mergeTokens(int[] counterA, int[] counterB) {
        int m = counterA.length;
        int n = counterB.length;

        int[] result = new int[m + n];

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < m && j < n) {
            if (counterA[i] <= counterB[j]) {
                result[k++] = counterA[i++];
            } else {
                result[k++] = counterB[j++];
            }
        }

        while (i < m) {
            result[k++] = counterA[i++];
        }

        while (j < n) {
            result[k++] = counterB[j++];
        }

        return result;
    }

    public static void main(String[] args) {
        int[] counterA = {3, 8, 15, 20};
        int[] counterB = {5, 8, 12};

        int[] result = mergeTokens(counterA, counterB);

        System.out.println(Arrays.toString(result));
    }
}

/* Complexity:
- Time: O(m + n)
- Additional space: O(m + n) for the output array
- It does not sort after joining. */