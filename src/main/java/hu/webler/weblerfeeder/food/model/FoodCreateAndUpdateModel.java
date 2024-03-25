package hu.webler.weblerfeeder.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodCreateAndUpdateModel {

    private Long id;
    private LocalDateTime createdAt;
    private String name;
    private String description;
    private Double price;
}
