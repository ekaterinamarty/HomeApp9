/*
Имеется следующая структура:
   interface Student {
        String getName();
        List<Course> getAllCourses();
    }
    interface Course {}
1. Написать функцию, принимающую список Student и возвращающую список уникальных курсов, на которые подписаны студенты.
2. Написать функцию, принимающую на вход список Student и возвращающую список из трех самых любознательных (любознательность определяется количеством курсов).
3. Написать функцию, принимающую на вход список Student и экземпляр Course, возвращающую список студентов, которые посещают этот курс.
*/

import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        var courses = Arrays.asList(
                newCourse("Course #1"),
                newCourse("Course #2"),
                newCourse("Course #3"),
                newCourse("Course #4"),
                newCourse("Course #5")
        );
        var rnd = new Random();
        var students = Arrays.asList(
                newStudent("Student #1", courses, rnd),
                newStudent("Student #2", courses, rnd),
                newStudent("Student #3", courses, rnd),
                newStudent("Student #4", courses, rnd),
                newStudent("Student #5", courses, rnd),
                newStudent("Student #6", courses, rnd),
                newStudent("Student #7", courses, rnd),
                newStudent("Student #8", courses, rnd),
                newStudent("Student #9", courses, rnd)
        );

        System.out.println("1. Get unique courses.");
        for (var item : getUniqueCourses(students)) {
            System.out.println(item.getName());
        }
        System.out.println();

        System.out.println("2. Get top-3 most active students.");
        for (var item : students) {
            System.out.println(item.getName() + ", number of courses " + item.getAllCourses().size());
        }
        System.out.println("Top-3 students are:");
        for (var item : getTop3MostActiveStudents(students)) {
            System.out.println(item.getName());
        }
        System.out.println();

        System.out.println("3. Get students by course.");
        var spec_course = courses.get(rnd.nextInt(courses.size() - 1));
        System.out.println("Specified course: " + spec_course.getName());
        for (var item : getStudentsByCourse(students, spec_course)) {
            System.out.println(item.getName());
        }
        System.out.println();
    }

    static List<Course> getUniqueCourses(List<Student> students) {
        return students.stream()
                .flatMap(s -> s.getAllCourses().stream())
                .distinct().collect(Collectors.toList());
    }

    static List<Student> getTop3MostActiveStudents(List<Student> students) {
        return students.stream()
                .sorted(Comparator.comparingInt(s -> -s.getAllCourses().size()))
                .limit(3).collect(Collectors.toList());
    }

    static List<Student> getStudentsByCourse(List<Student> students, Course course) {
        return students.stream()
                .filter(student -> student.getAllCourses().contains(course))
                .collect(Collectors.toList());
    }


    static Course newCourse(String name) {
        return new Course() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public int hashCode() {
                return getName().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Course e) {
                    return getName().equals(e.getName());
                }
                else return false;
            }
        };
    }

    static void runCourseTests() {
        // Test Course implementation.
        var c1 = newCourse("Course #1");
        var c2 = newCourse("Course #1");
        System.out.println(c1.equals(c2));
        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());
    }

    static Student newStudent(String name, List<Course> courses, Random rnd) {
        var s_courses = new HashSet<Course>();
        var num = rnd.nextInt(courses.size());
        while (s_courses.size() <= num) {
            var c_idx = rnd.nextInt(courses.size());
            var course = courses.get(c_idx);
            s_courses.add(course);
        }
        return new Student() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public List<Course> getAllCourses() {
                return new ArrayList<>(s_courses);
            }

            @Override
            public int hashCode() {
                return getName().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Student e) {
                    return getName().equals(e.getName());
                } else return false;
            }
        };
    }

    static void runStudentTests(List<Course> courses) {
        var rnd = new Random();
        // Test Student implementation.
        var s1 = newStudent("Student #1", courses, rnd);
        var s2 = newStudent("Student #1", courses, rnd);
        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());

        var s = newStudent("Student", courses, rnd);
        for (var item : s.getAllCourses()) {
            System.out.println(item.getName());
        }
    }
}
