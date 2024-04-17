package hu.webler.weblerfeeder.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@MappedSuperclass
@Getter
public class Auditable extends Identifier {

    @CreationTimestamp
    @Column(nullable = false)
    private final LocalDateTime createdAt = now();
}
