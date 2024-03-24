package br.com.school.edtech.feedback.application.service;


import static java.util.stream.Collectors.toList;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.CourseId;
import br.com.school.edtech.course.domain.repository.CourseRepository;
import br.com.school.edtech.enrollment.domain.repository.EnrollmentRepository;
import br.com.school.edtech.feedback.application.dto.CourseReviewDto;
import br.com.school.edtech.feedback.domain.model.CourseReview;
import br.com.school.edtech.feedback.domain.repository.CourseReviewRepository;
import br.com.school.edtech.shared.model.exceptions.DuplicatedException;
import br.com.school.edtech.shared.model.exceptions.NotFoundException;
import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.shared.model.exceptions.Validations;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.model.UserId;
import br.com.school.edtech.user.domain.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseReviewServiceImpl implements CourseReviewService {

  private final CourseReviewRepository courseReviewRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final EnrollmentRepository enrollmentRepository;

  public CourseReviewServiceImpl(CourseReviewRepository courseReviewRepository,
      UserRepository userRepository, CourseRepository courseRepository,
      EnrollmentRepository enrollmentRepository) {
    this.courseReviewRepository = courseReviewRepository;
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.enrollmentRepository = enrollmentRepository;
  }

  @Transactional(readOnly = true)
  @Override
  public List<CourseReviewDto> getNPS(Pageable pageable) {
    Page<CourseReview> courses = courseReviewRepository.findAll(pageable);

    List<CourseId> courseIds = courses.stream()
        .map(courseReview -> courseReview.getCourse())
        .map(course -> course.getId())
        .collect(toList());

    Map<CourseId, Long> courseEnrollmentCount = enrollmentRepository.findAllByCourseId(courseIds).stream()
        .collect(Collectors.groupingBy(enrollment -> enrollment.getCourse().getId(), Collectors.counting()));

    Set<CourseId> coursesWithMoreThanFourEnrollments = courseEnrollmentCount.entrySet().stream()
        .filter(entry -> entry.getValue() > 4)
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());

    return courses.stream()
        .filter(courseReview -> coursesWithMoreThanFourEnrollments.contains(courseReview.getCourse().getId()))
        .map(CourseReviewDto::map)
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public CourseReviewDto register(CourseReviewDto courseReviewDto) {
    Validations.isNotNull(courseReviewDto, ValidationMessage.REQUIRED_COURSE_REVIEW);

    courseReviewRepository.findByCourseIdAndUserId(CourseId.of(courseReviewDto.getIdCourse()),
        UserId.of(courseReviewDto.getIdUser())).ifPresent(course -> {
      throw new DuplicatedException(ValidationMessage.COURSE_ALREADY_REGISTERED);
    });

    if(courseReviewDto.getRating().getValue() < 6) {
      ///enviar notificação
    }

    User user = userRepository.findById(UserId.of(courseReviewDto.getIdUser())).orElseThrow(() -> {
      throw new NotFoundException(ValidationMessage.USER_NOT_FOUND, courseReviewDto.getIdUser());
    });

    Course course = courseRepository.findById(CourseId.of(courseReviewDto.getIdCourse())).orElseThrow(() -> {
      throw new NotFoundException(ValidationMessage.COURSE_NOT_FOUND, courseReviewDto.getIdCourse());
    });

    CourseReview courseReview = new CourseReview(user, course,
        courseReviewDto.getRating(), courseReviewDto.getJustification());

    courseReviewRepository.save(courseReview);
    return CourseReviewDto.map(courseReview);
  }
}
