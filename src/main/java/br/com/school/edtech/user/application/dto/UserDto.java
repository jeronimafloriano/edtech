package br.com.school.edtech.user.application.dto;

import br.com.school.edtech.shared.model.exceptions.ValidationMessage;
import br.com.school.edtech.shared.model.exceptions.Validations;
import br.com.school.edtech.user.domain.model.Role;
import br.com.school.edtech.user.domain.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

  @NotNull
  private String name;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Size(max = 20, message = "The name must have a maximum of 20 characters.")
  @NotNull
  private String username;

  @NotNull
  private String email;

  @NotNull
  private Role role;

  public static UserDto map(User user) {
    Validations.isNotNull(user, ValidationMessage.REQUIRED_USER);

    UserDto userDto = new UserDto();
    userDto.setName(user.getName());
    userDto.setEmail(user.getEmail().getAddress());
    userDto.setRole(user.getRole());

    return userDto;
  }
}
