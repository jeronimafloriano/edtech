package br.com.school.edtech.shared.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@MappedSuperclass
public abstract class DomainEntityId<ID> implements Serializable {

  @EmbeddedId
  private ID id;

  protected DomainEntityId() {
  }

  protected DomainEntityId(ID id) {
    this.id = id;
  }

  public ID getId() {
    return id;
  }

  public void setId(ID id) {
    this.id = id;
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
