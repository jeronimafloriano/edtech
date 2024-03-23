package br.com.school.edtech.model.exceptions;

public enum ValidationMessage {

  INVALID_EMAIL("0001", "Invalid email address!"),
  REQUIRED_NAME("0002", "Name is required."),
  REQUIRED_USERNAME("0003", "Username is required."),
  REQUIRED_EMAIL("0004", "Email is required."),
  REQUIRED_ROLE("0005", "Role is required."),
  INVALID_USERNAME("0006", "The username must contain only lowercase characters, no numerals and no spaces."),
  REQUIRED_USER("0007", "User is required."),
  USER_NOT_FOUND("0008", "User not found with the given name.");

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
