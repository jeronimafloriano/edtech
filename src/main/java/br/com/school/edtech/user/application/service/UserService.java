package br.com.school.edtech.user.application.service;

import br.com.school.edtech.user.application.dto.UserDto;

public interface UserService {

  UserDto getByUsername(String username);

  UserDto register(UserDto userDto);
}
