package hu.webler.weblerfeeder.model;

import hu.webler.weblerfeeder.value.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {

    private Long id;
    private String firstName;
    private String midName;
    private String lastName;
    private String cell;
    private String email;
    private LocalDateTime registrationDate;
    private Status status;
}
