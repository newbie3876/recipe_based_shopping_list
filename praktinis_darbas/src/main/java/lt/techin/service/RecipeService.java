package lt.techin.service;

import lt.techin.model.Recipe;
import lt.techin.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
  private final RecipeRepository recipeRepository;

  @Autowired
  public RecipeService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public List<Recipe> getAllRecipes() {
    return recipeRepository.findAll();
  }

  public Recipe getRecipeById(long id) {
    return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
  }

  public Recipe saveRecipe(Recipe recipe) {
    return recipeRepository.save(recipe);
  }

  public void deleteRecipeById(long id) {
    if (!recipeRepository.existsById(id)) {
      throw new IllegalArgumentException("Recipe not found with id: " + id);
    }
    recipeRepository.deleteById(id);
  }
}
