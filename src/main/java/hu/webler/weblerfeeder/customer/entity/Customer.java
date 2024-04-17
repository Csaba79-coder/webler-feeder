package hu.webler.weblerfeeder.customer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hu.webler.weblerfeeder.base.Auditable;
import hu.webler.weblerfeeder.order.entity.Order;
import hu.webler.weblerfeeder.address.entity.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import hu.webler.weblerfeeder.value.Status;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends Auditable {

    @Column(nullable = false)
    private String firstName;

    private String midName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String cell;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @JsonManagedReference
    private Address address;
}
