
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

abstract class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isLeaveAllowed(int days);
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) {
        super(name);
    }

    public boolean isLeaveAllowed(int days) {
        return days <= 30;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) {
        super(name);
    }

    public boolean isLeaveAllowed(int days) {
        return days <= 10;
    }
}

class Contractor extends Employee {
    public Contractor(String name) {
        super(name);
    }

    public boolean isLeaveAllowed(int days) {
        return days <= 5;
    }
}

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class LeaveRequest {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public int getDays() {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public boolean approve() {
        if (status != LeaveStatus.PENDING) {
            return false;
        }

        if (!employee.isLeaveAllowed(getDays())) {
            return false;
        }

        status = LeaveStatus.APPROVED;
        return true;
    }

    public boolean reject() {
        if (status != LeaveStatus.PENDING) {
            return false;
        }

        status = LeaveStatus.REJECTED;
        return true;
    }

    public boolean changeStatus(LeaveStatus newStatus) {
        if (status != LeaveStatus.PENDING) {
            return false;
        }

        status = newStatus;
        return true;
    }
}

class LeaveService {
    public LeaveRequest submitLeave(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate) {

        LeaveRequest request =
                new LeaveRequest(employee, startDate, endDate);

        System.out.println(
                "Leave request submitted for " +
                employee.getName() +
                " (" +
                startDate +
                " to " +
                endDate +
                ").");

        System.out.println("Status: " + formatStatus(request.getStatus()));

        return request;
    }

    public void approve(LeaveRequest request) {
        if (request.approve()) {
            System.out.println(
                    request.getEmployee().getName() +
                    "'s leave request (" +
                    request.getStartDate() +
                    " to " +
                    request.getEndDate() +
                    ") approved.");

            System.out.println(
                    "Status: " +
                    formatStatus(request.getStatus()));
        } else {
            System.out.println("Leave request cannot be approved.");
        }
    }

    public void reject(LeaveRequest request) {
        if (request.reject()) {
            System.out.println(
                    request.getEmployee().getName() +
                    "'s leave request (" +
                    request.getStartDate() +
                    " to " +
                    request.getEndDate() +
                    ") rejected.");

            System.out.println(
                    "Status: " +
                    formatStatus(request.getStatus()));
        } else {
            System.out.println("Leave request cannot be rejected.");
        }
    }

    public void changeStatus(
            LeaveRequest request,
            LeaveStatus newStatus) {

        if (!request.changeStatus(newStatus)) {
            System.out.println(
                    "Cannot change leave request status from " +
                    formatStatus(request.getStatus()) +
                    " to " +
                    formatStatus(newStatus) +
                    ".");
        }
    }

    private String formatStatus(LeaveStatus status) {
        String value = status.name().toLowerCase();
        return Character.toUpperCase(value.charAt(0))
                + value.substring(1);
    }
}

public class EmployeeLeaveWorkflow {
    public static void main(String[] args) {
        LeaveService service = new LeaveService();

        Employee john = new FullTimeEmployee("John");
        Employee jane = new PartTimeEmployee("Jane");

        LeaveRequest johnRequest =
                service.submitLeave(
                        john,
                        LocalDate.of(2027, 1, 1),
                        LocalDate.of(2027, 1, 5));

        service.approve(johnRequest);

        LeaveRequest janeRequest =
                service.submitLeave(
                        jane,
                        LocalDate.of(2027, 2, 10),
                        LocalDate.of(2027, 2, 11));

        service.reject(janeRequest);

        service.changeStatus(johnRequest, LeaveStatus.PENDING);
    }
}