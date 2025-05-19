package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.dto.ingredient.IngredientMapper;
import lt.techin.dto.ingredient.IngredientRequestDTO;
import lt.techin.dto.ingredient.IngredientResponseDTO;
import lt.techin.model.Ingredient;
import lt.techin.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class IngredientController {

  private final IngredientService ingredientService;

  @Autowired
  public IngredientController(IngredientService ingredientService) {
    this.ingredientService = ingredientService;
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

    if (ingredientService.existsIngredientByName(ingredientRequestDTO.ingredientName())) {

      Map<String, String> response = new HashMap<>();
      response.put("message", "Ingredient with such name already exists!");

      return ResponseEntity.badRequest().body(response);
    }

    IngredientResponseDTO createdIngredient = ingredientService.createIngredient(ingredientRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdIngredient);
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

