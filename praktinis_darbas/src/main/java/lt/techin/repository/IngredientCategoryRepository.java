package lt.techin.repository;

import lt.techin.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
  Optional<IngredientCategory> findByName(String name);
}
