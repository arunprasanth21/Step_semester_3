import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

interface AssignmentType {
    String getTitle();
    int getMaxMarks();
    LocalDate getDueDate();

    double applyLatePenalty(double marks, long lateDays);
}

class CodingAssignment implements AssignmentType {

    private String title;
    private int maxMarks;
    private LocalDate dueDate;

    public CodingAssignment(
            String title,
            int maxMarks,
            LocalDate dueDate) {

        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double applyLatePenalty(double marks, long lateDays) {

        double penalty = lateDays * 0.10;

        return Math.max(0, marks * (1 - penalty));
    }
}

class WrittenAssignment implements AssignmentType {

    private String title;
    private int maxMarks;
    private LocalDate dueDate;

    public WrittenAssignment(
            String title,
            int maxMarks,
            LocalDate dueDate) {

        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double applyLatePenalty(double marks, long lateDays) {

        double penalty = lateDays * 0.20;

        return Math.max(0, marks * (1 - penalty));
    }
}

class Student {

    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum SubmissionStatus {
    SUBMITTED,
    GRADED
}

class Submission {

    private Student student;
    private AssignmentType assignment;
    private LocalDate submissionDate;
    private SubmissionStatus status;
    private Double finalMarks;

    public Submission(
            Student student,
            AssignmentType assignment,
            LocalDate submissionDate) {

        this.student = student;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
        this.status = SubmissionStatus.SUBMITTED;
    }

    public Student getStudent() {
        return student;
    }

    public AssignmentType getAssignment() {
        return assignment;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public long getLateDays() {

        if (submissionDate.isAfter(assignment.getDueDate())) {
            return ChronoUnit.DAYS.between(
                assignment.getDueDate(),
                submissionDate
            );
        }

        return 0;
    }

    public void grade(double awardedMarks) {

        if (status != SubmissionStatus.SUBMITTED) {
            System.out.println(
                "Cannot grade: Submission is already graded."
            );
            return;
        }

        if (awardedMarks < 0 ||
            awardedMarks > assignment.getMaxMarks()) {

            System.out.println(
                "Invalid marks."
            );
            return;
        }

        long lateDays = getLateDays();

        finalMarks = assignment.applyLatePenalty(
            awardedMarks,
            lateDays
        );

        status = SubmissionStatus.GRADED;

        if (lateDays == 0) {

            System.out.printf(
                "%s graded: %.0f/%d.%n",
                student.getName(),
                finalMarks,
                assignment.getMaxMarks()
            );

        } else {

            double penaltyPercentage =
                assignment instanceof CodingAssignment
                ? lateDays * 10
                : lateDays * 20;

            System.out.printf(
                "%s graded: %.0f/%d after %.0f%% late penalty.%n",
                student.getName(),
                finalMarks,
                assignment.getMaxMarks(),
                penaltyPercentage
            );
        }

        System.out.println("Status: Graded.");
    }

    public boolean canResubmit() {
        return status != SubmissionStatus.GRADED;
    }
}

class SubmissionPortal {

    public Submission submit(
            Student student,
            AssignmentType assignment,
            LocalDate submissionDate) {

        Submission submission =
            new Submission(
                student,
                assignment,
                submissionDate
            );

        long lateDays = submission.getLateDays();

        if (lateDays == 0) {

            System.out.printf(
                "%s's submission for '%s' received (on time).%n",
                student.getName(),
                assignment.getTitle()
            );

        } else {

            System.out.printf(
                "%s's submission for '%s' received (%d days late).%n",
                student.getName(),
                assignment.getTitle(),
                lateDays
            );
        }

        System.out.println(
            "Status: Submitted."
        );

        return submission;
    }

    public void resubmit(Submission submission) {

        if (!submission.canResubmit()) {

            System.out.printf(
                "Cannot resubmit: '%s' has already been graded.%n",
                submission.getAssignment().getTitle()
            );

        } else {

            System.out.println(
                "Resubmission allowed."
            );
        }
    }
}

public class AssignmentSubmissionPortal {

    public static void main(String[] args) {

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        AssignmentType coding =
            new CodingAssignment(
                "Linked List Lab",
                50,
                LocalDate.of(2026, 3, 10)
            );

        AssignmentType written =
            new WrittenAssignment(
                "Design Essay",
                50,
                LocalDate.of(2026, 3, 12)
            );

        SubmissionPortal portal =
            new SubmissionPortal();

        Submission ashaSubmission =
            portal.submit(
                asha,
                coding,
                LocalDate.of(2026, 3, 10)
            );

        Submission raviSubmission =
            portal.submit(
                ravi,
                written,
                LocalDate.of(2026, 3, 14)
            );

        ashaSubmission.grade(45);

        raviSubmission.grade(40);

        portal.resubmit(ashaSubmission);
    }
}
