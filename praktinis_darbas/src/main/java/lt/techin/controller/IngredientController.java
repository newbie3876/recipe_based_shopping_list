package lt.techin.controller;

import lt.techin.model.Ingredient;
import lt.techin.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class IngredientController {

  private final IngredientService ingredientService;

  @Autowired
  public IngredientController(IngredientService ingredientService) {
    this.ingredientService = ingredientService;
  }

  @PostMapping("/ingredients")
  public ResponseEntity<Object> saveIngredient(@RequestBody Ingredient ingredient) {
    //
    //if (ingredientService.existsIngredientByName(ingredientRequestDTO.name()))

    if (ingredientService.existsIngredientByName(ingredient.getName())) {

      Map<String, String> response = new HashMap<>();
      response.put("message", "Ingredient with such name already exists!");

      return ResponseEntity.badRequest().body(response);
    }


    Ingredient savedIngredient = ingredientService.saveIngredient(ingredient);

    //Ingredient savedIngredient = ingredientService.saveIngredient(IngredientMapper.toIngredient(ingredientRequestDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedIngredient.getId())
                            .toUri())
            .body(savedIngredient);
    //body(IngredientMapper.toDTO(savedIngredient));
    //
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

