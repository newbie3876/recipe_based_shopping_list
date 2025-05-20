package lt.techin.service;

import jakarta.transaction.Transactional;
import lt.techin.dto.recipe.RecipeRequestDTO;
import lt.techin.dto.recipe.RecipeResponseDTO;
import lt.techin.model.Recipe;
import lt.techin.model.User;
import lt.techin.repository.RecipeCategoryRepository;
import lt.techin.repository.RecipeRepository;
import lt.techin.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecipeService {
  private final RecipeRepository recipeRepository;
  private final UserRepository userRepository;
  private final RecipeCategoryRepository recipeCategoryRepository;

  public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, RecipeCategoryRepository recipeCategoryRepository) {
    this.recipeRepository = recipeRepository;
    this.userRepository = userRepository;
    this.recipeCategoryRepository = recipeCategoryRepository;
  }

  public List<RecipeResponseDTO> getAllUserRecipes() {
    User user = getCurrentUser();
    return recipeRepository.findAllByUser(user).stream()
            .map(this::convertToResponseDTO)
            .toList();
  }

  @Transactional
  public void deleteRecipeById(long id) {
    Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receptas nerastas su ID: " + id));

    String currentUsername = getCurrentUsername();
    if (!recipe.getUser().getUsername().equals(currentUsername)) {
      throw new AccessDeniedException("Negalima ištrinti kitų žmonių receptų.");
    }
    recipeRepository.delete(recipe);
  }

  @Transactional
  public Recipe saveRecipeFromDTO(RecipeRequestDTO dto, User user) {
    Recipe recipe = new Recipe();
    recipe.setUser(user);
    applyRecipeDTOToEntity(recipe, dto);
    return recipeRepository.save(recipe);
  }

  @Transactional
  public Recipe updateRecipeFromDTO(Long id, RecipeRequestDTO dto, User user) {
    Recipe existingRecipe = recipeRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receptas nerastas su ID: " + id));

    if (!existingRecipe.getUser().equals(user)) {
      throw new AccessDeniedException("Neturite teisės redaguoti šio recepto.");
    }
    applyRecipeDTOToEntity(existingRecipe, dto);
    return recipeRepository.save(existingRecipe);
  }

  public RecipeResponseDTO convertToResponseDTO(Recipe recipe) {
    return new RecipeResponseDTO(
            recipe.getId(),
            recipe.getName(),
            recipe.getDescription(),
            recipe.getPortions(),
            recipe.getLink(),
            recipe.getRecipeCategory() != null ? recipe.getRecipeCategory().getName() : null
    );
  }

  private User getCurrentUser() {
    String username = getCurrentUsername();
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Toks vartotojas nerastas: " + username));
  }

  private void applyRecipeDTOToEntity(Recipe recipe, RecipeRequestDTO dto) {
    recipe.setName(dto.getName());
    recipe.setDescription(dto.getDescription());
    recipe.setLink(dto.getLink());
    recipe.setPortions(dto.getPortions());

    if (dto.getCategoryId() != null) {
      var category = recipeCategoryRepository.findById(dto.getCategoryId())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategorija nerasta su ID: " + dto.getCategoryId()));
      recipe.setRecipeCategory(category);
    } else {
      recipe.setRecipeCategory(null);
    }
  }

  private String getCurrentUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
