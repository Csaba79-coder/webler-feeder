package hu.webler.weblerfeeder.customer.entity;

import hu.webler.weblerfeeder.base.Auditable;
import hu.webler.weblerfeeder.order.entity.Order;
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
    private String streetAndNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postalCode;

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
    private List<Order> orders;
}
