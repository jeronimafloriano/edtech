package br.com.school.edtech.model.exceptions;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Validations {

  private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z]+$");

  public static void isNotBlank(CharSequence value, ValidationMessage validationMessage) {
    if(Objects.isNull(value)) {
      throw new RequiredArgumentException(validationMessage);
    } else if(value.equals("")) {
      throw new InvalidArgumentException(validationMessage);
    }
  }

  public static void isNotNull(Object object, ValidationMessage validationMessage) {
    if(Objects.isNull(object)) {
      throw new RequiredArgumentException(validationMessage);
    }
  }

  public static void isValidUsernameFormat(String username, ValidationMessage validationMessage) {
    if (!USERNAME_PATTERN.matcher(username).matches()) {
      throw new InvalidArgumentException(validationMessage);
    }
  }

}
