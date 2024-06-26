package br.com.school.edtech.user.application.dto;

import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.exceptions.Validations;
import br.com.school.edtech.user.domain.model.Role;
import br.com.school.edtech.user.domain.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private UUID id;

  @NotNull
  private String name;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Size(max = 20, message = "The name must have a maximum of 20 characters.")
  @NotNull
  private String username;

  @NotNull
  private String email;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Size(max = 20, message = "The password must have a maximum of 20 characters.")
  @NotNull
  private String password;

  @NotNull
  private Role role;

  public UserDto(String name, String username, String email, Role role, String password) {
    this.name = name;
    this.username = username;
    this.email = email;
    this.role = role;
    this.password = password;
  }

  public static UserDto map(User user) {
    Validations.isNotNull(user, ValidationMessage.REQUIRED_USER);

    UserDto userDto = new UserDto();
    userDto.setId(user.getId().getValue());
    userDto.setName(user.getName());
    userDto.setUsername(user.getUsername());
    userDto.setEmail(user.getEmail().getAddress());
    userDto.setRole(user.getRole());

    return userDto;
  }
}
