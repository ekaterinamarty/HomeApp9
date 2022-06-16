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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) { }

    static List<Course> getUniqueCourses(List<Student> students) {
        return students.stream()
                .flatMap(s -> s.getAllCourses().stream())
                .distinct().collect(Collectors.toList());
    }

    static List<Student> getTop3MostActiveStudents(List<Student> students) {
        return students.stream()
                .sorted(Comparator.comparingInt(s -> s.getAllCourses().size()))
                .limit(3).collect(Collectors.toList());
    }

    static List<Student> getStudentsByCourse(List<Student> students, Course course) {
        return students.stream()
                .filter(student -> student.getAllCourses().contains(course))
                .collect(Collectors.toList());
    }
}
