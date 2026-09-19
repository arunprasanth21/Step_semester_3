import java.util.Arrays;

public class FineLedger {

    static class LibraryMember {
        private int[] fineHistory = new int[10];
        private int fineCount;

        protected void chargeFine(int amount) {
            if (fineCount < fineHistory.length) {
                fineHistory[fineCount] = amount;
                fineCount++;
            }
        }

        public int[] getFineHistory() {
            return Arrays.copyOf(fineHistory, fineCount);
        }

        public int getTotalFine() {
            int total = 0;
            for (int i = 0; i < fineCount; i++) {
                total += fineHistory[i];
            }
            return total;
        }
    }

    static class StudentMember extends LibraryMember {

        public StudentMember(String memberId, int borrowLimit, String course) {
        }

        @Override
        protected void chargeFine(int amount) {
            super.chargeFine(amount / 2);
        }
    }

    public static void main(String[] args) {
        StudentMember student = new StudentMember("STU5", 3, "CSE");

        student.chargeFine(100);
        System.out.println(student.getTotalFine());

        int[] history = student.getFineHistory();
        history[0] = 999;

        System.out.println(Arrays.toString(student.getFineHistory()));
    }
}
