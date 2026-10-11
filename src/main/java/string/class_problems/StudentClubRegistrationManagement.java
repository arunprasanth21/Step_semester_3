import java.util.*;

public class StudentClubRegistrationManagement {
    static class Student {
        String rollNumber;
        String name;

        Student(String rollNumber, String name) {
            this.rollNumber = rollNumber;
            this.name = name;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof Student)) {
                return false;
            }

            Student other = (Student) obj;
            return rollNumber.equals(other.rollNumber);
        }

        public int hashCode() {
            return rollNumber.hashCode();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Set<Student> members = new LinkedHashSet<>();
        List<String> addResults = new ArrayList<>();
        List<String> containsResults = new ArrayList<>();

        while (sc.hasNext()) {
            String operation = sc.next();
            String rollNumber = sc.next();
            String name = sc.next();

            Student student = new Student(rollNumber, name);

            if (operation.equals("ADD")) {
                if (members.add(student)) {
                    addResults.add("Added");
                } else {
                    addResults.add("duplicate rejected");
                }
            } else if (operation.equals("CONTAINS")) {
                containsResults.add("contains: " + members.contains(student));
            }
        }

        for (String result : addResults) {
            System.out.println(result);
        }

        System.out.println("member count " + members.size());

        for (String result : containsResults) {
            System.out.println(result);
        }

        sc.close();
    }
}
