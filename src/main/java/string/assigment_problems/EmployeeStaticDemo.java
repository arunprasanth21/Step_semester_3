class StaticEmployee {
    String empName;
    double salary;

    static String companyName =
        "Bright Horizon Technologies";

    static int employeeCount = 0;

    StaticEmployee(String empName, double salary) {
        this.empName = empName;
        this.salary = salary;
        employeeCount++;
    }

    static void printCompanyInfo() {
        System.out.println(companyName);
        System.out.println("Employees on record: " + employeeCount);
    }
}

public class EmployeeStaticDemo {
    public static void main(String[] args) {

        StaticEmployee employee1 =
            new StaticEmployee("Arun", 50000);

        StaticEmployee employee2 =
            new StaticEmployee("Rohan", 60000);

        StaticEmployee employee3 =
            new StaticEmployee("Kaveen", 55000);

        StaticEmployee.printCompanyInfo();
    }
}