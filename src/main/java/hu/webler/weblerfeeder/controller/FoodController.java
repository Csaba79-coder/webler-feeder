package hu.webler.weblerfeeder.controller;

import hu.webler.weblerfeeder.entity.Food;
import hu.webler.weblerfeeder.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/foods")
    public List<Food> renderAllFoods() {
        return foodService.findAllFoods();
    }
    @PostMapping("/foods")
    public Food addNewFood(@RequestBody Food food) {
        return foodService.saveFood(food);
    }
    @DeleteMapping("/foods/{id}")
    public void deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
    }
}
