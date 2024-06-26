package hu.webler.weblerfeeder.order.model;

import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.order.entity.Order;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderModel {

    private Long id;
    private LocalDateTime createdAt;
    private String description;
    private Customer customer;
}
