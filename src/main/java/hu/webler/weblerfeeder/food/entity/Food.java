package hu.webler.weblerfeeder.food.entity;
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
public class Food extends Auditable {

    private String name;
    private String description;   // soup or main or dessert enum?
    private Double price;
}
