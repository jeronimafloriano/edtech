package br.com.school.edtech.course.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class CourseId implements Serializable {

  private UUID value;

  public CourseId() {
    this.value = UUID.randomUUID();
  }

  public CourseId(UUID value) {
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }

  public void setValue(UUID value) {
    this.value = value;
  }
}
