package lt.techin.repository;

import lt.techin.model.Recipe;
import lt.techin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
  List<Recipe> findAllByUser(User user);
}
