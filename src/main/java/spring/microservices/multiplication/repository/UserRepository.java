package spring.microservices.multiplication.repository;

import org.springframework.data.repository.CrudRepository;
import spring.microservices.multiplication.domain.User;

import java.util.Optional;

public interface UserRepository  extends CrudRepository<User,Long> {
    Optional<User> findByAlias(String alias);
}
