package lt.techin.service;

import lt.techin.model.IngredientCategory;
import lt.techin.repository.IngredientCategoryRepository;
import org.springframework.stereotype.Service;


@Service
public class IngredientCategoryService {
  private final IngredientCategoryRepository ingredientCategoryRepository;

  public IngredientCategoryService(IngredientCategoryRepository ingredientCategoryRepository) {
    this.ingredientCategoryRepository = ingredientCategoryRepository;
  }

  public IngredientCategory getCategoryById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Ingrediento kategorijos ID negali būti null.");
    }
    return ingredientCategoryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ingrediento kategorija nerasta su ID: " + id));
  }
}
