package br.com.school.edtech.shared.finder;

import br.com.school.edtech.user.domain.model.User;
import java.util.UUID;

public interface UserFinder {

  User findById(UUID userId);
}
