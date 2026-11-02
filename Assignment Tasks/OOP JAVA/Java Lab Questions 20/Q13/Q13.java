/*
Question 13: Online Examination and Automated Grading System
10 Marks
An educational institution conducts online examinations consisting of multiple-choice questions and short-answer questions.
Requirements:
Create an abstract class Question containing private attributes questionId, questionText, and marks.
Create:
MCQQuestion: Contains four options and one correct option.
ShortAnswerQuestion: Contains one predefined correct answer.
Implement an abstract method evaluateAnswer(String answer).
Evaluation:
MCQ: Full marks for the correct option; otherwise zero.
Short answer: Full marks for an exact match after ignoring leading/trailing spaces and letter case; otherwise zero.
Create an Exam class that stores questions in an ArrayList<Question>.
Implement:
Add questions to an examination.
Display questions.
Accept answers from a student.
Evaluate answers using runtime polymorphism.
Generate a result showing obtained marks, total marks, and percentage.
Concepts: OOP pillars, collections, polymorphic evaluation, string operations, input validation.
*/




import java.util.ArrayList;

abstract class Question {
    private int questionId;
    private String questionText;
    private double marks;

    Question(int id, String text, double marks) {
        questionId = id;
        questionText = text;
        this.marks = marks;
    }

    int getId() { return questionId; }
    String getText() { return questionText; }
    double getMarks() { return marks; }

    abstract double evaluateAnswer(String answer);
}

class MCQQuestion extends Question {
    private String[] options;
    private String correctOption;

    MCQQuestion(int id, String text, double marks,
                String[] options, String correctOption) {
        super(id, text, marks);
        this.options = options;
        this.correctOption = correctOption;
    }

    void displayOptions() {
        for (int i = 0; i < options.length; i++)
            System.out.println((i + 1) + ". " + options[i]);
    }

    double evaluateAnswer(String answer) {
        return answer.trim().equalsIgnoreCase(correctOption)
                ? getMarks() : 0;
    }
}

class ShortAnswerQuestion extends Question {
    private String correctAnswer;

    ShortAnswerQuestion(int id, String text, double marks, String answer) {
        super(id, text, marks);
        correctAnswer = answer;
    }

    double evaluateAnswer(String answer) {
        return answer.trim().equalsIgnoreCase(correctAnswer.trim())
                ? getMarks() : 0;
    }
}

class Exam {
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();

    void addQuestion(Question q) {
        questions.add(q);
    }

    void conductExam() {
        java.util.Scanner input = new java.util.Scanner(System.in);

        for (Question q : questions) {
            System.out.println("\n" + q.getText());

            if (q instanceof MCQQuestion)
                ((MCQQuestion) q).displayOptions();

            System.out.print("Your answer: ");
            answers.add(input.nextLine());
        }
    }

    void generateResult() {
        double obtained = 0, total = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);

            obtained += q.evaluateAnswer(answers.get(i));
            total += q.getMarks();
        }

        System.out.println("\nObtained Marks: " + obtained);
        System.out.println("Total Marks: " + total);
        System.out.println("Percentage: " + (obtained / total * 100) + "%");
    }
}

public class Q13 {
    public static void main(String[] args) {
        Exam exam = new Exam();

        exam.addQuestion(new MCQQuestion(
            1, "Which language is used for Android development?", 5,
            new String[]{"Java", "HTML", "SQL", "CSS"}, "Java"
        ));

        exam.addQuestion(new ShortAnswerQuestion(
            2, "What does JVM stand for?", 5,
            "Java Virtual Machine"
        ));

        exam.addQuestion(new MCQQuestion(
            3, "Which collection stores unique elements?", 5,
            new String[]{"ArrayList", "HashSet", "Array", "LinkedList"},
            "HashSet"
        ));

        exam.conductExam();
        exam.generateResult();
    }
}