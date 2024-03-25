package hu.webler.weblerfeeder.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodCreateModel {

    private String name;

    private String description;

    private Double price;
}
