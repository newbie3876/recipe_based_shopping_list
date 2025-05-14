package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.dto.ingredient.IngredientMapper;
import lt.techin.dto.ingredient.IngredientRequestDTO;
import lt.techin.dto.ingredient.IngredientResponseDTO;
import lt.techin.model.Ingredient;
import lt.techin.model.IngredientCategory;
import lt.techin.service.IngredientCategoryService;
import lt.techin.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class IngredientController {

  private final IngredientService ingredientService;
  private final IngredientCategoryService ingredientCategoryService;

  @Autowired
  public IngredientController(IngredientService ingredientService, IngredientCategoryService ingredientCategoryService) {
    this.ingredientService = ingredientService;
    this.ingredientCategoryService = ingredientCategoryService;
  }

  @GetMapping("/ingredients")
  public ResponseEntity<List<IngredientResponseDTO>> getIngredients() {

    List<Ingredient> ingredients = ingredientService.findAllIngredients();

    return ResponseEntity.ok(IngredientMapper.toListDTO(ingredients));
  }

  @GetMapping("/ingredients/{id}")
  public ResponseEntity<IngredientResponseDTO> getIngredientById(@PathVariable Long id) {

    return ingredientService.findIngredientById(id).map(
                    ingredient -> ResponseEntity.ok(IngredientMapper.toDTO(ingredient)))
            .orElseGet(() -> ResponseEntity.notFound().build());

  }

  @PostMapping("/ingredients")
  public ResponseEntity<Object> createIngredient(@Valid @RequestBody IngredientRequestDTO ingredientRequestDTO) {

    if (ingredientService.existsIngredientByName(ingredientRequestDTO.name())) {

      Map<String, String> response = new HashMap<>();
      response.put("message", "Ingredient with such name already exists!");

      return ResponseEntity.badRequest().body(response);
    }

    // 2. Fetch the FULL category (ID + NAME) from the database
    IngredientCategory ingredientCategory = ingredientCategoryService.getCategoryById(ingredientRequestDTO.ingredientCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Ingredient category does not exits!"));

    // 3. Map DTO → Ingredient (now includes category name)
    Ingredient newIngredient = IngredientMapper.toIngredient(ingredientRequestDTO, ingredientCategory);

    // 4. Save the ingredient
    Ingredient savedIngredient = ingredientService.saveIngredient(newIngredient);

    // 5. Convert to Response DTO (includes category name)
    IngredientResponseDTO responseDTO = IngredientMapper.toDTO(savedIngredient);

    // 6. Return response with location header
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedIngredient.getId())
            .toUri();

    return ResponseEntity.created(location).body(responseDTO);
  }

  @DeleteMapping("/ingredients/{id}")
  public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {

    Optional<Ingredient> ingredient = ingredientService.findIngredientById(id);

    if (ingredient.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    ingredientService.deleteIngredientById(id);
    return ResponseEntity.noContent().build();
  }

}

