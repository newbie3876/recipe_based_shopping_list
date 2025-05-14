package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.model.Recipe;
import lt.techin.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RecipeController {
  private final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @GetMapping
  public ResponseEntity<List<Recipe>> showAllRecipes() {
    List<Recipe> allRecipes = recipeService.getAllRecipes();
    return ResponseEntity.ok(allRecipes);
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
  public ResponseEntity<Recipe> updateRecipeById(@PathVariable Long id, @RequestBody @Valid Recipe recipe) {
    try {
      Recipe existingRecipe = recipeService.getRecipeById(id);

      existingRecipe.setName(recipe.getName());
      existingRecipe.setDescription(recipe.getDescription());
      existingRecipe.setPortions(recipe.getPortions());

      Recipe updatedRecipe = recipeService.saveRecipe(existingRecipe);
      return ResponseEntity.ok(updatedRecipe);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<Recipe> addRecipe(@RequestBody @Valid Recipe recipe) {
    Recipe addition = recipeService.saveRecipe(recipe);
    return ResponseEntity.status(HttpStatus.CREATED).body(addition);
  }
}
