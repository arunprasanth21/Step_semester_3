import java.util.HashSet;

public class PairWithTargetSumUnsorted {

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

    static boolean hashSetApproach(int[] nums, int target) {
        HashSet<Integer> seen = new HashSet<>();

        for (int num : nums) {
            if (seen.contains(target - num)) {
                return true;
            }
            seen.add(num);
        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 7, 11, 15};
        int[] nums2 = {3, 4, 6};

        boolean result1 = hashSetApproach(nums1, 9);
        boolean result2 = hashSetApproach(nums2, 20);

        if (result1) {
            System.out.println("true (because 2 + 7 = 9)");
        } else {
            System.out.println("false");
        }

        System.out.println(result2);
    }
}
