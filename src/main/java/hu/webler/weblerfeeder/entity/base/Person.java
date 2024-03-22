package hu.webler.weblerfeeder.entity.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Person {

    private String firstName;
    private String midName;
    private String lastName;
    private String cell;
    private String email;
}
