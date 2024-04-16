package hu.webler.weblerfeeder.customer.model;

import hu.webler.weblerfeeder.address.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateModel {

    private String firstName;
    private String midName;
    private String lastName;
    private String cell;
    private String email;
    private LocalDate dateOfBirth;
    private Address address;
}
