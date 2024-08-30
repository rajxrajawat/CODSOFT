import java.util.Scanner;
public class TaskTwo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of subjects: ");
        int num = scanner.nextInt();
        int marks[] = new int[num];
        int totalMarks = 0;
        for (int i = 0; i < num; i++) {
            System.out.print("Enter marks for subject " + (i + 1) + " (out of 100): ");
            marks[i] = scanner.nextInt();
        }
        for (int i=0; i<num; i++) {
            totalMarks += marks[i];
        }
        double averagePercentage = (double) totalMarks / num;
        String grade;
        if (averagePercentage >= 90) {
            grade = "A";
        } else if (averagePercentage >= 80) {
            grade = "B";
        } else if (averagePercentage >= 70) {
            grade = "C";
        } else if (averagePercentage >= 60) {
            grade = "D";
        } else {
            grade = "Fail! Better Luck next time";
        }
        System.out.println("Total Marks: " + totalMarks);
        System.out.println("Average Percentage: " + (averagePercentage) + "%");
        System.out.println("Grade: " + grade);
    }
}