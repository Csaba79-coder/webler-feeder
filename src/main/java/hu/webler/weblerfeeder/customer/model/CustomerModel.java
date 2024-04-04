package hu.webler.weblerfeeder.customer.model;

import hu.webler.weblerfeeder.value.Status;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {

    private Long id;
    private LocalDateTime registrationDate;
    private String firstName;
    private String midName;
    private String lastName;
    private String cell;
    private String email;
    private LocalDate dateOfBirth;
    private Status status;
}
