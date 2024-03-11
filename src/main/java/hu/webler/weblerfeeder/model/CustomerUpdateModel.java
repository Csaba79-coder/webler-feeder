package hu.webler.weblerfeeder.model;

import hu.webler.weblerfeeder.value.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateModel {

    private String firstName;
    private String midName;
    private String lastName;
    private String cell;
    private String email;
    private Status status;
}
