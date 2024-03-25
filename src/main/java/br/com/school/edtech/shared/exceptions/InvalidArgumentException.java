package br.com.school.edtech.shared.exceptions;


import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends ValidationException {

  public InvalidArgumentException(ValidationMessage message) {
    super(message.getMessage());
  }

}
