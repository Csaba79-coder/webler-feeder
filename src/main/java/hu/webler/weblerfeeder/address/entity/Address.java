package hu.webler.weblerfeeder.address.entity;

import hu.webler.weblerfeeder.base.Auditable;
import hu.webler.weblerfeeder.customer.entity.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends Auditable {

    @Column(nullable = false)
    private String streetAndNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postalCode;

    @OneToOne(mappedBy = "address")
    private Customer customer;
}