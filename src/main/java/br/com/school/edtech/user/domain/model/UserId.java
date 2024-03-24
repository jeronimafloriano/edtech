package br.com.school.edtech.user.domain.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class UserId implements Serializable {

  private UUID value;

  public UserId() {
    this.value = UUID.randomUUID();
  }

  public UserId(UUID value) {
    this.value = value;
  }

  public static UserId of(UUID id) {
    return new UserId(id);
  }

  public UUID getValue() {
    return value;
  }

  public void setValue(UUID value) {
    this.value = value;
  }
}
