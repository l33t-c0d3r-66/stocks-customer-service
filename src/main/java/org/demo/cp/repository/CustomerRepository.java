package org.demo.cp.repository;

import org.demo.cp.entity.Customer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
}
