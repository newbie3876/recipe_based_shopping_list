package lt.techin.service;

import lt.techin.model.RecipeCategory;
import lt.techin.repository.RecipeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeCategoryService {
  private final RecipeCategoryRepository recipeCategoryRepository;

  @Autowired
  public RecipeCategoryService(RecipeCategoryRepository recipeCategoryRepository) {
    this.recipeCategoryRepository = recipeCategoryRepository;
  }

  public List<RecipeCategory> getAllRecipeCategories() {
    return recipeCategoryRepository.findAll();
  }

  public RecipeCategory getRecipeCategoryById(long id) {
    return recipeCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe category not found with id: " + id));
  }

  public RecipeCategory saveRecipeCategory(RecipeCategory category) {
    return recipeCategoryRepository.save(category);
  }

  public void deleteRecipeCategoryById(long id) {
    if (!recipeCategoryRepository.existsById(id)) {
      throw new IllegalArgumentException("Recipe category not found with id: " + id);
    }
    recipeCategoryRepository.deleteById(id);
  }
}
