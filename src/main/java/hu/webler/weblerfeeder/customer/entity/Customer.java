package hu.webler.weblerfeeder.customer.entity;

import hu.webler.weblerfeeder.base.Auditable;
import hu.webler.weblerfeeder.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import hu.webler.weblerfeeder.value.Status;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer extends Auditable {

    private String firstName;
    private String midName;
    private String lastName;
    private String cell;
    private String email;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;
}
