import java.util.HashSet;

public class PairWithTargetSum {

    static boolean bruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean optimal(int[] nums, int target) {
        HashSet<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(target - num)) {
                return true;
            }
            set.add(num);
        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 7, 11, 15};
        int[] nums2 = {3, 4, 6};

        boolean result1 = optimal(nums1, 9);
        boolean result2 = optimal(nums2, 20);

        if (result1) {
            System.out.println("true (because 2 + 7 = 9)");
        } else {
            System.out.println("false");
        }

        System.out.println(result2);
    }
}
