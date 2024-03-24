package br.com.school.edtech.course.application.service;

import br.com.school.edtech.course.application.dto.CourseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CourseService {

  List<CourseDto> getAll(Pageable pageable, String filter);

  CourseDto create(CourseDto courseDto);

  CourseDto inactivate(String code);
}
