package br.com.school.edtech.shared.finder.impl;

import br.com.school.edtech.shared.exceptions.NotFoundException;
import br.com.school.edtech.shared.exceptions.ValidationMessage;
import br.com.school.edtech.shared.finder.UserFinder;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.model.UserId;
import br.com.school.edtech.user.domain.repository.UserRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserFinderImpl implements UserFinder {

  private final UserRepository repository;

  public UserFinderImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User findById(UUID userId) {
    return repository.findById(UserId.of(userId))
        .orElseThrow(() -> {
          throw new NotFoundException(ValidationMessage.USER_NOT_FOUND, userId);
        });
  }
}
