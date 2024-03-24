package br.com.school.edtech.feedback.application.dto;

import br.com.school.edtech.course.application.dto.CourseDto;
import br.com.school.edtech.feedback.domain.model.CourseReview;
import br.com.school.edtech.feedback.domain.model.Rating;
import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.shared.model.exceptions.Validations;
import br.com.school.edtech.user.application.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class CourseReviewDto {


  @JsonProperty(access = Access.READ_ONLY)
  private UUID id;

  @JsonProperty(access = Access.READ_ONLY)
  private UserDto user;

  @JsonProperty(access = Access.WRITE_ONLY)
  @NotNull
  private UUID idUser;

  @JsonProperty(access = Access.READ_ONLY)
  private CourseDto course;

  @JsonProperty(access = Access.WRITE_ONLY)
  @NotNull
  private UUID idCourse;

  @NotNull
  private Rating rating;

  @NotNull
  private String justification;

  public static CourseReviewDto map(CourseReview courseReview) {
    Validations.isNotNull(courseReview, ValidationMessage.REQUIRED_COURSE_REVIEW);

    CourseReviewDto courseReviewDto = new CourseReviewDto();
    courseReviewDto.setId(courseReview.getId().getValue());
    courseReviewDto.setUser(UserDto.map(courseReview.getUser()));
    courseReviewDto.setCourse(CourseDto.map(courseReview.getCourse()));
    courseReviewDto.setRating(courseReview.getRating());
    courseReviewDto.setJustification(courseReview.getJustification());

    return courseReviewDto;
  }
}
