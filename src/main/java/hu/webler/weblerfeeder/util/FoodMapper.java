package hu.webler.weblerfeeder.util;

import hu.webler.weblerfeeder.food.entity.Food;
import hu.webler.weblerfeeder.food.model.FoodCreateAndUpdateModel;
import hu.webler.weblerfeeder.food.model.FoodModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FoodMapper {

    public static FoodModel mapFoodEntityToFoodModel(Food food) {
        return FoodModel
                .builder()
                .id(food.getId())
                .createdAt(food.getCreatedAt())
                .name(food.getName())
                .description(food.getDescription())
                .price(food.getPrice())
                .foodPic(food.getFoodPic())
                .build();
    }

    public static Food mapFoodCreateAndUpdateModelToFoodEntity(FoodCreateAndUpdateModel foodCreateAndUpdateModel) {
        return Food
                .builder()
                .name(foodCreateAndUpdateModel.getName())
                .description(foodCreateAndUpdateModel.getDescription())
                .price(foodCreateAndUpdateModel.getPrice())
                .foodPic(foodCreateAndUpdateModel.getFoodPic())
                .build();
    }
}
