package br.com.school.edtech.feedback.application.service;

import br.com.school.edtech.feedback.application.dto.CourseReviewDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CourseReviewService {

  List<CourseReviewDto> getNPS(Pageable pageable);

  CourseReviewDto register(CourseReviewDto courseReviewDto);
}
