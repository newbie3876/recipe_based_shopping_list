package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.dto.ingredient.IngredientMapper;
import lt.techin.dto.ingredient.IngredientRequestDTO;
import lt.techin.dto.ingredient.IngredientResponseDTO;
import lt.techin.service.IngredientCategoryService;
import lt.techin.service.IngredientService;
import lt.techin.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
  private final IngredientService ingredientService;
  private final IngredientCategoryService ingredientCategoryService;
  private final UnitService unitService;

  @Autowired
  public IngredientController(IngredientService ingredientService,
                              IngredientCategoryService ingredientCategoryService,
                              UnitService unitService) {
    this.ingredientService = ingredientService;
    this.ingredientCategoryService = ingredientCategoryService;
    this.unitService = unitService;
  }

  @GetMapping
  public ResponseEntity<List<IngredientResponseDTO>> getAll() {
    List<IngredientResponseDTO> ingredients = ingredientService.getAllIngredientDTO();
    return ResponseEntity.ok(ingredients);
  }

  @GetMapping("/{id}")
  public ResponseEntity<IngredientResponseDTO> getById(@PathVariable Long id) {
    IngredientResponseDTO dto = ingredientService.getIngredientDTOById(id);
    return ResponseEntity.ok(dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    ingredientService.deleteIngredientById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<IngredientResponseDTO> create(@Valid @RequestBody IngredientRequestDTO dto) {
    if (ingredientService.existsIngredientByName(dto.name())) {
      return ResponseEntity.badRequest().build();
    }

    var category = ingredientCategoryService.getCategoryById(dto.ingredientCategoryId());
    if (category == null) {
      return ResponseEntity.badRequest().build();
    }

    var unit = unitService.getUnitById(dto.unitId());
    if (unit == null) {
      return ResponseEntity.badRequest().build();
    }

    var ingredient = IngredientMapper.toIngredient(dto, category, unit);
    var savedDTO = ingredientService.saveIngredient(ingredient);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedDTO.id())
            .toUri();

    return ResponseEntity.created(location).body(savedDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<IngredientResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody IngredientRequestDTO dto) {
    var category = ingredientCategoryService.getCategoryById(dto.ingredientCategoryId());
    if (category == null) {
      return ResponseEntity.badRequest().build();
    }

    var unit = unitService.getUnitById(dto.unitId());
    if (unit == null) {
      return ResponseEntity.badRequest().build();
    }

    var existing = ingredientService.getIngredientById(id);
    existing.setName(dto.name());
    existing.setIngredientCategory(category);
    existing.setUnit(unit);

    IngredientResponseDTO updated = ingredientService.saveIngredient(existing);
    return ResponseEntity.ok(updated);
  }
}

