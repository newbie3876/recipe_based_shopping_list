package lt.techin.service;

import jakarta.transaction.Transactional;
import lt.techin.dto.ingredientCategory.IngredientCategoryResponseDTO;
import lt.techin.dto.recipe.RecipeRequestDTO;
import lt.techin.dto.recipe.RecipeResponseDTO;
import lt.techin.dto.recipeIngredient.RecipeIngredientRequestDTO;
import lt.techin.dto.recipeIngredient.RecipeIngredientResponseDTO;
import lt.techin.exceptions.RecipeNotFoundException;
import lt.techin.exceptions.UserNotFoundException;
import lt.techin.model.Ingredient;
import lt.techin.model.Recipe;
import lt.techin.model.RecipeIngredient;
import lt.techin.model.User;
import lt.techin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static lt.techin.security.SecurityUtils.getCurrentAuthenticatedUsername;

@Service
public class RecipeService {
  private final RecipeRepository recipeRepository;
  private final UserRepository userRepository;
  private final RecipeCategoryRepository recipeCategoryRepository;
  private final UnitRepository unitRepository;
  private final IngredientRepository ingredientRepository;
  private final RecipeIngredientRepository recipeIngredientRepository;

  @Autowired
  public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, RecipeCategoryRepository recipeCategoryRepository, UnitRepository unitRepository, IngredientRepository ingredientRepository, RecipeIngredientRepository recipeIngredientRepository) {
    this.recipeRepository = recipeRepository;
    this.userRepository = userRepository;
    this.recipeCategoryRepository = recipeCategoryRepository;
    this.unitRepository = unitRepository;
    this.ingredientRepository = ingredientRepository;
    this.recipeIngredientRepository = recipeIngredientRepository;
  }

  public List<RecipeResponseDTO> getAllUserRecipes() {
    User user = getCurrentUser();
    return recipeRepository.findAllByUser(user).stream()
            .map(this::convertToResponseDTO)
            .toList();
  }

  public Recipe getRecipeById(Long id) {
    return recipeRepository.findById(id)
            .orElseThrow(() -> new RecipeNotFoundException(id));
  }

  @Transactional
  public void deleteRecipeById(long id) {
    Recipe recipe = getRecipeById(id);
    String currentUsername = getCurrentAuthenticatedUsername();

    if (!recipe.getUser().getUsername().equals(currentUsername)) {
      throw new AccessDeniedException("Negalima ištrinti kito vartotojo recepto.");
    }

    recipeRepository.delete(recipe);
  }

  @Transactional
  public Recipe saveRecipeFromDTO(RecipeRequestDTO dto, User user) {
    Recipe recipe = new Recipe();
    recipe.setUser(user);
    applyRecipeDTOtoEntity(recipe, dto);
    return recipeRepository.save(recipe);
  }

  @Transactional
  public Recipe updateRecipeFromDTO(Long id, RecipeRequestDTO dto, User user) {
    Recipe recipe = getRecipeById(id);

    if (!recipe.getUser().equals(user)) {
      throw new AccessDeniedException("Neturite teisės redaguoti šio recepto.");
    }

    applyRecipeDTOtoEntity(recipe, dto);

    // Pirmiausiai išsaugom receptą, kad gautų ID (jei naujas)
    Recipe savedRecipe = recipeRepository.save(recipe);

    // Tada išsaugom RecipeIngredient atskirai, jei jie nauji
    for (RecipeIngredient ri : savedRecipe.getRecipeIngredients()) {
      if (ri.getId() == null) {
        ri.setRecipe(savedRecipe);
        recipeIngredientRepository.save(ri);
      }
    }

    return savedRecipe;
  }

  public RecipeResponseDTO convertToResponseDTO(Recipe recipe) {
    List<RecipeIngredientResponseDTO> ingredientDTOs = recipe.getRecipeIngredients().stream()
            .map(this::convertRecipeIngredientToResponseDTO)
            .toList();

    return new RecipeResponseDTO(
            recipe.getId(),
            recipe.getName(),
            recipe.getDescription(),
            recipe.getPortions(),
            recipe.getLink(),
            recipe.getRecipeCategory() != null ? recipe.getRecipeCategory().getName() : null,
            ingredientDTOs
    );
  }

  private RecipeIngredientResponseDTO convertRecipeIngredientToResponseDTO(RecipeIngredient ri) {
    Ingredient ingredient = ri.getIngredient();
    IngredientCategoryResponseDTO categoryDTO = null;

    if (ingredient.getIngredientCategory() != null) {
      categoryDTO = new IngredientCategoryResponseDTO(
              ingredient.getIngredientCategory().getId(),
              ingredient.getIngredientCategory().getName()
      );
    }

    return new RecipeIngredientResponseDTO(
            ri.getId(),                 // RecipeIngredient ID
            ingredient.getName(),       // Ingredient pavadinimas
            categoryDTO,
            ri.getQuantity(),
            ri.getUnit() != null ? ri.getUnit().getName() : null
    );
  }

  private void applyRecipeDTOtoEntity(Recipe recipe, RecipeRequestDTO dto) {
    recipe.setName(dto.name());
    recipe.setDescription(dto.description());
    recipe.setLink(dto.link());
    recipe.setPortions(dto.portions());

    if (dto.categoryId() != null) {
      var category = recipeCategoryRepository.findById(dto.categoryId())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategorija nerasta"));
      recipe.setRecipeCategory(category);
    } else {
      recipe.setRecipeCategory(null);
    }

    // Pašalinam recepto ingredientus, kurie nėra DTO sąraše
    recipe.getRecipeIngredients().removeIf(existing ->
            dto.ingredients().stream()
                    .noneMatch(dtoIng -> dtoIng.id() != null && dtoIng.id().equals(existing.getId()))
    );

    for (var ingredientDTO : dto.ingredients()) {
      RecipeIngredient ri;
      if (ingredientDTO.id() != null) {
        // Update existing
        ri = recipe.getRecipeIngredients().stream()
                .filter(i -> i.getId().equals(ingredientDTO.id()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredientas nerastas"));
      } else {
        ri = new RecipeIngredient();
        ri.setRecipe(recipe);
        recipe.getRecipeIngredients().add(ri);
      }
      updateRecipeIngredientFromDTO(ri, ingredientDTO);
    }
  }

  private void updateRecipeIngredientFromDTO(RecipeIngredient ri, RecipeIngredientRequestDTO dto) {
    Ingredient ingredient;

    if (dto.ingredientId() != null) {
      ingredient = ingredientRepository.findById(dto.ingredientId())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredientas nerastas"));
    } else if (dto.ingredientName() != null && !dto.ingredientName().isBlank()) {
      String trimmedName = dto.ingredientName().trim();
      if (trimmedName.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ingrediento pavadinimas negali būti tuščias arba sudarytas tik iš tarpų");
      }

      ingredient = new Ingredient();
      ingredient.setName(trimmedName);
      ingredientRepository.save(ingredient);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IngredientId arba ingredientName būtinas");
    }

    ri.setIngredient(ingredient);
    ri.setQuantity(dto.quantity());

    if (dto.unitId() != null) {
      var unit = unitRepository.findById(dto.unitId())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vienetas nerastas"));
      ri.setUnit(unit);
    } else {
      ri.setUnit(null);
    }
  }

  public List<RecipeIngredient> getRecipeIngredientsByRecipeId(Long recipeId) {
    Recipe recipe = recipeRepository.findById(recipeId)
            .orElseThrow(() -> new RecipeNotFoundException(recipeId));
    return recipe.getRecipeIngredients();
  }

  private User getCurrentUser() {
    String username = getCurrentAuthenticatedUsername();
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));
  }
}

