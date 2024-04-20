package hu.webler.weblerfeeder.food.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodModel {

    private Long id;
    private LocalDateTime createdAt;
    private String name;
    private String description;
    private Double price;
}
