package br.com.school.edtech.shared.model.exceptions;

public enum ValidationMessage {

  INVALID_EMAIL("0001", "Invalid email address!"),
  REQUIRED_NAME("0002", "Name is required."),
  REQUIRED_USERNAME("0003", "Username is required."),
  REQUIRED_EMAIL("0004", "Email is required."),
  REQUIRED_ROLE("0005", "Role is required."),
  INVALID_USERNAME("0006", "The username must contain only lowercase characters, no numerals and no spaces."),
  REQUIRED_USER("0007", "User is required."),
  USER_NOT_FOUND("0008", "User not found with the given name."),
  EMAIL_ALREADY_REGISTERED("0009", "Email already registered."),
  USERNAME_ALREADY_REGISTERED("0009", "Email already registered."),
  REQUIRED_COURSE_CODE("00010", "Course code is required."),
  REQUIRED_INSTRUCTOR("00011", "Course instructor is required."),
  REQUIRED_DESCRIPTION("00012", "Description is required."),
  INVALID_COURSE_CODE("00013", "A course code must be textual, with no spaces, \n"
      + "numeric characters or special characters."),
  REQUIRED_COURSE("00014", "Course is required."),
  COURSE_NOT_FOUND("00015", "Course not found with the given code."),
  COURSE_ALREADY_REGISTERED("00016", "Course already registered with the given code."),
  REQUIRED_USER_INSTRUCTOR("00017", "Only users of the instructor type can be instructors of a course.");

  private final String code;
  private final String message;

  ValidationMessage(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
