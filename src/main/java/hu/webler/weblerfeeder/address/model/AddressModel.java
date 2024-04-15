package hu.webler.weblerfeeder.address.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressModel {

    private Long id;
    private LocalDateTime registrationDate;
    private String streetAndNumber;
    private String city;
    private String postalCode;
}
