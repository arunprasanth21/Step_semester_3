public class CirculationReport {

    static class LibraryMember {
        private String memberId;
        private int borrowLimit;
        private int booksBorrowed;

        public LibraryMember(String memberId, int borrowLimit) {
            this.memberId = memberId;
            this.borrowLimit = borrowLimit;
        }

        public void borrowBook() {
            if (booksBorrowed < borrowLimit) {
                booksBorrowed++;
            }
        }

        public int getBooksBorrowed() {
            return booksBorrowed;
        }

        public void displayInfo(StringBuilder report) {
            report.append("General | Books: " + booksBorrowed);
        }
    }

    static class StudentMember extends LibraryMember {
        private String course;

        public StudentMember(String memberId, int borrowLimit, String course) {
            super(memberId, borrowLimit);
            this.course = course;
        }

        public String getCourse() {
            return course;
        }

        @Override
        public void displayInfo(StringBuilder report) {
            report.append("Student | Course: " + course
                    + " | Books: " + getBooksBorrowed());
        }
    }

    public static String batchPrint(LibraryMember[] members) {
        StringBuilder report = new StringBuilder();

        for (LibraryMember member : members) {
            member.displayInfo(report);

            if (member instanceof StudentMember) {
                StudentMember student = (StudentMember) member;
                report.append(" [Course via downcast: " + student.getCourse() + "]");
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {
        LibraryMember general = new LibraryMember("LB5", 3);
        StudentMember student = new StudentMember("STU6", 3, "ECE");

        System.out.println(batchPrint(new LibraryMember[] {general, student}));
    }
}
