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

//  private final IngredientService ingredientService;
//  private final IngredientCategoryService ingredientCategoryService;
//  private final UserService userService;
//
//  @Autowired
//  public IngredientController(IngredientService ingredientService,
//                              IngredientCategoryService ingredientCategoryService,
//                              UserService userService) {
//    this.ingredientService = ingredientService;
//    this.ingredientCategoryService = ingredientCategoryService;
//    this.userService = userService;
//  }

  private final IngredientService ingredientService;
  private final IngredientCategoryService ingredientCategoryService;
  private final UnitRepository unitRepository;
  private final ShoppingListItemRepository shoppingListItemRepository;
  private final UserService userService;

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

//  @GetMapping("/ingredients/{id}")
//  public ResponseEntity<IngredientResponseDTO> getIngredientById(@PathVariable Long id) {
//
//    return ingredientService.findIngredientById(id).map(
//                    ingredient -> ResponseEntity.ok(IngredientMapper.toDTO(ingredient)))
//            .orElseGet(() -> ResponseEntity.notFound().build());
//
//  }

  @PostMapping("/ingredients")
  public ResponseEntity<?> createIngredientWithUsage(@RequestBody IngredientRequestDTO dto) {
    String username = SecurityUtils.getCurrentUsername();
    User user = userService.findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//    // Tikrinam ar ingredientas jau egzistuoja
//    Optional<Ingredient> existingIngredient = ingredientService
//            .findByNameAndUserId(dto.ingredientName(), user.getId());
//
//    if (existingIngredient.isPresent()) {
//      return ResponseEntity.badRequest().body("Ingredient already exists for this user.");
//    }

    IngredientCategory category = ingredientCategoryService.getCategoryById(dto.ingredientCategoryId())
            .orElseThrow(() -> new RuntimeException("Ingredient category not found."));

    Unit unit = unitRepository.findById(dto.unitId())
            .orElseThrow(() -> new RuntimeException("Unit not found."));

    // Ingredientas
    Ingredient ingredient = new Ingredient();
    ingredient.setName(dto.ingredientName());
    ingredient.setIngredientCategory(category);
    ingredient.setUser(user);
    ingredient = ingredientService.saveIngredient(ingredient);

    // ShoppingListItem (be ShoppingList)
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

//  @PostMapping("/ingredients")
//  public ResponseEntity<Object> createIngredient(@Valid @RequestBody IngredientRequestDTO ingredientRequestDTO) {
//
//    // paimam autentifikuotą user
//    String username = SecurityUtils.getCurrentUsername();
//    User user = userService.findUserByUsername(username)
//            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//    // Patikrink, ar vartotojas jau turi tokį ingredientą

  /// /    if (ingredientService.existsIngredientByUserId(user.getId(), ingredientRequestDTO.ingredientName())) {
  /// /      Map<String, String> response = new HashMap<>();
  /// /      response.put("message", "Ingredient with such name already exists for this user!");
  /// /      return ResponseEntity.badRequest().body(response);
  /// /    }
//
//    // 2. Fetch the FULL category (ID + NAME) from the database
//    IngredientCategory ingredientCategory = ingredientCategoryService.getCategoryById(ingredientRequestDTO.ingredientCategoryId())
//            .orElseThrow(() -> new IllegalArgumentException("Ingredient category does not exits!"));
//
//
//    // 3. Map DTO → Ingredient (now includes category name)
//    Ingredient newIngredient = IngredientMapper.toIngredient(ingredientRequestDTO, ingredientCategory, user);
//
//    // 4. Save the ingredient
//    Ingredient savedIngredient = ingredientService.saveIngredient(newIngredient);
//
//    // 5. Convert to Response DTO (includes category name)
//    IngredientResponseDTO responseDTO = IngredientMapper.toDTO(savedIngredient);
//
//    // 6. Return response with location header
//    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//            .path("/{id}")
//            .buildAndExpand(savedIngredient.getId())
//            .toUri();
//
//    return ResponseEntity.created(location).body(responseDTO);
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

