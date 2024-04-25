package hu.webler.weblerfeeder.food.service;

import hu.webler.weblerfeeder.exception.EntityAlreadyExistsException;
import hu.webler.weblerfeeder.food.entity.Food;
import hu.webler.weblerfeeder.food.model.FoodCreateAndUpdateModel;
import hu.webler.weblerfeeder.food.model.FoodModel;
import hu.webler.weblerfeeder.food.repository.FoodRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static hu.webler.weblerfeeder.util.FoodMapper.mapFoodCreateAndUpdateModelToFoodEntity;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Food service test - unit test")
public class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodService foodService;

    @Test
    @DisplayName("Given valid food create model then returns food model")
    public void givenValidFoodCreateModel_whenAddFood_thenReturnsFoodModel() {
        //Given
        FoodCreateAndUpdateModel foodCreateAndUpdateModel = new FoodCreateAndUpdateModel();
        foodCreateAndUpdateModel.setName("Sajt");
        foodCreateAndUpdateModel.setDescription("Hasábbal");
        foodCreateAndUpdateModel.setPrice(2000D);
        foodCreateAndUpdateModel.setFoodPic("/images/defaultFood.png");

        //Mock customerRepository.save() to return a mock CustomerModel
        FoodModel expectedFood = new FoodModel();
        expectedFood.setName("Sajt");
        expectedFood.setDescription("Hasábbal");
        expectedFood.setPrice(2000D);
        expectedFood.setFoodPic("/images/defaultFood.png");

        when(foodRepository.save(any())).thenReturn(mapFoodCreateAndUpdateModelToFoodEntity(foodCreateAndUpdateModel));

        //When
        FoodModel createdFood = foodService.addFood(foodCreateAndUpdateModel);

        //Then
        then(expectedFood)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(createdFood);
    }

    @Test
    @DisplayName("Given valid food create model when addFood then throws food already exists exception")
    public void givenValidFoodCreateModel_whenAddFood_thenThrowsFoodAlreadyExistsException() {
        //Given
        String foodName = "Sajt";
        FoodCreateAndUpdateModel foodCreateAndUpdateModel = new FoodCreateAndUpdateModel();
        foodCreateAndUpdateModel.setName("Sajt");
        foodCreateAndUpdateModel.setDescription("Krumplival");
        foodCreateAndUpdateModel.setPrice(1500D);
        foodCreateAndUpdateModel.setFoodPic("/images/defaultFood.png");

        when(foodRepository.save(any())).thenReturn(mapFoodCreateAndUpdateModelToFoodEntity(foodCreateAndUpdateModel))
                .thenThrow(new EntityAlreadyExistsException(String.format("Food with this name %s already exists", foodName)));

        //When
        foodService.addFood(foodCreateAndUpdateModel);

        //Then
        assertThatThrownBy(() -> foodService.addFood(foodCreateAndUpdateModel))
                .isInstanceOf(EntityAlreadyExistsException.class)
                .hasMessage("Food with this name %s already exists", foodName);
    }

    @Test
    @DisplayName("Given empty food list when getAllFoods then returns empty list")
    public void givenEmptyFoodList_whenGetAllFoods_thenReturnsEmptyList() {
        //Given
        when(foodRepository.findAll()).thenReturn(Collections.emptyList());

        //When
        List<FoodModel> foods = foodService.getAllFoods();

        //Then
        then(foods).isEmpty();
    }

    @Test
    @DisplayName("Given a non empty food list when getAllCustomers then returns a list of food models")
    public void givenNonEmptyFoodList_whenGetAllFoods_thenReturnsListOfFoodModels() {
        //Given
        List<Food> foodData = List.of(
                new Food("Sajt",
                        "Hasábbal",
                        2000D,
                        "/images/defaultFood.png"),
                new Food("Hamburger",
                        "Krumplival",
                        2000D,
                        "/images/defaultFood.png")
        );
        when(foodRepository.findAll()).thenReturn(foodData);

        //When
        List<FoodModel> foods = foodService.getAllFoods();

        //Then
        then(foods).hasSize(2)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(foodData);
    }

    @Test
    @DisplayName("Given valid food name when getFoodByName then returns food with that name")
    public void givenValidFoodName_whenGetFoodByName_thenReturnsFoodWithThatName() {
        //Given
        String foodName = "Sajt";
        Food food = new Food();
        food.setName("Sajt");
        food.setDescription("");
        food.setPrice(2000D);
        food.setFoodPic("/images/defaultFood.png");

        when(foodRepository.findByName(foodName)).thenReturn(Optional.of(food));

        //When
        FoodModel existingFood = foodService.getFoodByName(foodName);

        //Then
        then(existingFood.getName()).isEqualTo(food.getName());
    }

    @Test
    @DisplayName("Given food name when getFoodByName then throws no such element exception")
    public void givenFoodName_whenGetFoodByName_thenThrowsNoSuchElementException() {
        //Given
        String foodName = "Sajt";

        //When / Then
        assertThatThrownBy(() -> foodService.getFoodByName(foodName))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Food not found with name: %s", foodName);

        //Ensure foodRepository.save() is not called
        verify(foodRepository, never()).save(any());
    }

    @Test
    @DisplayName("Given food id when getFoodById then returns food")
    public void givenFoodId_whenGetFoodById_thenReturnsFood() {
        //Given
        Long id = 1L;
        Food food = new Food();
        food.setName("Sajt");
        food.setDescription("Hasábbal");
        food.setPrice(2000D);
        food.setFoodPic("/images/defaultFood.png");

        when(foodRepository.findById(id)).thenReturn(Optional.of(food));

        //When
        Food existingFood = foodService.getFoodById(id);

        //Then
        then(existingFood).isEqualTo(food);
    }

    @Test
    @DisplayName("Given food id when getFoodById then throws no such element exception")
    public void givenFoodId_whenGetFoodById_thenThrowsNoSuchElementException() {
        //Given
        Long id = 1L;

        //When / Then
        assertThatThrownBy(() -> foodService.getFoodById(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Food not found with ID: %s", id);
    }
}
