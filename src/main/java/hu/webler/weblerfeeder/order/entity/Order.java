package hu.webler.weblerfeeder.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hu.webler.weblerfeeder.base.Auditable;
import hu.webler.weblerfeeder.customer.entity.Customer;
import hu.webler.weblerfeeder.food.entity.Food;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "food_order")
public class Order extends Auditable {

    @Column(nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_food_ref",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private List<Food> foods;
}
