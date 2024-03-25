package br.com.school.edtech.shared.exceptions;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Validations {

  private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z]+$");
  private static final Pattern COURSE_CODE_PATTERN = Pattern.compile("^[a-zA-Z]+(-[a-zA-Z]+)*$");

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

  public static void isValidCourseCodeFormat(String code, ValidationMessage validationMessage) {
    if (!COURSE_CODE_PATTERN.matcher(code).matches()) {
      throw new InvalidArgumentException(validationMessage);
    }
  }
}
