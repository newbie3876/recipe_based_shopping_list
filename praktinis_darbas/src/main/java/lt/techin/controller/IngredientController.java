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
import lt.techin.service.ShoppingListService;
import lt.techin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class IngredientController {
  private final IngredientService ingredientService;
  private final IngredientCategoryService ingredientCategoryService;
  private final UnitRepository unitRepository;
  private final ShoppingListItemRepository shoppingListItemRepository;
  private final UserService userService;
  private ShoppingList shoppingList;
  private ShoppingListService shoppingListService;


  @Autowired
  public IngredientController(
          IngredientService ingredientService,
          IngredientCategoryService ingredientCategoryService,
          UnitRepository unitRepository,
          ShoppingListItemRepository shoppingListItemRepository,
          UserService userService
  ) {
    this.ingredientService = ingredientService;
    this.ingredientCategoryService = ingredientCategoryService;
    this.unitRepository = unitRepository;
    this.shoppingListItemRepository = shoppingListItemRepository;
    this.userService = userService;
  }

  @GetMapping("/ingredients")
  public ResponseEntity<List<IngredientResponseDTO>> getIngredients() {

    List<Ingredient> ingredients = ingredientService.findIngredientForCurrentUser();

    return ResponseEntity.ok(IngredientMapper.toListDTO(ingredients));
  }

  @PostMapping("/ingredients")
  public ResponseEntity<?> createIngredient(@RequestBody IngredientRequestDTO dto) {
    String username = SecurityUtils.getCurrentAuthenticatedUsername();
    User user = userService.findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Vartotojas nerastas"));

    IngredientCategory category = ingredientCategoryService.getCategoryById(dto.ingredientCategoryId());

    Unit unit = unitRepository.findById(dto.unitId())
            .orElseThrow(() -> new RuntimeException("Vienetas nerastas."));

    // Create and save independent ingredient
    Ingredient ingredient = new Ingredient();
    ingredient.setName(dto.ingredientName());
    ingredient.setIngredientCategory(category);
    ingredient.setUser(user);
    ingredient = ingredientService.saveIngredient(ingredient);

    ShoppingListItem item = new ShoppingListItem();
    item.setIngredient(ingredient);
    item.setQuantity(dto.quantity());
    item.setUnit(unit);
    item.setShoppingList(null);

    IngredientResponseDTO response = IngredientMapper.toDTO(ingredient, item);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

