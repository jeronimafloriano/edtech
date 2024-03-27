package br.com.school.edtech.enrollment;

import static br.com.school.edtech.EdTechFactoryTests.oneCourse;
import static br.com.school.edtech.EdTechFactoryTests.oneUserStudent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.enrollment.domain.model.Enrollment;
import br.com.school.edtech.shared.exceptions.RequiredArgumentException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnrollmentTest {

  private User user;
  private Course course;

  @BeforeEach
  void setUp() {
    this.user = oneUserStudent();
    this.course = oneCourse();
  }

  @DisplayName("Should create a enrollment with all fields provided")
  @Test
  void testCreate(){
    //given
    Enrollment enrollment = new Enrollment(user, course);

    //then
    assertEquals(user, enrollment.getUser());
    assertEquals(course, enrollment.getCourse());
    assertNotNull(enrollment.getEnrollmentDate());
    assertNotNull(enrollment.getId());
  }

  @DisplayName("Should throw exception when trying to create a enrollment with invalid fields")
  @Test
  void testCreateInvalidEnrollment(){
    //when
    Throwable exceptionNullUser = assertThrows(RequiredArgumentException.class, () ->
        new Enrollment(null, course));
    //then
    assertEquals(ValidationMessage.REQUIRED_USER.getMessage(), exceptionNullUser.getMessage());

    //when
    Throwable exceptionNullCourse = assertThrows(RequiredArgumentException.class, () ->
        new Enrollment(user, null));
    //then
    assertEquals(ValidationMessage.REQUIRED_COURSE.getMessage(), exceptionNullCourse.getMessage());
  }
}
