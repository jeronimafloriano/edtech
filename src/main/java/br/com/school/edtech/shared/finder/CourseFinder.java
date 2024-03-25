package br.com.school.edtech.shared.finder;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.user.domain.model.User;
import java.util.List;
import java.util.UUID;

public interface CourseFinder {

  Course findById(UUID userId);

  List<Course> findCoursesWithMoreThanFourEnrollments();
}
