package hu.webler.weblerfeeder.address.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressModelUpdateCreate {

    private String streetAndNumber;
    private String city;
    private String postalCode;
}
