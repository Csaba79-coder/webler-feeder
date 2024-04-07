package hu.webler.weblerfeeder.customer.model;

import hu.webler.weblerfeeder.value.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerModel {

    private Long id;
    private LocalDateTime registrationDate;
    private String firstName;
    private String midName;
    private String lastName;
    private String streetAndNumber;
    private String city;
    private String postalCode;
    private String cell;
    private String email;
    private LocalDate dateOfBirth;
    private Status status;
}
