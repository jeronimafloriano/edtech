package br.com.school.edtech.feedback.application.dto;

import br.com.school.edtech.feedback.domain.model.CourseReview;
import br.com.school.edtech.feedback.domain.model.Rating;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Nps {

  @NotNull
  private String course;
  @NotNull
  private Rating rating;

  public static Nps map(CourseReview courseReview) {
    return new Nps(courseReview.getCourse().getName(), courseReview.getRating());
  }
}
