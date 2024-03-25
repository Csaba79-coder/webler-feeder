package hu.webler.weblerfeeder.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateAndUpdateModel {

    private String address;

    private String description;
}
