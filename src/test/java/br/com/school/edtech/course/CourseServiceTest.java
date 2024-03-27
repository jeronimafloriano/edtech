package br.com.school.edtech.course;

import br.com.school.edtech.EdTechFactoryTests;
import br.com.school.edtech.course.application.dto.CourseDto;
import br.com.school.edtech.course.application.service.impl.CourseServiceImpl;
import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.Status;
import br.com.school.edtech.course.domain.repository.CourseRepository;
import br.com.school.edtech.shared.exceptions.DuplicatedException;
import br.com.school.edtech.shared.exceptions.InvalidArgumentException;
import br.com.school.edtech.shared.exceptions.NotFoundException;
import br.com.school.edtech.shared.exceptions.RequiredArgumentException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.user.application.dto.UserDto;
import br.com.school.edtech.shared.finder.UserFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    CourseRepository repository;

    @Mock
    UserFinder userFinder;

    @InjectMocks
    CourseServiceImpl courseService;

    private List<Course> courses;

    @BeforeEach
    void setUp() {
        this.courses = EdTechFactoryTests.coursesList();
    }

    @DisplayName("Should search for a course by going through a pagination")
    @Test
    void testGetAll_NoFilter() {
        //given
        Pageable pageable = PageRequest.of(0,5);
        Page<Course> page = new PageImpl<>(courses);
        given(repository.findAll(pageable)).willReturn(page);

        //when
        var result = courseService.getAll(pageable, null);

        //then
        verify(repository, times(1)).findAll(pageable);
        verify(repository, never()).findAll(any(Specification.class), any(Pageable.class));

        List<CourseDto> coursesDto = courses.stream().map(CourseDto::map).toList();
        assertEquals(coursesDto.size(), result.size());
        assertEquals(coursesDto.get(0), result.get(0));
        assertEquals(coursesDto.get(1), result.get(1));
        assertEquals(coursesDto.get(2), result.get(2));
    }

    @DisplayName("Should search for a course by filtering the status")
    @Test
    void testGetAll_Filter() {
        //given
        Pageable pageable = PageRequest.of(0,5, Sort.unsorted());
        Page<Course> page = new PageImpl<>(courses);
        given(repository.findAll(any(Specification.class), any(Pageable.class))).willReturn(page);

        //when
        var result = courseService.getAll(pageable, Status.ACTIVE.name());

        //then
        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(repository, never()).findAll(pageable);

        List<CourseDto> coursesDto = courses.stream().map(CourseDto::map).toList();
        assertEquals(coursesDto.size(), result.size());
        assertEquals(coursesDto.get(0), result.get(0));
        assertEquals(coursesDto.get(1), result.get(1));
        assertEquals(coursesDto.get(2), result.get(2));
    }

    @DisplayName("Should throw an exception when searching for a course with no pagination")
    @Test
    void testGetAll_InvalidData() {
        //when
        Throwable exceptionInvalidPagination = assertThrows(RequiredArgumentException.class, () ->
                courseService.getAll(null, null));

        //then
        assertEquals(ValidationMessage.REQUIRED_PAGINATION.getMessage(),
                exceptionInvalidPagination.getMessage());
    }


    @DisplayName("Should create a course")
    @Test
    void testCreate() {
        //given
        Course course = EdTechFactoryTests.oneCourse();
        CourseDto courseDto = new CourseDto(course.getName(), course.getCode(),
                course.getInstructor().getId().getValue(), course.getDescription());

        given(repository.findByCode(courseDto.getCode())).willReturn(Optional.empty());
        given(userFinder.findById(any())).willReturn(course.getInstructor());

        //when
        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);

        var result = courseService.create(courseDto);

        //then
        verify(repository, times(1)).save(captor.capture());
        Course courseCreated = captor.getValue();

        assertEquals(courseCreated.getId().getValue(), result.getId());
        assertEquals(courseCreated.getName(), result.getName());
        assertEquals(courseCreated.getCode(), result.getCode());
        assertEquals(UserDto.map(courseCreated.getInstructor()), result.getInstructor());
        assertEquals(courseCreated.getDescription(), result.getDescription());
        assertEquals(courseCreated.getStatus(), result.getStatus());
        assertEquals(courseCreated.getInactivationDate(), result.getInactivationDate());
        assertEquals(courseCreated.getCreationDate(), result.getCreationDate());

        assertEquals(courseDto.getName(), courseCreated.getName());
        assertEquals(courseDto.getCode(), courseCreated.getCode());
        assertEquals(courseDto.getIdInstructor(), courseCreated.getInstructor().getId().getValue());
        assertEquals(courseDto.getDescription(), courseCreated.getDescription());
        assertEquals(courseDto.getInactivationDate(), courseCreated.getInactivationDate());

        assertEquals(Status.ACTIVE, courseCreated.getStatus());
        assertNotNull(courseCreated.getCreationDate());
    }

    @DisplayName("Should throw an exception when trying to create a user with an already registered code")
    @Test
    void testCreate_DuplicatedCourse() {
        //given
        Course course = EdTechFactoryTests.oneCourse();
        CourseDto courseDto = new CourseDto();

        given(repository.findByCode(courseDto.getCode())).willReturn(Optional.of(course));

        verify(repository, never()).save(any());

        //when
        Throwable exceptionDuplicatedCourse = assertThrows(DuplicatedException.class, () ->
                courseService.create(courseDto));

        //then
        assertEquals(ValidationMessage.COURSE_ALREADY_REGISTERED.getMessage(),
                exceptionDuplicatedCourse.getMessage());
    }

    @DisplayName("Should throw an exception when trying to create a course with an instructor not found")
    @Test
    void testCreate_InstructorNotFound() {
        //given
        Course course = EdTechFactoryTests.oneCourse();
        CourseDto courseDto = new CourseDto(course.getName(), course.getCode(),
            course.getInstructor().getId().getValue(), course.getDescription());

        given(repository.findByCode(courseDto.getCode())).willReturn(Optional.empty());
        when(userFinder.findById(any())).thenThrow(new NotFoundException(ValidationMessage.USER_NOT_FOUND, ""));


        //when
        Throwable exceptionInstructorNotFound = assertThrows(NotFoundException.class, () ->
                courseService.create(courseDto));

        //then
        assertEquals(ValidationMessage.USER_NOT_FOUND.getMessage(),
                exceptionInstructorNotFound.getMessage());

        verify(repository, never()).save(any());
    }

    @DisplayName("Should inactivate a course")
    @Test
    void testInactivate() {
        //given
        Course course = EdTechFactoryTests.oneCourse();

        given(repository.findByCode(course.getCode())).willReturn(Optional.of(course));

        //when
        var result = courseService.inactivate(course.getCode());

        //then
        verify(repository, times(1)).save(course);
        assertEquals(Status.INACTIVE, result.getStatus());
        assertNotNull(result.getInactivationDate());
    }

    @DisplayName("It should throw an exception when trying to inactivate a course with invalid data")
    @Test
    void testInactivate_InvalidData() {
        //when
        Throwable exceptionNullCode= assertThrows(RequiredArgumentException.class, () ->
                courseService.inactivate(null));

        //then
        assertEquals(ValidationMessage.REQUIRED_COURSE_CODE.getMessage(),
                exceptionNullCode.getMessage());

        //when
        Throwable exceptionBlanckCode= assertThrows(InvalidArgumentException.class, () ->
                courseService.inactivate(""));

        //then
        assertEquals(ValidationMessage.REQUIRED_COURSE_CODE.getMessage(),
            exceptionBlanckCode.getMessage());
    }

    @DisplayName("It should throw an exception when trying to inactivate a course that doesn't exist")
    @Test
    void testInactivate_CourseNotFound() {
        //when
        Throwable exceptionCourseNotFound= assertThrows(NotFoundException.class,
                () -> courseService.inactivate("xpto"));

        //then
        assertEquals(ValidationMessage.COURSE_NOT_FOUND.getMessage(),
                exceptionCourseNotFound.getMessage());
    }
}
