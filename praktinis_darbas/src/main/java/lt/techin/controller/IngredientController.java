package lt.techin.controller;

import lt.techin.dto.ingredient.IngredientMapper;
import lt.techin.dto.ingredient.IngredientRequestDTO;
import lt.techin.dto.ingredient.IngredientResponseDTO;
import lt.techin.model.*;
import lt.techin.repository.ShoppingListItemRepository;
import lt.techin.repository.UnitRepository;
import lt.techin.security.SecurityUtils;
import lt.techin.service.IngredientCategoryService;
import lt.techin.service.IngredientService;
import lt.techin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/api/ingredients")
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class IngredientController {
  private final IngredientService ingredientService;
  private final IngredientCategoryService ingredientCategoryService;
  //private final UnitService unitService;
  private final UnitRepository unitRepository;
  private final ShoppingListItemRepository shoppingListItemRepository;
  private final UserService userService;

  @Autowired
  //public IngredientController(IngredientService ingredientService,
  //IngredientCategoryService ingredientCategoryService,
  //UnitService unitService) {
  public IngredientController(
          IngredientService ingredientService,
          IngredientCategoryService ingredientCategoryService,
          UnitRepository unitRepository,
          ShoppingListItemRepository shoppingListItemRepository,
          UserService userService
  ) {
    this.ingredientService = ingredientService;
    this.ingredientCategoryService = ingredientCategoryService;
    //this.unitService = unitService;
    this.unitRepository = unitRepository;
    this.shoppingListItemRepository = shoppingListItemRepository;
    this.userService = userService;
  }

  //  @GetMapping
//  public ResponseEntity<List<IngredientResponseDTO>> getAll() {
//    List<IngredientResponseDTO> ingredients = ingredientService.getAllIngredientDTO();
//    return ResponseEntity.ok(ingredients);
  @GetMapping("/ingredients")
  public ResponseEntity<List<IngredientResponseDTO>> getIngredients() {

    List<Ingredient> ingredients = ingredientService.findIngredientForCurrentUser();

    return ResponseEntity.ok(IngredientMapper.toListDTO(ingredients));
  }

//  @GetMapping("/{id}")
//  public ResponseEntity<IngredientResponseDTO> getById(@PathVariable Long id) {
//    IngredientResponseDTO dto = ingredientService.getIngredientDTOById(id);
//    return ResponseEntity.ok(dto);
//  }

  //  @DeleteMapping("/{id}")
//  public ResponseEntity<Void> delete(@PathVariable Long id) {
//    ingredientService.deleteIngredientById(id);
//    return ResponseEntity.noContent().build();
//  }
  @PostMapping("/ingredients")
  public ResponseEntity<?> createIngredientWithUsage(@RequestBody IngredientRequestDTO dto) {
    String username = SecurityUtils.getCurrentAuthenticatedUsername(); //getCurrentUsername();
    User user = userService.findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));


    IngredientCategory category = ingredientCategoryService.getCategoryById(dto.ingredientCategoryId());
    //.orElseThrow(() -> new RuntimeException("Ingredient category not found."));


    Unit unit = unitRepository.findById(dto.unitId())
            .orElseThrow(() -> new RuntimeException("Unit not found."));


    // Ingredientas
    Ingredient ingredient = new Ingredient();
    ingredient.setName(dto.ingredientName());
    ingredient.setIngredientCategory(category);
    ingredient.setUser(user);
    ingredient = ingredientService.saveIngredient(ingredient);


    ShoppingListItem item = new ShoppingListItem();
    item.setIngredient(ingredient);
    item.setQuantity(dto.quantity());
    item.setUnit(unit);
    item.setShoppingList(null); // sąmoningai nenaudojam


    shoppingListItemRepository.save(item);

    // Atsakymas
    IngredientResponseDTO response = IngredientMapper.toDTO(ingredient, item);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
//
//    return ResponseEntity.created(location).body(savedDTO);
//  }

//  @PutMapping("/{id}")
//  public ResponseEntity<IngredientResponseDTO> update(@PathVariable Long id,
//                                                      @Valid @RequestBody IngredientRequestDTO dto) {
//    var category = ingredientCategoryService.getCategoryById(dto.ingredientCategoryId());
//    if (category == null) {
//      return ResponseEntity.badRequest().build();
//    }

//    var unit = unitService.getUnitById(dto.unitId());
//    if (unit == null) {
//      return ResponseEntity.badRequest().build();
//    }

//    var existing = ingredientService.getIngredientById(id);
//    existing.setName(dto.name());
//    existing.setIngredientCategory(category);
//    existing.setUnit(unit);
//
//    IngredientResponseDTO updated = ingredientService.saveIngredient(existing);
//    return ResponseEntity.ok(updated);
//  }

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

