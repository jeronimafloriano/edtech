package br.com.school.edtech.feedback.application.service;

import br.com.school.edtech.feedback.application.dto.CourseReviewDto;
import java.util.Map;

public interface CourseReviewService {

  Map<String, Double> getNPS();

  CourseReviewDto register(CourseReviewDto courseReviewDto);
}
