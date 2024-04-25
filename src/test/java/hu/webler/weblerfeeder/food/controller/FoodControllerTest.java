package hu.webler.weblerfeeder.food.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webler.weblerfeeder.base.Auditable;
import hu.webler.weblerfeeder.base.Identifier;
import hu.webler.weblerfeeder.food.entity.Food;
import hu.webler.weblerfeeder.food.model.FoodModel;
import hu.webler.weblerfeeder.food.service.FoodService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodController.class)
@ContextConfiguration(classes = {FoodController.class})
@ExtendWith(MockitoExtension.class)
@DisplayName("Food controller - Integration test")
public class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FoodService foodService;

    @Test
    @DisplayName("Given empty list when listAllFoods then return empty list")
    public void givenEmptyList_whenListAllFoods_thenReturnsEmptyList() throws Exception {
        //Given
        given(foodService.getAllFoods()).willReturn(Collections.emptyList());
        List<FoodModel> expectedModels = new ArrayList<>();

        //When
        MvcResult result = mockMvc.perform(get("/api/foods"))
                .andExpect(status().isOk())
                .andReturn();

        List<FoodModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        //Then
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isEmpty();
        assertThat(actualModels).isEqualTo(expectedModels);
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(0);
    }

    @Test
    @DisplayName("Given non-empty list when listAllFoods then return non empty list")
    public void givenNonEmptyList_whenListAllFoods_thenReturnsNonEmptyList() throws Exception {
        //Given
        FoodModel foodModelOne = new FoodModel();
        foodModelOne.setId(1L);
        foodModelOne.setCreatedAt(new Auditable().getCreatedAt());
        foodModelOne.setName("Sajt");
        foodModelOne.setDescription("");
        foodModelOne.setPrice(2000D);
        foodModelOne.setFoodPic("/images/defaultFood.png");

        FoodModel foodModelTwo = new FoodModel();
        foodModelTwo.setId(2L);
        foodModelTwo.setCreatedAt(new Auditable().getCreatedAt());
        foodModelTwo.setName("Hamburger");
        foodModelTwo.setDescription("");
        foodModelTwo.setPrice(2000D);
        foodModelTwo.setFoodPic("/images/defaultFood.png");

        List<FoodModel> expectedModels = Arrays.asList(
                foodModelOne, foodModelTwo
        );

        given(foodService.getAllFoods()).willReturn(expectedModels);

        //When
        MvcResult result = mockMvc.perform(get("/api/foods"))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<FoodModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isNotEmpty(); // Assert that the list is not empty
        assertThat(actualModels)
                .usingRecursiveComparison()
                .isEqualTo(expectedModels); // Assert that the lists are equal
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(expectedModels.size()); // Assert that the size of actual list matches the size of expected list
    }

    @Test
    @DisplayName("Given valid food name when getFoodByName then returns food with that name")
    public void givenValidFoodName_whenGetFoodByName_thenReturnsFoodWithThatName() throws Exception {
        //Given
        String foodName = "Sajt"; // valid food name

        //Mock the foodService to return a food model
        FoodModel foodModel = new FoodModel();
        foodModel.setId(new Identifier().getId());
        foodModel.setCreatedAt(new Auditable().getCreatedAt());
        foodModel.setName("Sajt");
        foodModel.setDescription("");
        foodModel.setPrice(2000D);
        foodModel.setFoodPic("/images/defaultFood.png");

        when(foodService.getFoodByName(any(String.class))).thenReturn(foodModel);

        //When
        MvcResult result = mockMvc.perform(get("/api/foods/food/name/{name}", foodName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        assertThat(foodName).isNotNull();
        assertThat(foodName).isNotBlank();
        assertThat(foodName).isEqualTo(foodModel.getName());

        String responseContent = result.getResponse().getContentAsString();
        FoodModel actualFood = objectMapper.readValue(responseContent, FoodModel.class);

        assertThat(actualFood)
                .usingRecursiveComparison()
                .isEqualTo(foodModel);
    }

    @Test
    @DisplayName("Given valid id when getFoodById then return food model")
    public void givenValidId_whenGetFoodById_thenReturnFoodModel() throws Exception {
        //Given
        Long id = 1L; // valid id

        // Mock the foodService to return a food
        Food food = new Food();
        food.setName("Sajt");
        food.setDescription("");
        food.setPrice(2000D);
        food.setFoodPic("/images/defaultFood.png");

        when(foodService.getFoodById(any(Long.class))).thenReturn(food);

        // When
        MvcResult result = mockMvc.perform(get("/api/foods/food/id/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        assertThat(id).isNotNull();
        assertThat(id).isNotNegative();
        assertThat(id).isNotZero();

        String responseContent = result.getResponse().getContentAsString();
        Food actualFood = objectMapper.readValue(responseContent, Food.class);

        assertThat(actualFood)
                .usingRecursiveComparison()
                .ignoringFields("id", "registrationDate", "createdAt", "orders")
                .isEqualTo(food);
    }
}
