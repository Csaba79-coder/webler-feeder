package hu.webler.weblerfeeder.order.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    private Long id;

    private LocalDateTime createdAt;
    private String address;

    private String description;
}
