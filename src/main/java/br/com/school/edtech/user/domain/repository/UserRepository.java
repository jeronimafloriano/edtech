package br.com.school.edtech.user.domain.repository;

import br.com.school.edtech.shared.model.DomainEntityId;
import br.com.school.edtech.user.domain.model.Email;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.model.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(Email email);
}
