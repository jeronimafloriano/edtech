package br.com.school.edtech.course.domain.repository;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.CourseId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, CourseId>,
    JpaSpecificationExecutor<Course> {

  Optional<Course> findByCode(String code);

  @Query("SELECT c FROM Course c WHERE SIZE(c.enrollments) > 4")
  List<Course> findCoursesWithMoreThanFourEnrollments();
}
