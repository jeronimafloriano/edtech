package br.com.school.edtech.shared.exceptions;


import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReportException extends ValidationException {

  public ReportException(String message) {
    super(message);
  }

}
