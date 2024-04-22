package hu.webler.weblerfeeder.util;

import hu.webler.weblerfeeder.food.entity.Food;
import hu.webler.weblerfeeder.food.model.FoodCreateAndUpdateModel;
import hu.webler.weblerfeeder.food.model.FoodModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hu.webler.weblerfeeder.util.FoodMapper.mapFoodCreateAndUpdateModelToFoodEntity;
import static org.assertj.core.api.BDDAssertions.then;

public class FoodMapperTest {

    @Test
    @DisplayName("Given food create model when mapping to entity then returns food entity")
    public void givenFoodCreateModel_whenMappingToEntity_thenReturnsFoodEntity() {
        //Given
        FoodCreateAndUpdateModel foodCreateAndUpdateModel = new FoodCreateAndUpdateModel();
        foodCreateAndUpdateModel.setName("Sajt");
        foodCreateAndUpdateModel.setDescription("Hasábbal");
        foodCreateAndUpdateModel.setPrice(2000D);

        //When
        Food food = mapFoodCreateAndUpdateModelToFoodEntity(foodCreateAndUpdateModel);

        //Then
        then(foodCreateAndUpdateModel)
                .usingRecursiveComparison()
                .isEqualTo(food);
    }

    @Test
    @DisplayName("Given food entity when mapping to food model then returns food model")
    public void givenFoodEntity_whenMappingToFoodModel_thenReturnsFoodModel() {
        //Given
        Food food = new Food();
        food.setName("Sajt");
        food.setDescription("Hasábbal");
        food.setPrice(2000D);

        FoodModel expectedFoodModel = new FoodModel();
        expectedFoodModel.setName("Sajt");
        expectedFoodModel.setDescription("Hasábbal");
        expectedFoodModel.setPrice(2000D);

        //When
        FoodModel foodModel = FoodMapper.mapFoodEntityToFoodModel(food);

        //Then
        then(foodModel)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(expectedFoodModel);
    }
}
