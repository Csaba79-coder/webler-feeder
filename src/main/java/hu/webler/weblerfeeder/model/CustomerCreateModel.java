package hu.webler.weblerfeeder.model;

import hu.webler.weblerfeeder.value.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime registrationDate = LocalDateTime.now();
    private Status status = Status.INACTIVE;
}
