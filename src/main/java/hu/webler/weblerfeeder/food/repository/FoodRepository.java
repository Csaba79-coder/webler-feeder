package hu.webler.weblerfeeder.food.repository;

import hu.webler.weblerfeeder.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findFoodById(Long id);
}
