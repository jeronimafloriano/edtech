package br.com.school.edtech.user.application.service;


import br.com.school.edtech.model.exceptions.NotFoundException;
import br.com.school.edtech.model.exceptions.ValidationMessage;
import br.com.school.edtech.model.exceptions.Validations;
import br.com.school.edtech.user.application.dto.UserDto;
import br.com.school.edtech.user.domain.model.Email;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
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
    User user = new User(userDto.getName(), userDto.getUsername(), email, userDto.getRole());
    userRepository.save(user);
    return UserDto.map(user);
  }
}
