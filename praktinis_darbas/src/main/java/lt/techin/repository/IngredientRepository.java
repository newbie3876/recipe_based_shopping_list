package lt.techin.repository;

import lt.techin.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

  boolean existsByName(String name);
}

