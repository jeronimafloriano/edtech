package br.com.school.edtech.enrollment.application.service;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.CourseId;
import br.com.school.edtech.course.domain.model.Status;
import br.com.school.edtech.course.domain.repository.CourseRepository;
import br.com.school.edtech.enrollment.application.dto.EnrollmentDto;
import br.com.school.edtech.enrollment.domain.model.Enrollment;
import br.com.school.edtech.enrollment.domain.repository.EnrollmentRepository;
import br.com.school.edtech.shared.model.exceptions.DuplicatedException;
import br.com.school.edtech.shared.model.exceptions.InvalidArgumentException;
import br.com.school.edtech.shared.model.exceptions.NotFoundException;
import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.shared.model.exceptions.Validations;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.model.UserId;
import br.com.school.edtech.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

  private final EnrollmentRepository enrollmentRepository;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
      CourseRepository courseRepository, UserRepository userRepository) {
    this.enrollmentRepository = enrollmentRepository;
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  @Override
  public EnrollmentDto register(EnrollmentDto enrollmentDto) {
    Validations.isNotNull(enrollmentDto, ValidationMessage.REQUIRED_ENROLLMENT);

    User user = userRepository.findById(UserId.of(enrollmentDto.getUserId()))
        .orElseThrow(() -> {
          throw new NotFoundException(ValidationMessage.USER_NOT_FOUND, enrollmentDto.getUserId());
        });

    Course course = courseRepository.findById(CourseId.of(enrollmentDto.getCourdeId()))
        .orElseThrow(() -> {
          throw new NotFoundException(ValidationMessage.COURSE_NOT_FOUND, enrollmentDto.getCourdeId());
        });

    enrollmentRepository.findByCourseAndUser(course, user).ifPresent(enrollment -> {
      throw new DuplicatedException(ValidationMessage.ENROLLMENT_ALREADY_REGISTERED);
    });

    validationCourseInactive(course.getStatus());

    Enrollment enrollment = new Enrollment(user, course);
    enrollmentRepository.save(enrollment);
    return EnrollmentDto.map(enrollment);
  }

  private void validationCourseInactive(Status status) {
    if(status.equals(Status.INACTIVE)) {
      throw new InvalidArgumentException(ValidationMessage.COURSE_INACTIVE);
    }
  }


}
