package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.dto.recipe.RecipeRequestDTO;
import lt.techin.dto.recipe.RecipeResponseDTO;
import lt.techin.model.Recipe;
import lt.techin.model.User;
import lt.techin.repository.UserRepository;
import lt.techin.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:5173",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true")
public class RecipeController {
  private final RecipeService recipeService;
  private final UserRepository userRepository;

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
    try {
      recipeService.deleteRecipeById(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<RecipeResponseDTO> updateRecipeById(@PathVariable Long id, @RequestBody @Valid RecipeRequestDTO dto) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Vartotojas nerastas."));

    Recipe updated = recipeService.updateRecipeFromDTO(id, dto, user);
    return ResponseEntity.ok(recipeService.convertToResponseDTO(updated));
  }

  @PostMapping
  public ResponseEntity<RecipeResponseDTO> addRecipe(@RequestBody @Valid RecipeRequestDTO dto) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Vartotojas nerastas."));

    Recipe saved = recipeService.saveRecipeFromDTO(dto, user);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(recipeService.convertToResponseDTO(saved));
  }
}
