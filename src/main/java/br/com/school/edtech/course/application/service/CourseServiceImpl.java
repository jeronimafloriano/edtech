package br.com.school.edtech.course.application.service;


import static java.util.stream.Collectors.toList;

import br.com.school.edtech.course.application.dto.CourseDto;
import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.repository.CourseRepository;
import br.com.school.edtech.shared.model.exceptions.DuplicatedException;
import br.com.school.edtech.shared.model.exceptions.NotFoundException;
import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.shared.model.exceptions.Validations;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.model.UserId;
import br.com.school.edtech.user.domain.repository.UserRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  @Override
  public List<CourseDto> getAll(Pageable paginacao, String filter) {
    Page<Course> courses = filter!= null && !filter.isBlank()
        ? courseRepository.findAll(createStatusFilter(filter), paginacao)
        : courseRepository.findAll(paginacao);

    return courses.stream()
        .map(CourseDto::map)
        .collect(toList());
  }

  @Transactional
  @Override
  public CourseDto create(CourseDto courseDto) {
    Validations.isNotNull(courseDto, ValidationMessage.REQUIRED_COURSE);

    courseRepository.findByCode(courseDto.getCode()).ifPresent(course -> {
      throw new DuplicatedException(ValidationMessage.COURSE_ALREADY_REGISTERED);
    });

    User instructor = userRepository.findById(UserId.of(courseDto.getIdInstructor()))
        .orElseThrow(() -> {
          throw new NotFoundException(ValidationMessage.USER_NOT_FOUND, courseDto.getIdInstructor());
        });

    Course course = new Course(courseDto.getName(), courseDto.getCode(),
        instructor, courseDto.getDescription());

    courseRepository.save(course);
    return CourseDto.map(course);
  }

  @Override
  public CourseDto inactivate(String code) {
    Validations.isNotNull(code, ValidationMessage.REQUIRED_COURSE_CODE);

    Course course = courseRepository.findByCode(code).orElseThrow(() -> {
      throw new NotFoundException(ValidationMessage.COURSE_NOT_FOUND, code);
    });

    course.inactivate();

    courseRepository.save(course);
    return CourseDto.map(course);
  }

  private Specification<Course> createStatusFilter(String filter){
    return ((root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get("status"), filter + "%"));
  }
}
