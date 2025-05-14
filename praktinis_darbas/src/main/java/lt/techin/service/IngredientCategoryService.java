package lt.techin.service;

import lt.techin.model.IngredientCategory;
import lt.techin.repository.IngredientCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientCategoryService {

  private final IngredientCategoryRepository ingredientCategoryRepository;

  public IngredientCategoryService(IngredientCategoryRepository ingredientCategoryRepository) {
    this.ingredientCategoryRepository = ingredientCategoryRepository;
  }

  public Optional<IngredientCategory> getCategoryById(Long id) {
    return this.ingredientCategoryRepository.findById(id);
  }

}
