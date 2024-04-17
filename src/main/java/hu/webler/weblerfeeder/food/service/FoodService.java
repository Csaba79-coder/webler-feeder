package hu.webler.weblerfeeder.food.service;

import hu.webler.weblerfeeder.exception.EntityAlreadyExistsException;
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

    private boolean isFoodAlreadyExistsWithThisName(String foodName) {
        List<FoodModel> foods = getAllFoods();
        for (FoodModel food : foods) {
            if (food.getName().equals(foodName)) {
                return true;
            }
        }
        return false;
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
        } else {
            String name = foodCreateAndUpdateModel.getName();
            String message = String.format("Please use another food name, food with this name %s already exists", name);
            throw new EntityAlreadyExistsException(message);
        }
    }

    public FoodModel updateFood(Long id, FoodCreateAndUpdateModel foodCreateAndUpdateModel) {
        Food existingFood = getFoodById(id);
        if (isAllFieldsContainData(foodCreateAndUpdateModel)) {
            addNewDataToExistingFood(existingFood, foodCreateAndUpdateModel);
        }
        return mapFoodEntityToFoodModel(foodRepository.save(existingFood));
    }

    private void addNewDataToExistingFood(Food existingFood, FoodCreateAndUpdateModel foodCreateAndUpdateModel) {
        if (existingFood.getName().equals(foodCreateAndUpdateModel.getName()) ||
                !isFoodAlreadyExistsWithThisName(foodCreateAndUpdateModel.getName())) {
            existingFood.setName(foodCreateAndUpdateModel.getName());
        } else {
            String name = foodCreateAndUpdateModel.getName();
            String message = String.format("Please use another food name, food with this name %s already exists", name);
            throw new EntityAlreadyExistsException(message);
        }
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
