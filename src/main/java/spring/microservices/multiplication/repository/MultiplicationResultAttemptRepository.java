package spring.microservices.multiplication.repository;

import org.springframework.data.repository.CrudRepository;
import spring.microservices.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationResultAttemptRepository extends CrudRepository<MultiplicationResultAttempt,Long> {
    /**
     * @return the latest 5 attempts for a given user,
    identified by their alias.
     */
    List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String alias);
}
