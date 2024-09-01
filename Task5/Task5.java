import java.io.*;
import java.util.*;

public class Task5 {

    static class Course implements Serializable {
        private static final long serialVersionUID = 1L;
        String code;
        String title;
        String description;
        int capacity;
        int enrolled;
        String timeSlot;
        String instructor;

        Course(String code, String title, String description, int capacity, String timeSlot, String instructor) {
            this.code = code;
            this.title = title;
            this.description = description;
            this.capacity = capacity;
            this.enrolled = 0;
            this.timeSlot = timeSlot;
            this.instructor = instructor;
        }

        boolean isOpen() {
            return enrolled < capacity;
        }

        void addStudent() {
            if (isOpen()) {
                enrolled++;
            }
        }

        void removeStudent() {
            if (enrolled > 0) {
                enrolled--;
            }
        }

        @Override
        public String toString() {
            return String.format("Code: %s, Title: %s, Description: %s, Capacity: %d, Enrolled: %d, Time Slot: %s, Instructor: %s",
                    code, title, description, capacity, enrolled, timeSlot, instructor);
        }
    }

    static class Learner implements Serializable {
        private static final long serialVersionUID = 1L;
        String id;
        String name;
        List<String> enrolledCourses = new ArrayList<>();

        Learner(String id, String name) {
            this.id = id;
            this.name = name;
        }

        void enrollInCourse(String courseCode) {
            enrolledCourses.add(courseCode);
        }

        void withdrawFromCourse(String courseCode) {
            enrolledCourses.remove(courseCode);
        }
    }

    private static final Map<String, Course> courseCatalog = new HashMap<>();
    private static final Map<String, Learner> studentRegistry = new HashMap<>();
    private static final String COURSE_FILE = "course_data.dat";
    private static final String STUDENT_FILE = "student_data.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            loadData();
            if (courseCatalog.isEmpty()) {
                setupDefaultData();
            }

            while (true) {
                System.out.println("1. View courses");
                System.out.println("2. Enroll in a course");
                System.out.println("3. Withdraw from a course");
                System.out.println("4. Exit");
                System.out.print("Select an option: ");
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        displayCourses();
                        break;
                    case 2:
                        enroll(scanner);
                        break;
                    case 3:
                        withdraw(scanner);
                        break;
                    case 4:
                        saveData();
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void setupDefaultData() {
        // Initialize with new course data and instructors
        courseCatalog.put("MCA301", new Course("MCA301", "Fundamentals of Programming", "Introduction to programming concepts", 25, "Tue 09:00 - 11:00", "Dr. Subhash"));
        courseCatalog.put("MCA302", new Course("MCA302", "Data Structures", "Study of data structures", 20, "Wed 13:00 - 15:00", "Prof. Devendra"));
        courseCatalog.put("MCA303", new Course("MCA303", "Algorithms", "Algorithms and their applications", 20, "Thu 11:00 - 13:00", "Dr. Madhavi"));
        // Initialize with new student data
        studentRegistry.put("S101", new Learner("S101", "Rishi"));
        studentRegistry.put("S102", new Learner("S102", "Raj"));
        studentRegistry.put("S103", new Learner("S103", "Shourya"));
    }

    private static void displayCourses() {
        System.out.println("Available Courses:");
        for (Course course : courseCatalog.values()) {
            System.out.println(course);
        }
    }

    private static void enroll(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Learner student = studentRegistry.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = courseCatalog.get(courseCode);
        if (course == null || !course.isOpen()) {
            System.out.println("Course not available or full.");
            return;
        }

        student.enrollInCourse(courseCode);
        course.addStudent();
        System.out.println("Enrollment successful.");
    }

    private static void withdraw(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Learner student = studentRegistry.get(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        if (!student.enrolledCourses.contains(courseCode)) {
            System.out.println("Student not enrolled in this course.");
            return;
        }

        student.withdrawFromCourse(courseCode);
        Course course = courseCatalog.get(courseCode);
        if (course != null) {
            course.removeStudent();
        }
        System.out.println("Withdrawal successful.");
    }

    private static void saveData() throws IOException {
        try (ObjectOutputStream oosCourses = new ObjectOutputStream(new FileOutputStream(COURSE_FILE));
             ObjectOutputStream oosStudents = new ObjectOutputStream(new FileOutputStream(STUDENT_FILE))) {
            oosCourses.writeObject(courseCatalog);
            oosStudents.writeObject(studentRegistry);
        }
    }

    private static void loadData() throws IOException, ClassNotFoundException {
        File courseFile = new File(COURSE_FILE);
        File studentFile = new File(STUDENT_FILE);

        if (courseFile.exists()) {
            try (ObjectInputStream oisCourses = new ObjectInputStream(new FileInputStream(courseFile))) {
                @SuppressWarnings("unchecked")
                Map<String, Course> loadedCourses = (Map<String, Course>) oisCourses.readObject();
                courseCatalog.putAll(loadedCourses);
            }
        }

        if (studentFile.exists()) {
            try (ObjectInputStream oisStudents = new ObjectInputStream(new FileInputStream(studentFile))) {
                @SuppressWarnings("unchecked")
                Map<String, Learner> loadedStudents = (Map<String, Learner>) oisStudents.readObject();
                studentRegistry.putAll(loadedStudents);
            }
        }
    }
}
