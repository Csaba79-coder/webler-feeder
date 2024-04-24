package hu.webler.weblerfeeder.food.controller;

import hu.webler.weblerfeeder.food.model.FoodCreateAndUpdateModel;
import hu.webler.weblerfeeder.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class FoodWebController {

    private final FoodService foodService;

    @GetMapping("/foods")
    public String renderAllFoodsOnWeb(Model model) {
        model.addAttribute("foods", foodService.getAllFoods());
        return "foods";
    }

    @PostMapping("/foods/food/create")
    public String addFoodOnWeb(@RequestParam String name, String description, Double price, String foodPic) {
        FoodCreateAndUpdateModel newFood = new FoodCreateAndUpdateModel();
        newFood.setName(name);
        newFood.setDescription(description);
        newFood.setPrice(price);
        newFood.setFoodPic(foodPic);
        foodService.addFood(newFood);
        return "redirect:/foods";
    }

    @GetMapping("/update-food/{foodId}")
    public String updateFoodOnWeb(@PathVariable Long foodId, Model model) {
        model.addAttribute("foods", foodService.getFoodById(foodId));
        return "update-food";
    }

    @PostMapping("/foods/food/update")
    public String updateFoodOnWeb(@RequestParam Long foodId, String name, String description, Double price, String foodPic) {
        FoodCreateAndUpdateModel foodCreateAndUpdateModel = new FoodCreateAndUpdateModel();
        foodCreateAndUpdateModel.setName(name);
        foodCreateAndUpdateModel.setDescription(description);
        foodCreateAndUpdateModel.setPrice(price);
        foodCreateAndUpdateModel.setFoodPic(foodPic);
        foodService.updateFood(foodId, foodCreateAndUpdateModel);
        return "redirect:/foods";
    }

    @PostMapping("/foods/food")
    public String deleteFoodOnWeb(@RequestParam Long id) {
        foodService.deleteFood(id);
        return "redirect:/foods";
    }
}
