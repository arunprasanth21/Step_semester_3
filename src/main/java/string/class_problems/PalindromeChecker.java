import java.util.Scanner;

public class PalindromeChecker {

    // Approach 1: Iterative / Two Pointers
    static boolean isPalindromeIterative(String text) {

        int left = 0;
        int right = text.length() - 1;

        while (left < right) {

            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    // Approach 2: Recursive
    static boolean isPalindromeRecursive(String text) {

        return checkRecursive(text, 0, text.length() - 1);
    }

    static boolean checkRecursive(
            String text, int left, int right) {

        if (left >= right) {
            return true;
        }

        if (text.charAt(left) != text.charAt(right)) {
            return false;
        }

        return checkRecursive(text, left + 1, right - 1);
    }

    // Approach 3: Array Reversal
    static boolean isPalindromeArrayReversal(String text) {

        char[] chars = text.toCharArray();

        int left = 0;
        int right = chars.length - 1;

        while (left < right) {

            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;

            left++;
            right--;
        }

        String reversed = new String(chars);

        return text.equals(reversed);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        boolean iterative =
                isPalindromeIterative(text);

        boolean recursive =
                isPalindromeRecursive(text);

        boolean arrayReversal =
                isPalindromeArrayReversal(text);

        System.out.println(
                "Iterative: " +
                (iterative ? "Palindrome" : "Not Palindrome")
        );

        System.out.println(
                "Recursive: " +
                (recursive ? "Palindrome" : "Not Palindrome")
        );

        System.out.println(
                "Array Reversal: " +
                (arrayReversal ? "Palindrome" : "Not Palindrome")
        );

        sc.close();
    }
}
