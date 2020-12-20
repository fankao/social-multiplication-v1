package spring.microservices.multiplication.repository;

import org.springframework.data.repository.CrudRepository;

public interface MultiplicationRepository extends CrudRepository<MultiplicationRepository,Long> {
}
