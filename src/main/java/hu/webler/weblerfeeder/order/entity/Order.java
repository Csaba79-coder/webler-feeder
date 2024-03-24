package hu.webler.weblerfeeder.order.entity;

import hu.webler.weblerfeeder.base.Auditable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends Auditable {

    private String address;

    private String description;   // kapucsengő emelett stb.
    // customer + food customerOrder one-one, food customerorder one-to-many
}
