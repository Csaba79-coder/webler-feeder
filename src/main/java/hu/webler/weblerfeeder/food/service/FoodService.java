package hu.webler.weblerfeeder.food.service;

import hu.webler.weblerfeeder.exception.FoodAlreadyExistsException;
import hu.webler.weblerfeeder.exception.InvalidInputException;
import hu.webler.weblerfeeder.food.entity.Food;
import hu.webler.weblerfeeder.food.model.FoodCreateAndUpdateModel;
import hu.webler.weblerfeeder.food.model.FoodModel;
import hu.webler.weblerfeeder.food.repository.FoodRepository;
import hu.webler.weblerfeeder.util.FoodMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static hu.webler.weblerfeeder.util.FoodMapper.mapFoodCreateAndUpdateModelToFoodEntity;
import static hu.webler.weblerfeeder.util.FoodMapper.mapFoodEntityToFoodModel;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {

    private final FoodRepository foodRepository;

    public List<FoodModel> getAllFoods() {
        return foodRepository.findAll()
                .stream()
                .map(FoodMapper::mapFoodEntityToFoodModel)
                .collect(Collectors.toList());
    }

    public FoodModel getFoodByName(String name) {
        return mapFoodEntityToFoodModel(
                foodRepository.findByName(name)
                        .orElseThrow(() -> {
                            String message = String.format("Food not found with name: %s", name);
                            log.info(message);
                            return new NoSuchElementException(message);
                        })
        );
    }

    private Optional<Food> findFoodByName(String name) {
        return foodRepository.findByName(name);
    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Food not found with ID: %s", id);
                    log.info(message);
                    return new NoSuchElementException(message);
                });
    }

    public FoodModel addFood(FoodCreateAndUpdateModel foodCreateAndUpdateModel) {
        Optional<Food> existingFoodWithThisName = findFoodByName(foodCreateAndUpdateModel.getName());
        if (isAllFieldsContainData(foodCreateAndUpdateModel) && existingFoodWithThisName.isEmpty()) {
            return mapFoodEntityToFoodModel(foodRepository
                    .save(mapFoodCreateAndUpdateModelToFoodEntity(foodCreateAndUpdateModel)));
        } else
            throw new FoodAlreadyExistsException("Food with this name already exists");
    }

    public FoodModel updateFood(Long id, FoodCreateAndUpdateModel foodCreateAndUpdateModel) {
        Food existingFood = getFoodById(id);
        if (isAllFieldsContainData(foodCreateAndUpdateModel)) {
            addNewDataToExistingFood(existingFood, foodCreateAndUpdateModel);
        }
        return mapFoodEntityToFoodModel(foodRepository.save(existingFood));
    }

    private void addNewDataToExistingFood(Food existingFood, FoodCreateAndUpdateModel foodCreateAndUpdateModel) {
        if (!foodCreateAndUpdateModel.getName().equals(existingFood.getName())) {
            existingFood.setName(foodCreateAndUpdateModel.getName());
        } else
            throw new FoodAlreadyExistsException("Please use another food name, food with this name already exists");
        existingFood.setDescription(foodCreateAndUpdateModel.getDescription());
        existingFood.setPrice(foodCreateAndUpdateModel.getPrice());
    }

    private boolean isAllFieldsContainData(FoodCreateAndUpdateModel foodCreateAndUpdateModel) {
        if (
                foodCreateAndUpdateModel.getName() != null &&
                        foodCreateAndUpdateModel.getDescription() != null &&
                        foodCreateAndUpdateModel.getPrice() != null
        ) {
            return true;
        } else throw new InvalidInputException("Please fill all fields");
    }

    public void deleteFood(Long id) {
        foodRepository.delete(getFoodById(id));
    }
}
