package br.com.school.edtech.feedback.domain.repository;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.CourseId;
import br.com.school.edtech.feedback.domain.model.CourseReview;
import br.com.school.edtech.feedback.domain.model.CourseReviewId;
import br.com.school.edtech.user.domain.model.UserId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, CourseReviewId>,
    JpaSpecificationExecutor<Course> {

  Optional<CourseReview> findByCourseIdAndUserId(CourseId courseId, UserId userId);

  @Query("SELECT cr FROM CourseReview cr WHERE cr.course IN :courses")
  List<CourseReview> findReviewsByCourses(List<Course> courses);
}
