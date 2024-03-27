package br.com.school.edtech;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.user.domain.model.Email;
import br.com.school.edtech.user.domain.model.Role;
import br.com.school.edtech.user.domain.model.User;

import java.util.Arrays;
import java.util.List;

public class EdTechFactory {

    private static final String name = "Basic Sql";
    private static final String code = "basic-sql";
    private static final String description = "Basic Sql course";

    public static User oneUserInstructor() {
        return new User("Mary", "mary", Email.of("mary@email.com"), Role.INSTRUCTOR, "xpto");
    }

    public static Course oneCourse() {
        return new Course("Programming Logic", "programming-logic",
                oneUserInstructor(), "Programming Logic for Beginners");
    }

    public static List<Course> coursesList() {
        Course course = new Course(name, code, oneUserInstructor(), description);
        Course course2 = new Course("Machine Learning", "machine-learning",
                oneUserInstructor(), "Advanced Machine Learning");
        Course course3 = new Course("Junit", "tests-junit",
                oneUserInstructor(), "Unit testing with junit");
        return Arrays.asList(course, course2, course3);
    }
}
