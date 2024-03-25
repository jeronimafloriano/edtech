package br.com.school.edtech.enrollment.domain.repository;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.enrollment.domain.model.Enrollment;
import br.com.school.edtech.enrollment.domain.model.EnrollmentId;
import br.com.school.edtech.user.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

  Optional<Enrollment> findByCourseAndUser(Course course, User user);
}
