package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.dto.recipe.RecipeRequestDTO;
import lt.techin.dto.recipe.RecipeResponseDTO;
import lt.techin.exceptions.UserNotFoundException;
import lt.techin.model.Recipe;
import lt.techin.model.User;
import lt.techin.repository.UserRepository;
import lt.techin.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lt.techin.security.SecurityUtils.getCurrentAuthenticatedUsername;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:5173",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true")
public class RecipeController {
  private final RecipeService recipeService;
  private final UserRepository userRepository;

  @Autowired
  public RecipeController(RecipeService recipeService, UserRepository userRepository) {
    this.recipeService = recipeService;
    this.userRepository = userRepository;
  }

  @GetMapping
  public ResponseEntity<List<RecipeResponseDTO>> showAllRecipes() {
    List<RecipeResponseDTO> userRecipes = recipeService.getAllUserRecipes();
    return ResponseEntity.ok(userRecipes);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id) {
    recipeService.deleteRecipeById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<RecipeResponseDTO> updateRecipeById(@PathVariable Long id, @Valid @RequestBody RecipeRequestDTO dto) {
    String username = getCurrentAuthenticatedUsername();
    User user = getCurrentUser();

    Recipe updated = recipeService.updateRecipeFromDTO(id, dto, user);
    return ResponseEntity.ok(recipeService.convertToResponseDTO(updated));
  }

  @PostMapping
  public ResponseEntity<RecipeResponseDTO> addRecipe(@Valid @RequestBody RecipeRequestDTO dto) {
    String username = getCurrentAuthenticatedUsername();
    User user = getCurrentUser();

    Recipe saved = recipeService.saveRecipeFromDTO(dto, user);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(recipeService.convertToResponseDTO(saved));
  }

  private User getCurrentUser() {
    String username = getCurrentAuthenticatedUsername();
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));
  }
}

