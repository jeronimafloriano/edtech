package br.com.school.edtech.user.domain.model;

import br.com.school.edtech.model.DomainEntityId;
import br.com.school.edtech.model.exceptions.ValidationMessage;
import br.com.school.edtech.model.exceptions.Validations;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class User extends DomainEntityId {

  @Column(name = "name")
  private String name;

  @Column(name = "username")
  private String username;

  @Embedded
  private Email email;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;

  @Column(name = "creationDate")
  private Instant creationDate;

  public User(String name, String username, Email email,
      Role role) {
    super();

    Validations.isNotBlank(name, ValidationMessage.REQUIRED_NAME);
    Validations.isNotBlank(username, ValidationMessage.REQUIRED_USERNAME);
    Validations.isNotNull(email, ValidationMessage.REQUIRED_EMAIL);
    Validations.isNotNull(role, ValidationMessage.REQUIRED_ROLE);
    Validations.isValidUsernameFormat(username, ValidationMessage.INVALID_USERNAME);

    this.name = name;
    this.username = username;
    this.email = email;
    this.role = role;
    this.creationDate = Instant.now();
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public Role getRole() {
    return role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    return new EqualsBuilder().appendSuper(super.equals(o))
        .append(username, user.username).append(email, user.email).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(username).append(email)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("name", name)
        .append("username", username)
        .append("email", email)
        .append("role", role)
        .append("creationDate", creationDate)
        .toString();
  }
}
