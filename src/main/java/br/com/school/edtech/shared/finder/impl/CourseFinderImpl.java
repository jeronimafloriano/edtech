package br.com.school.edtech.shared.finder.impl;

import br.com.school.edtech.course.domain.model.Course;
import br.com.school.edtech.course.domain.model.CourseId;
import br.com.school.edtech.course.domain.repository.CourseRepository;
import br.com.school.edtech.shared.exceptions.NotFoundException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.finder.CourseFinder;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CourseFinderImpl implements CourseFinder {

  private final CourseRepository repository;

  public CourseFinderImpl(CourseRepository repository) {
    this.repository = repository;
  }

  @Override
  public Course findById(UUID userId) {
    return repository.findById(CourseId.of(userId))
        .orElseThrow(() -> {
          throw new NotFoundException(ValidationMessage.COURSE_NOT_FOUND, userId);
        });
  }

  @Override
  public List<Course> findCoursesWithMoreThanFourEnrollments() {
    return repository.findCoursesWithMoreThanFourEnrollments();
  }
}
