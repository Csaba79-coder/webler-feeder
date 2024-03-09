package hu.webler.weblerfeeder.repository;

import hu.webler.weblerfeeder.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findById (Long id);

    Food findFoodsByNameIgnoreCase(String name);

}
