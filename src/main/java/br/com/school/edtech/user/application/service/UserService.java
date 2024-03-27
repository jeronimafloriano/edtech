package br.com.school.edtech.user.application.service;

import br.com.school.edtech.config.auth.dto.LoginReqDto;
import br.com.school.edtech.config.auth.dto.LoginResDto;
import br.com.school.edtech.user.application.dto.UserDto;

public interface UserService {

  UserDto getByUsername(String username);

  UserDto register(UserDto userDto);

  LoginResDto login(LoginReqDto body);
}
