package br.com.school.edtech.user.application.api;

import br.com.school.edtech.user.application.dto.UserDto;
import br.com.school.edtech.user.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
