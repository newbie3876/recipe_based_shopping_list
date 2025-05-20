package lt.techin.service;

import jakarta.transaction.Transactional;
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
    return recipeCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Recepto kategorija nerasta su ID: " + id));
  }

  public RecipeCategory saveRecipeCategory(RecipeCategory category) {
    return recipeCategoryRepository.save(category);
  }

  @Transactional
  public void deleteRecipeCategoryById(long id) {
    if (!recipeCategoryRepository.existsById(id)) {
      throw new IllegalArgumentException("Recepto kategorija nerasta su ID: " + id);
    }
    recipeCategoryRepository.deleteById(id);
  }
}
