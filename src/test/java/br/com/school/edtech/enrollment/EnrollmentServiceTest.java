package br.com.school.edtech.enrollment;

import static br.com.school.edtech.EdTechFactoryTests.oneCourse;
import static br.com.school.edtech.EdTechFactoryTests.oneUserStudent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.school.edtech.course.application.dto.CourseDto;
import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.enrollment.application.dto.EnrollmentDto;
import br.com.school.edtech.enrollment.application.service.impl.EnrollmentServiceImpl;
import br.com.school.edtech.enrollment.domain.model.Enrollment;
import br.com.school.edtech.enrollment.domain.repository.EnrollmentRepository;
import br.com.school.edtech.shared.exceptions.DuplicatedException;
import br.com.school.edtech.shared.exceptions.InvalidArgumentException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.finder.impl.CourseFinderImpl;
import br.com.school.edtech.shared.finder.impl.UserFinderImpl;
import br.com.school.edtech.user.application.dto.UserDto;
import br.com.school.edtech.user.domain.model.User;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @Mock
    EnrollmentRepository repository;

    @Mock
    CourseFinderImpl courseFinder;

    @Mock
    UserFinderImpl userFinder;

    @InjectMocks
    EnrollmentServiceImpl enrollmentService;

    private User user;
    private Course course;

    @BeforeEach
    void setUp() {
        this.user = oneUserStudent();
        this.course = oneCourse();
    }

    @DisplayName("Should create a enrollment")
    @Test
    void testRegister() {
        //given
        EnrollmentDto enrollmentDto = new EnrollmentDto(UUID.randomUUID(), UUID.randomUUID());

        when(userFinder.findById(enrollmentDto.getUserId())).thenReturn(user);
        when(courseFinder.findById(enrollmentDto.getCourdeId())).thenReturn(course);
        when(repository.findByCourseAndUser(course, user)).thenReturn(Optional.empty());


        var result = enrollmentService.register(enrollmentDto);

        //then
        verify(repository, times(1)).save(any(Enrollment.class));
        assertEquals(CourseDto.map(course), result.getCourse());
        assertEquals(UserDto.map(user), result.getUser());
        assertNotNull(result.getEnrollmentDate());
    }

    @DisplayName("Should create a enrollment")
    @Test
    void testRegister_alreadyExisting() {
        //given
        EnrollmentDto enrollmentDto = new EnrollmentDto();

        when(userFinder.findById(enrollmentDto.getUserId())).thenReturn(user);
        when(courseFinder.findById(enrollmentDto.getCourdeId())).thenReturn(course);
        when(repository.findByCourseAndUser(course, user)).thenReturn(Optional.of(new Enrollment(user, course)));

        //when
        Throwable existingEnrollment = assertThrows(DuplicatedException.class, () -> {
            enrollmentService.register(enrollmentDto);
        });
        //then
        assertEquals(ValidationMessage.ENROLLMENT_ALREADY_REGISTERED.getMessage(), existingEnrollment.getMessage());

        //then
        verify(repository, never()).save(any());
    }

    @DisplayName("Should create a enrollment")
    @Test
    void testRegister_courseInactive() {
        //given
        EnrollmentDto enrollmentDto = new EnrollmentDto(UUID.randomUUID(), UUID.randomUUID());

        when(userFinder.findById(enrollmentDto.getUserId())).thenReturn(user);
        when(courseFinder.findById(enrollmentDto.getCourdeId())).thenReturn(course);
        when(repository.findByCourseAndUser(course, user)).thenReturn(Optional.empty());

        course.inactivate();

        //when
        Throwable courseInactive = assertThrows(InvalidArgumentException.class, () -> {
            enrollmentService.register(enrollmentDto);
        });
        //then
        assertEquals(ValidationMessage.COURSE_INACTIVE.getMessage(), courseInactive.getMessage());

        //then
        verify(repository, never()).save(any());
    }
}
