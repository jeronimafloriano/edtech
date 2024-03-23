package br.com.school.edtech.shared.model.exceptions;


import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredArgumentException extends ValidationException {

  public RequiredArgumentException(ValidationMessage message) {
    super(message.getMessage());
  }

}
