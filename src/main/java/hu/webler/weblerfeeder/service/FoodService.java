package hu.webler.weblerfeeder.service;
import hu.webler.weblerfeeder.entity.Food;
import hu.webler.weblerfeeder.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public List<Food> findAllFoods() {
        return foodRepository.findAll();
    }

    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }

    public void deleteFood(Long id) {
        Food food = findFoodById(id);
        foodRepository.delete(food);
    }

    public Food findFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with ID: " + id));
    }

    public Food findFoodByName(String name) {
        return foodRepository.findFoodsByNameIgnoreCase(name);
    }
}
