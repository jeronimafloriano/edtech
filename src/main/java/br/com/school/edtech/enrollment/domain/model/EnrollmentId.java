package br.com.school.edtech.enrollment.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class EnrollmentId implements Serializable {

  private UUID value;

  public EnrollmentId() {
    this.value = UUID.randomUUID();
  }

  public EnrollmentId(UUID value) {
    this.value = value;
  }

  public static EnrollmentId of(UUID id) {
    return new EnrollmentId(id);
  }

  public UUID getValue() {
    return value;
  }

  public void setValue(UUID value) {
    this.value = value;
  }
}
