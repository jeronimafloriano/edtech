package br.com.school.edtech.enrollment.application.service.impl;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.Status;
import br.com.school.edtech.enrollment.application.dto.EnrollmentDto;
import br.com.school.edtech.enrollment.application.service.EnrollmentService;
import br.com.school.edtech.enrollment.domain.model.Enrollment;
import br.com.school.edtech.enrollment.domain.repository.EnrollmentRepository;
import br.com.school.edtech.shared.exceptions.DuplicatedException;
import br.com.school.edtech.shared.exceptions.InvalidArgumentException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.exceptions.Validations;
import br.com.school.edtech.shared.finder.CourseFinder;
import br.com.school.edtech.shared.finder.UserFinder;
import br.com.school.edtech.user.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

  private final EnrollmentRepository enrollmentRepository;
  private final CourseFinder courseFinder;
  private final UserFinder userFinder;

  public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
      CourseFinder courseFinder, UserFinder userFinder) {
    this.enrollmentRepository = enrollmentRepository;
    this.courseFinder = courseFinder;
    this.userFinder = userFinder;
  }

  @Transactional
  @Override
  public EnrollmentDto register(EnrollmentDto enrollmentDto) {
    Validations.isNotNull(enrollmentDto, ValidationMessage.REQUIRED_ENROLLMENT);

    User user = userFinder.findById(enrollmentDto.getUserId());
    Course course = courseFinder.findById(enrollmentDto.getCourdeId());

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
