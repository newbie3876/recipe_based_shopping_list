package lt.techin.service;

import lt.techin.model.Ingredient;
import lt.techin.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientService {

  private final IngredientRepository ingredientRepository;

  @Autowired
  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  public Optional<Ingredient> findIngredientById(Long id) {
    return this.ingredientRepository.findById(id);
  }

  public boolean existsIngredientByName(String name) {
    return this.ingredientRepository.existsByName(name);
  }

  public Ingredient saveIngredient(Ingredient ingredient) {
    return this.ingredientRepository.save(ingredient);
  }

  public void deleteIngredientById(Long id) {
    this.ingredientRepository.deleteById(id);
  }
}

