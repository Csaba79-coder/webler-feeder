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

    private String firstName;
    private String midName;
    private String lastName;
    private String cell;
    private String email;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;
}
