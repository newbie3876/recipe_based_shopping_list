package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.dto.recipeIngredient.RecipeIngredientRequestDTO;
import lt.techin.dto.recipeIngredient.RecipeIngredientResponseDTO;
import lt.techin.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes/{recipeId}/ingredients")
public class RecipeIngredientController {
  private final RecipeIngredientService recipeIngredientService;

  @Autowired
  public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
    this.recipeIngredientService = recipeIngredientService;
  }

  @PostMapping
  public ResponseEntity<RecipeIngredientResponseDTO> addIngredientToRecipe(
          @PathVariable Long recipeId,
          @Valid @RequestBody RecipeIngredientRequestDTO dto) {

    RecipeIngredientResponseDTO riDTO = recipeIngredientService.addIngredientToRecipe(
            recipeId,
            dto.ingredientId(),
            dto.ingredientName(),
            dto.quantity(),
            dto.unitId(),
            dto.ingredientCategoryId()
    );
    return ResponseEntity.ok(riDTO);
  }
  
  @GetMapping
  public ResponseEntity<List<RecipeIngredientResponseDTO>> getIngredientsByRecipe(@PathVariable Long recipeId) {
    List<RecipeIngredientResponseDTO> ingredientsDTO = recipeIngredientService.getIngredientsByRecipeDTO(recipeId);
    return ResponseEntity.ok(ingredientsDTO);
  }
}


