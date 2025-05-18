package lt.techin.service;

import lt.techin.dto.ingredient.IngredientCategoryResponseDTO;
import lt.techin.dto.ingredient.IngredientRequestDTO;
import lt.techin.dto.ingredient.IngredientResponseDTO;
import lt.techin.model.Ingredient;
import lt.techin.model.IngredientCategory;
import lt.techin.model.User;
import lt.techin.repository.IngredientCategoryRepository;
import lt.techin.repository.IngredientRepository;
import lt.techin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

  private final IngredientRepository ingredientRepository;
  private final IngredientCategoryRepository ingredientCategoryRepository;
  private final UserRepository userRepository;

  @Autowired
  public IngredientService(IngredientRepository ingredientRepository, IngredientCategoryRepository ingredientCategoryRepository, UserRepository userRepository) {
    this.ingredientRepository = ingredientRepository;
    this.ingredientCategoryRepository = ingredientCategoryRepository;
    this.userRepository = userRepository;
  }

  public Optional<Ingredient> findIngredientById(Long id) {
    return this.ingredientRepository.findById(id);
  }

  public boolean existsIngredientByName(String name) {
    return this.ingredientRepository.existsByName(name);
  }

  public void deleteIngredientById(Long id) {
    this.ingredientRepository.deleteById(id);
  }

  public List<Ingredient> findAllIngredients() {
    return this.ingredientRepository.findAll();
  }

  public IngredientResponseDTO createIngredient(IngredientRequestDTO requestDTO) {
    // 1. ️Surandame vartotoją pagal userId
    User user = userRepository.findById(requestDTO.userId())
            .orElseThrow(() -> new RuntimeException("User with ID " + requestDTO.userId() + " not found"));

    // 2. ️Surandame ingredientų kategoriją
    IngredientCategory ingredientCategory = ingredientCategoryRepository.findById(requestDTO.ingredientCategoryId())
            .orElseThrow(() -> new RuntimeException("Ingredient category not found"));

    // 3. Konvertuojame DTO į Ingredient objektą
    Ingredient ingredient = new Ingredient();
    ingredient.setName(requestDTO.ingredientName());
    ingredient.setIngredientCategory(ingredientCategory);
    ingredient.setUser(user);

    // 4. Išsaugome ingredientą į duomenų bazę
    Ingredient savedIngredient = ingredientRepository.save(ingredient);

    // 5. Atsakymą grąžiname
    return new IngredientResponseDTO(
            user.getId(),
            savedIngredient.getId(),
            savedIngredient.getName(),
            new IngredientCategoryResponseDTO(ingredientCategory.getId(), ingredientCategory.getName())
    );
  }
  
}

