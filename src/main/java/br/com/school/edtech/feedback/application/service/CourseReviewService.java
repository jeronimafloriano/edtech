package br.com.school.edtech.feedback.application.service;

import br.com.school.edtech.feedback.application.dto.CourseReviewDto;
import br.com.school.edtech.feedback.application.dto.Nps;
import java.util.List;

public interface CourseReviewService {

  List<Nps> getNPS();

  CourseReviewDto register(CourseReviewDto courseReviewDto);
}
