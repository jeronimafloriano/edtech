package br.com.school.edtech.course;

import br.com.school.edtech.EdTechFactory;
import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.Status;
import br.com.school.edtech.shared.model.exceptions.InvalidArgumentException;
import br.com.school.edtech.shared.model.exceptions.RequiredArgumentException;
import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    private final String name = "Basic Java";
    private final String code = "basic-java";
    private final String description = "Basic Java course";
    private User instructor;

    @BeforeEach
    void setUp() {
        this.instructor = EdTechFactory.oneUserInstructor();
    }

    @DisplayName("Should create user with all fields provided")
    @Test
    void testCreate(){
        //given
        Course course = new Course(name, code, instructor, description);

        //then
        assertEquals(name, course.getName());
        assertEquals(code, course.getCode());
        assertEquals(instructor, course.getInstructor());
        assertEquals(description, course.getDescription());
        assertEquals(Status.ACTIVE, course.getStatus());
        assertNotNull(course.getCreationDate());
        assertNull(course.getInactivationDate());
    }

    @DisplayName("Should consider the same couses with the same code")
    @Test
    void testCreateEqualCourses(){
        //given
        Course course = new Course(name, code, instructor, description);
        Course course2 = new Course(name, code, instructor, description);

        assertEquals(course, course2);
    }

    @DisplayName("Should throw exception when trying to create course with invalid fields")
    @Test
    void testCreateInvalidCourse(){
        //when
        Throwable exceptionNullName = assertThrows(RequiredArgumentException.class, () -> {
            new Course(null, code, instructor, description);
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_NAME.getMessage(), exceptionNullName.getMessage());

        //when
        Throwable exceptionBlankName = assertThrows(InvalidArgumentException.class, () -> {
            new Course("", code, instructor, description);
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_NAME.getMessage(), exceptionBlankName.getMessage());

        //when
        Throwable exceptionNullCode = assertThrows(RequiredArgumentException.class, () -> {
            new Course(name, null, instructor, description);
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_COURSE_CODE.getMessage(), exceptionNullCode.getMessage());

        //when
        Throwable exceptionBlankCode = assertThrows(InvalidArgumentException.class, () -> {
            new Course(name, "", instructor, description);
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_COURSE_CODE.getMessage(), exceptionBlankCode.getMessage());

        //when
        Throwable exceptionNullInstructor = assertThrows(RequiredArgumentException.class, () -> {
            new Course(name, code, null, description);
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_INSTRUCTOR.getMessage(), exceptionNullInstructor.getMessage());

        //when
        Throwable exceptionNullDescription = assertThrows(RequiredArgumentException.class, () -> {
            new Course(name, code, instructor, null);
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_DESCRIPTION.getMessage(), exceptionNullDescription.getMessage());

        //when
        Throwable exceptionBlankDescription = assertThrows(InvalidArgumentException.class, () -> {
            new Course(name, code, instructor, "");
        });
        //then
        assertEquals(ValidationMessage.REQUIRED_DESCRIPTION.getMessage(), exceptionBlankDescription.getMessage());
    }

    @DisplayName("Should throw exception when trying to create a course with invalid code format")
    @Test
    void testInvalidCode(){
        //when
        Throwable inValidCodeFormat = assertThrows(InvalidArgumentException.class, () -> {
            new Course(name, "12324@", instructor, description);
        });
        //then
        assertEquals(ValidationMessage.INVALID_COURSE_CODE.getMessage(), inValidCodeFormat.getMessage());
    }
}
