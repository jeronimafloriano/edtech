package br.com.school.edtech.feedback.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class CourseReviewId implements Serializable {

  private UUID value;

  public CourseReviewId() {
    this.value = UUID.randomUUID();
  }

  public CourseReviewId(UUID value) {
    this.value = value;
  }

  public static CourseReviewId of(UUID id) {
    return new CourseReviewId(id);
  }

  public UUID getValue() {
    return value;
  }

  public void setValue(UUID value) {
    this.value = value;
  }
}
