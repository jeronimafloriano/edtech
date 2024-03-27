package br.com.school.edtech.user.application.api;

import br.com.school.edtech.config.auth.dto.LoginReqDto;
import br.com.school.edtech.config.auth.dto.LoginResDto;
import br.com.school.edtech.user.application.dto.UserDto;
import br.com.school.edtech.user.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "User manager")
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(description = "Search for a user by their username")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User created")})
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  @GetMapping("/{username}")
  public UserDto getByUsername(
      @Parameter(description = "Username to be searched") @PathVariable String username) {
    return userService.getByUsername(username);
  }

  @Operation(description = "Register a user")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User created")})
  @PostMapping()
  public UserDto register(
      @Parameter(description = "User information") @RequestBody
          UserDto userDto) {
    return userService.register(userDto);
  }

  @Operation(description = "Log a user into the api")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Logged user")})
  @PostMapping("/login")
  public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto body) {
    return new ResponseEntity<>(userService.login(body), HttpStatus.OK);
  }
}
