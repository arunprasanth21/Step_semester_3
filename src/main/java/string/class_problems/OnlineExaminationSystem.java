import java.util.*;

abstract class Question {
    private int id;
    private String text;
    private int points;

    public Question(int id, String text, int points) {
        this.id = id;
        this.text = text;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {
    private String correctAnswer;

    public MultipleChoiceQuestion(
            int id,
            String text,
            int points,
            String correctAnswer) {

        super(id, text, points);
        this.correctAnswer = correctAnswer;
    }

    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(
            int id,
            String text,
            int points,
            boolean correctAnswer) {

        super(id, text, points);
        this.correctAnswer = correctAnswer;
    }

    public boolean evaluate(String answer) {
        return Boolean.parseBoolean(answer) == correctAnswer;
    }
}

class ShortAnswerQuestion extends Question {
    private String correctAnswer;

    public ShortAnswerQuestion(
            int id,
            String text,
            int points,
            String correctAnswer) {

        super(id, text, points);
        this.correctAnswer = correctAnswer;
    }

    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer.trim());
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

class Examination {
    private String name;
    private List<Question> questions = new ArrayList<>();

    public Examination(String name) {
        this.name = name;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getTotalPoints() {
        int total = 0;

        for (Question question : questions) {
            total += question.getPoints();
        }

        return total;
    }
}

class Attempt {
    private Student student;
    private Examination examination;
    private Map<Question, String> answers = new LinkedHashMap<>();
    private boolean submitted;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        this.submitted = false;
    }

    public boolean recordAnswer(Question question, String answer) {
        if (submitted) {
            System.out.println(
                    "Cannot change answers for a submitted examination.");
            return false;
        }

        if (!examination.getQuestions().contains(question)) {
            return false;
        }

        answers.put(question, answer);
        System.out.println(
                "Answer recorded for Question " +
                question.getId() +
                ".");
        return true;
    }

    public void submit() {
        if (submitted) {
            return;
        }

        submitted = true;

        System.out.println(
                examination.getName() +
                " submitted by " +
                student.getName() +
                ".");

        int score = 0;

        for (Question question : examination.getQuestions()) {
            String answer = answers.get(question);

            boolean correct =
                    answer != null &&
                    question.evaluate(answer);

            if (correct) {
                score += question.getPoints();

                System.out.println(
                        "Result: Question " +
                        question.getId() +
                        ": Correct (" +
                        question.getPoints() +
                        " points)");
            } else {
                System.out.println(
                        "Result: Question " +
                        question.getId() +
                        ": Incorrect (0 points)");
            }
        }

        System.out.println(
                "Total score: " +
                score +
                "/" +
                examination.getTotalPoints());
    }
}

class ExaminationService {
    private Set<String> submittedAttempts = new HashSet<>();

    public Attempt startExamination(
            Student student,
            Examination examination) {

        String key =
                student.getName() +
                "-" +
                examination.getName();

        if (submittedAttempts.contains(key)) {
            System.out.println("A submitted attempt already exists.");
            return null;
        }

        System.out.println(
                examination.getName() +
                " started by " +
                student.getName() +
                ".");

        return new Attempt(student, examination);
    }

    public void submitAttempt(
            Student student,
            Examination examination,
            Attempt attempt) {

        String key =
                student.getName() +
                "-" +
                examination.getName();

        attempt.submit();
        submittedAttempts.add(key);
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {
        Student student = new Student("Student 1");

        Examination exam = new Examination("Exam A");

        Question q1 =
                new MultipleChoiceQuestion(
                        1,
                        "Select the correct option.",
                        5,
                        "C");

        Question q2 =
                new TrueFalseQuestion(
                        2,
                        "Java is a programming language.",
                        5,
                        false);

        exam.addQuestion(q1);
        exam.addQuestion(q2);

        ExaminationService service =
                new ExaminationService();

        Attempt attempt =
                service.startExamination(student, exam);

        attempt.recordAnswer(q1, "C");
        attempt.recordAnswer(q2, "True");

        service.submitAttempt(student, exam, attempt);

        attempt.recordAnswer(q1, "A");
    }
}