package br.com.school.edtech.user.application.service.impl;


import br.com.school.edtech.config.auth.JwtService;
import br.com.school.edtech.config.auth.dto.LoginReqDto;
import br.com.school.edtech.config.auth.dto.LoginResDto;
import br.com.school.edtech.shared.exceptions.DuplicatedException;
import br.com.school.edtech.shared.exceptions.InvalidArgumentException;
import br.com.school.edtech.shared.exceptions.NotFoundException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.exceptions.Validations;
import br.com.school.edtech.user.application.dto.UserDto;
import br.com.school.edtech.user.application.service.UserService;
import br.com.school.edtech.user.domain.model.Email;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, JwtService jwtService,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional(readOnly = true)
  @Override
  public UserDto getByUsername(String username) {
    Validations.isNotNull(username, ValidationMessage.REQUIRED_USERNAME);

    User user = userRepository.findByUsername(username).orElseThrow(() ->
         new NotFoundException(ValidationMessage.USER_NOT_FOUND, username)
     );
     return UserDto.map(user);
  }

  @Transactional
  @Override
  public UserDto register(UserDto userDto) {
    Validations.isNotNull(userDto, ValidationMessage.REQUIRED_USER);

    Email email = Email.of(userDto.getEmail());

    validateDuplicityUsername(userDto.getUsername());
    validateDuplicityEmail(email);

    String encode = passwordEncoder.encode(userDto.getPassword());
    User user = new User(userDto.getName(), userDto.getUsername(),
        email, userDto.getRole(), encode);
    userRepository.save(user);
    return UserDto.map(user);
  }

  public LoginResDto login(LoginReqDto body) {
    User user = userRepository.findByEmail(Email.of(body.getEmail()))
        .orElseThrow(() -> new NotFoundException(ValidationMessage.USER_NOT_FOUND, body.getEmail()));

    if (!passwordEncoder.matches(body.getPassword(), user.getPassword())) {
      throw new InvalidArgumentException(ValidationMessage.INCORRECT_PASSWORD);
    }
    String token = jwtService.generateToke2(user.getId().getValue(),
        user.getEmail().getAddress());
    return new LoginResDto(token);
  }

  private void validateDuplicityEmail(Email email) {
    userRepository.findByEmail(email)
        .ifPresent(user -> {
          throw new DuplicatedException(ValidationMessage.EMAIL_ALREADY_REGISTERED);
        });
  }

  private void validateDuplicityUsername(String username) {
    userRepository.findByUsername(username)
        .ifPresent(user -> {
          throw new DuplicatedException(ValidationMessage.USERNAME_ALREADY_REGISTERED);
        });
  }

}
