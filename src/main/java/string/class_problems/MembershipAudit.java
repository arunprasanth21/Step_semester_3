public class MembershipAudit {

    static class LibraryMember {
        private static int membersEnrolled = 0;

        public final String memberNumber;

        private int borrowLimit;
        private int booksBorrowed;

        public LibraryMember(int borrowLimit) {
            this.borrowLimit = borrowLimit;
            membersEnrolled++;
            memberNumber = "LIB-" + (100 + membersEnrolled);
        }

        public void borrowBook() {
            if (booksBorrowed < borrowLimit) {
                booksBorrowed++;
            }
        }

        public void borrowBook(String genre) {
            System.out.println("Genre: " + genre);
            borrowBook();
        }

        public static boolean isValidRenewalCode(String code) {
            if (code == null || code.length() != 4) {
                return false;
            }

            return code.charAt(0) == 'R'
                    && Character.isDigit(code.charAt(1))
                    && Character.isDigit(code.charAt(2))
                    && Character.isUpperCase(code.charAt(3));
        }

        public static int getMembersEnrolled() {
            return membersEnrolled;
        }

        public int getBooksBorrowed() {
            return booksBorrowed;
        }
    }

    static class FacultyMember extends LibraryMember {
        private String department;

        public FacultyMember(int borrowLimit, String department) {
            super(borrowLimit);
            this.department = department;
        }
    }

    public static String processNightlyAudit(LibraryMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int faculty = 0;
        int regular = 0;

        for (LibraryMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof FacultyMember) {
                faculty++;
            } else {
                regular++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + faculty + " faculty | " + regular + " regular";
    }

    public static void main(String[] args) {
        LibraryMember member = new LibraryMember(3);

        System.out.println(member.memberNumber);
        System.out.println(LibraryMember.getMembersEnrolled());
        System.out.println(LibraryMember.isValidRenewalCode("R12A"));
        System.out.println(LibraryMember.isValidRenewalCode("R1A"));
        System.out.println(LibraryMember.isValidRenewalCode("X12A"));

        member.borrowBook();
        member.borrowBook("Fiction");
        System.out.println(member.getBooksBorrowed());

        LibraryMember[] members = {
                new FacultyMember(5, "Physics"),
                null,
                new LibraryMember(3)
        };

        System.out.println(processNightlyAudit(members));
    }
}
