package hu.webler.weblerfeeder.food.controller;

import hu.webler.weblerfeeder.food.model.FoodCreateAndUpdateModel;
import hu.webler.weblerfeeder.food.model.FoodModel;
import hu.webler.weblerfeeder.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hu.webler.weblerfeeder.util.FoodMapper.mapFoodEntityToFoodModel;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/foods")
    public ResponseEntity<List<FoodModel>> listAllFoods() {
        return ResponseEntity.status(200).body(foodService.getAllFoods());
    }

    @PostMapping("/foods")
    public ResponseEntity<FoodModel> addFood(@RequestBody FoodCreateAndUpdateModel foodCreateAndUpdateModel) {
        return ResponseEntity.status(200).body(foodService.addFood(foodCreateAndUpdateModel));
    }

    @GetMapping("/foods/food/name/{name}")
    public ResponseEntity<FoodModel> getFoodByName(@PathVariable String name) {
        return ResponseEntity.status(200).body(foodService.getFoodByName(name));
    }

    @GetMapping("/foods/food/id/{id}")
    public ResponseEntity<FoodModel> getFoodById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(mapFoodEntityToFoodModel(foodService.getFoodById(id)));
    }

    @PatchMapping("/foods/food/id/{id}")
    public ResponseEntity<FoodModel> updateFood(@PathVariable Long id, @RequestBody FoodCreateAndUpdateModel foodCreateAndUpdateModel) {
        return ResponseEntity.status(200).body(foodService.updateFood(id, foodCreateAndUpdateModel));
    }

    @DeleteMapping("/foods/food/id/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.status(204).build();
    }
}
