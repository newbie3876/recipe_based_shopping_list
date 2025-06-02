package lt.techin.service;

import lt.techin.dto.recipeIngredient.RecipeIngredientMapper;
import lt.techin.dto.recipeIngredient.RecipeIngredientResponseDTO;
import lt.techin.model.*;
import lt.techin.repository.*;
import lt.techin.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RecipeIngredientService {
  private final RecipeIngredientRepository recipeIngredientRepository;
  private final RecipeRepository recipeRepository;
  private final IngredientRepository ingredientRepository;
  private final UnitRepository unitRepository;
  private final IngredientCategoryRepository ingredientCategoryRepository;
  private final UserRepository userRepository;

  @Autowired
  public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository, RecipeRepository recipeRepository, IngredientRepository ingredientRepository, UnitRepository unitRepository, IngredientCategoryRepository ingredientCategoryRepository, UserRepository userRepository) {
    this.recipeIngredientRepository = recipeIngredientRepository;
    this.recipeRepository = recipeRepository;
    this.ingredientRepository = ingredientRepository;
    this.unitRepository = unitRepository;
    this.ingredientCategoryRepository = ingredientCategoryRepository;
    this.userRepository = userRepository;
  }

  public RecipeIngredientResponseDTO addIngredientToRecipe(
          Long recipeId,
          Long ingredientId,
          String ingredientName,
          Double quantity,
          Long unitId,
          Long ingredientCategoryId
  ) {
    if (ingredientId == null && (ingredientName == null || ingredientName.isBlank())) {
      throw new IllegalArgumentException("Turi būti nurodytas ingredientId arba ingredientName.");
    }

    Recipe recipe = recipeRepository.findById(recipeId)
            .orElseThrow(() -> new RuntimeException("Receptas nerastas."));

    String username = SecurityUtils.getCurrentAuthenticatedUsername();
    User currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Vartotojas nerastas."));

    Ingredient ingredient;
    if (ingredientId != null) {
      ingredient = ingredientRepository.findById(ingredientId)
              .orElseThrow(() -> new RuntimeException("Ingredientas nerastas."));
    } else {
      if (ingredientCategoryId == null) {
        throw new IllegalArgumentException("Kuriant naują ingredientą, ingredientCategoryId yra privalomas.");
      }

      IngredientCategory category = ingredientCategoryRepository.findById(ingredientCategoryId)
              .orElseThrow(() -> new RuntimeException("Ingrediento kategorija nerasta."));

      ingredient = new Ingredient();
      ingredient.setName(ingredientName);
      ingredient.setIngredientCategory(category);
      ingredient.setUser(currentUser);
      ingredient = ingredientRepository.save(ingredient);
    }

    Unit unit = unitRepository.findById(unitId)
            .orElseThrow(() -> new RuntimeException("Matavimo vienetas nerastas."));

    RecipeIngredient recipeIngredient = new RecipeIngredient();
    recipeIngredient.setRecipe(recipe);
    recipeIngredient.setIngredient(ingredient);
    recipeIngredient.setQuantity(quantity);
    recipeIngredient.setUnit(unit);

    RecipeIngredient saved = recipeIngredientRepository.save(recipeIngredient);

    return RecipeIngredientMapper.toDTO(saved);
  }

  public List<RecipeIngredientResponseDTO> getIngredientsByRecipeDTO(Long recipeId) {
    List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(recipeId);
    return ingredients.stream()
            .map(RecipeIngredientMapper::toDTO)
            .toList();
  }
}

