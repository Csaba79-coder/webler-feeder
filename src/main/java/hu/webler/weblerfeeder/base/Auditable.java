package hu.webler.weblerfeeder.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import static java.time.LocalDate.now;

@MappedSuperclass
@Getter
public class Auditable extends Identifier {

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate createdAt = now();
}
