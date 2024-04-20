package hu.webler.weblerfeeder.address.repository;

import hu.webler.weblerfeeder.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
