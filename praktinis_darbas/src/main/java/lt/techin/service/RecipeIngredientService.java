package lt.techin.service;

import lt.techin.model.RecipeIngredient;
import lt.techin.repository.RecipeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientService {
  private final RecipeIngredientRepository recipeIngredientRepository;

  @Autowired
  public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository) {
    this.recipeIngredientRepository = recipeIngredientRepository;
  }

  public List<RecipeIngredient> getAllRecipeIngredients() {
    return recipeIngredientRepository.findAll();
  }

  public RecipeIngredient getRecipeIngredientById(long id) {
    return recipeIngredientRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe ingredient not found with id: " + id));
  }

  public RecipeIngredient saveRecipe(RecipeIngredient ingredient) {
    return recipeIngredientRepository.save(ingredient);
  }

  public void deleteRecipeById(long id) {
    if (!recipeIngredientRepository.existsById(id)) {
      throw new IllegalArgumentException("Recipe ingredient not found with id: " + id);
    }
    recipeIngredientRepository.deleteById(id);
  }
}
