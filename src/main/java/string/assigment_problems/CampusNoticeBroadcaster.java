import java.util.*;

class NoticeStudent {

    private String name;
    private String department;
    private List<NotificationChannel> channels;

    public NoticeStudent(String name, String department) {
        this.name = name;
        this.department = department;
        channels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void addChannel(NotificationChannel channel) {
        channels.add(channel);
    }

    public List<NotificationChannel> getChannels() {
        return channels;
    }
}

class Notice {

    private String title;
    private Set<String> targetDepartments;

    public Notice(String title, Set<String> targetDepartments) {
        this.title = title;
        this.targetDepartments = new LinkedHashSet<>(targetDepartments);
    }

    public String getTitle() {
        return title;
    }

    public Set<String> getTargetDepartments() {
        return targetDepartments;
    }

    public boolean isValid() {
        return title != null
                && !title.trim().isEmpty()
                && targetDepartments != null
                && !targetDepartments.isEmpty();
    }
}

interface NotificationChannel {

    void send(NoticeStudent student, Notice notice);
}

class EmailChannel implements NotificationChannel {

    public void send(NoticeStudent student, Notice notice) {
        System.out.println(
                "[Email → " + student.getName() + "] "
                        + notice.getTitle()
        );
    }
}

class SmsChannel implements NotificationChannel {

    public void send(NoticeStudent student, Notice notice) {
        System.out.println(
                "[SMS → " + student.getName() + "] "
                        + notice.getTitle()
        );
    }
}

class AppChannel implements NotificationChannel {

    public void send(NoticeStudent student, Notice notice) {
        System.out.println(
                "[App → " + student.getName() + "] "
                        + notice.getTitle()
        );
    }
}

class NoticeBoard {

    private List<NoticeStudent> students;

    public NoticeBoard() {
        students = new ArrayList<>();
    }

    public void addStudent(NoticeStudent student) {
        students.add(student);
    }

    public void postNotice(Notice notice) {

        if (!notice.isValid()) {
            System.out.println(
                    "Cannot post notice: At least one target department is required."
            );
            return;
        }

        System.out.print(
                "Notice '" + notice.getTitle() + "' posted to "
        );

        int count = 0;

        for (String department : notice.getTargetDepartments()) {

            System.out.print(department);

            count++;

            if (count < notice.getTargetDepartments().size()) {
                System.out.print(", ");
            }
        }

        System.out.println(".");

        for (NoticeStudent student : students) {

            if (notice.getTargetDepartments()
                    .contains(student.getDepartment())) {

                for (NotificationChannel channel : student.getChannels()) {
                    channel.send(student, notice);
                }
            }
        }
    }
}

public class CampusNoticeBroadcaster {

    public static void main(String[] args) {

        NoticeStudent asha =
                new NoticeStudent("Asha", "CSE");

        NoticeStudent ravi =
                new NoticeStudent("Ravi", "ECE");


        asha.addChannel(new EmailChannel());
        asha.addChannel(new AppChannel());

        ravi.addChannel(new SmsChannel());

        NoticeBoard board = new NoticeBoard();

        board.addStudent(asha);
        board.addStudent(ravi);

        Set<String> cse = new LinkedHashSet<>();
        cse.add("CSE");

        Notice notice1 =
                new Notice(
                        "Lab Closed Tomorrow",
                        cse
                );

        board.postNotice(notice1);

        Set<String> cseEce = new LinkedHashSet<>();
        cseEce.add("CSE");
        cseEce.add("ECE");

        Notice notice2 =
                new Notice(
                        "Fee Deadline Extended",
                        cseEce
                );

        board.postNotice(notice2);

        Set<String> emptyDepartments =
                new LinkedHashSet<>();

        Notice notice3 =
                new Notice(
                        "Sports Day",
                        emptyDepartments
                );

        board.postNotice(notice3);
    }
}