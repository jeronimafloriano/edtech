package br.com.school.edtech.feedback.application.service.impl;

import br.com.school.edtech.config.MapDataSource;
import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.CourseId;
import br.com.school.edtech.feedback.application.dto.CourseReviewDto;
import br.com.school.edtech.feedback.application.service.CourseReviewService;
import br.com.school.edtech.feedback.domain.model.CourseReview;
import br.com.school.edtech.feedback.domain.model.Rating;
import br.com.school.edtech.feedback.domain.repository.CourseReviewRepository;
import br.com.school.edtech.shared.exceptions.DuplicatedException;
import br.com.school.edtech.shared.exceptions.ReportException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.exceptions.Validations;
import br.com.school.edtech.shared.finder.CourseFinder;
import br.com.school.edtech.shared.finder.UserFinder;
import br.com.school.edtech.shared.notification.Notification;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.model.UserId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseReviewServiceImpl implements CourseReviewService {

  @Value("${path.report.configuration}")
  private String pathConfigurationReport;

  @Value("${path.report.result}")
  private String pathResultReport;

  private final CourseReviewRepository courseReviewRepository;
  private final UserFinder userFinder;
  private final CourseFinder courseFinder;
  private final Notification notification;

  public CourseReviewServiceImpl(CourseReviewRepository courseReviewRepository,
      UserFinder userFinder, CourseFinder courseFinder, Notification notification) {
    this.courseReviewRepository = courseReviewRepository;
    this.userFinder = userFinder;
    this.courseFinder = courseFinder;
    this.notification = notification;
  }


  @Transactional(readOnly = true)
  @Override
  public Map<String, Double> getNPS() {
    List<Course> courses = courseFinder.findCoursesWithMoreThanFourEnrollments();

    List<CourseReview> reviews = courseReviewRepository.findReviewsByCourses(courses);

    Map<String, List<CourseReview>> ratingsByCourse = reviews.stream()
        .collect(Collectors.groupingBy(courseReview -> courseReview.getCourse().getName()));

    Map<String, Double> npsResult = npsCalculation(ratingsByCourse);

    generateReport(npsResult);

    return npsResult;
  }

  @Transactional
  @Override
  public CourseReviewDto register(CourseReviewDto courseReviewDto) {
    Validations.isNotNull(courseReviewDto, ValidationMessage.REQUIRED_COURSE_REVIEW);

    courseReviewRepository.findByCourseIdAndUserId(CourseId.of(courseReviewDto.getIdCourse()),
        UserId.of(courseReviewDto.getIdUser())).ifPresent(course -> {
      throw new DuplicatedException(ValidationMessage.COURSE_REVIEW_ALREADY_REGISTERED);
    });

    User user = userFinder.findById(courseReviewDto.getIdUser());
    Course course = courseFinder.findById(courseReviewDto.getIdCourse());

    CourseReview courseReview = new CourseReview(user, course,
        courseReviewDto.getRating(), courseReviewDto.getJustification());

    notificationSending(courseReview);
    courseReviewRepository.save(courseReview);
    return CourseReviewDto.map(courseReview);
  }

  private void notificationSending(CourseReview courseReview) {
    if(courseReview.getRating().compareTo(Rating.SIX) < 0) {
      String course = courseReview.getCourse().getName();
      String instructor = courseReview.getCourse().getInstructor().getName();
      String justification = courseReview.getJustification();
      notification.send(instructor, String.format("Review of course: %s", course), justification);
    }
  }

  private Map<String, Double> npsCalculation(Map<String, List<CourseReview>> ratingsByCourse) {
    return ratingsByCourse.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            entry -> {
              long ratingsMoreThenEight = entry.getValue().stream()
                  .filter(review -> review.getRating().getValue() > 8)
                  .count();
              long ratingsLessThenSix = entry.getValue().stream()
                  .filter(review -> review.getRating().getValue() < 6)
                  .count();
              int totalReviews = entry.getValue().size();
              return ((double) (ratingsMoreThenEight - ratingsLessThenSix) / totalReviews) * 100;
            }));
  }

  private void generateReport(Map<String, Double> stringDoubleMap) {
    try {
      JasperReport jasperReport = JasperCompileManager.compileReport(pathConfigurationReport);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new MapDataSource(
          stringDoubleMap));
      String filePath = pathResultReport;
      JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

    } catch (Exception ex) {
      throw new ReportException(ex.getMessage());
    }
  }
}
