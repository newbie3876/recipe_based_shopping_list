package lt.techin.repository;

import lt.techin.model.Ingredient;
import lt.techin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
  boolean existsByName(String name);

  List<Ingredient> findByUser(User user);
}

