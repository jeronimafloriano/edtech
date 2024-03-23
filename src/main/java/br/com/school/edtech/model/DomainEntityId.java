package br.com.school.edtech.model;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@MappedSuperclass
public abstract class DomainEntityId implements Serializable {

  private final UUID id;

  protected DomainEntityId() {
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DomainEntityId that = (DomainEntityId) o;

    return new EqualsBuilder().append(id, that.id).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).toHashCode();
  }

  @Override
  public String toString() {
    return getId().toString();
  }
}
