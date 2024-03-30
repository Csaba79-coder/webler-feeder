package hu.webler.weblerfeeder.customer.entity;

import hu.webler.weblerfeeder.base.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import hu.webler.weblerfeeder.value.Status;

import java.time.LocalDate;

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

    private LocalDate dateOfBirth;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;
}
