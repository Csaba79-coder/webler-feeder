package hu.webler.weblerfeeder.order.model;

import hu.webler.weblerfeeder.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateAndUpdateModel {

    private String description;
    private Customer customer;
}
