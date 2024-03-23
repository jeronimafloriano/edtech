package br.com.school.edtech.user.domain.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

@Embeddable
@Access(AccessType.FIELD)
public class Email {

  @NotNull
  private String address;

  private Email(String address) {
    EmailValidator validator = new EmailValidator();

    if (!validator.isValid(address, null)) {
      throw new IllegalArgumentException();
    }

    this.address = address;
  }

  public static Email of(String address) {
    return new Email(address);
  }

  public String getAddress() {
    return address;
  }
}
