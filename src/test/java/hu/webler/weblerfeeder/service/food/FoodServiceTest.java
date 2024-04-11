package hu.webler.weblerfeeder.service.food;


import hu.webler.weblerfeeder.food.model.FoodCreateAndUpdateModel;
import hu.webler.weblerfeeder.food.model.FoodModel;
import hu.webler.weblerfeeder.food.repository.FoodRepository;
import hu.webler.weblerfeeder.food.service.FoodService;
import hu.webler.weblerfeeder.util.FoodMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static  hu.webler.weblerfeeder.util.FoodMapper.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Food service test - unit test")
public class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodService foodService;

    @Test
    @DisplayName("Given valid Food create model when addFood then returns Food model")
    public void givenValidFoodCreateModel_whenAddFood_thenReturnsFoodModel() {

        //Given
        FoodCreateAndUpdateModel foodCreateAndUpdateModel = new FoodCreateAndUpdateModel();
        foodCreateAndUpdateModel.setName("Melon");
        foodCreateAndUpdateModel.setDescription("Fruit");
        foodCreateAndUpdateModel.setPrice(100.0);

        //Mock foodRepository.save() to return a mock FoodModel

        FoodModel expectedFood = new FoodModel();
        expectedFood.setName("Melon");
        expectedFood.setDescription("Fruit");
        expectedFood.setPrice(100.0);

        //when save
        when(foodRepository.save(any())).thenReturn(mapFoodCreateAndUpdateModelToFoodEntity(foodCreateAndUpdateModel));

        //when
        FoodModel createdFood = foodService.addFood(foodCreateAndUpdateModel);

        //then
        then(createdFood.getDescription()).isEqualTo(foodCreateAndUpdateModel.getDescription());

        System.out.println(createdFood.getDescription());

        verify(foodRepository, times(1)).save(any());

        then(expectedFood)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(createdFood);
    }
}
