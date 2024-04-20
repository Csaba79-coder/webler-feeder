package hu.webler.weblerfeeder.customer.repository;

import hu.webler.weblerfeeder.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

    Optional<Customer> findByEmail(String email);
}
