package hu.webler.weblerfeeder.order.entity;

import hu.webler.weblerfeeder.base.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "food_order")
public class Order extends Auditable {

    private String address;
    private String description;
}
