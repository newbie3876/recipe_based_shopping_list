package lt.techin.service;

import jakarta.transaction.Transactional;
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
    return recipeIngredientRepository.findById(id).orElseThrow(() -> new RuntimeException("Recepto ingredientas nerastas su ID: " + id));
  }

  public RecipeIngredient saveRecipe(RecipeIngredient ingredient) {
    return recipeIngredientRepository.save(ingredient);
  }

  @Transactional
  public void deleteRecipeById(long id) {
    if (!recipeIngredientRepository.existsById(id)) {
      throw new IllegalArgumentException("Recepto ingredientas nerastas su ID: " + id);
    }
    recipeIngredientRepository.deleteById(id);
  }
}
