package lt.techin.controller;

import lt.techin.dto.shoppingList.ShoppingListItemRequestDTO;
import lt.techin.dto.shoppingList.ShoppingListMapper;
import lt.techin.dto.shoppingList.ShoppingListRequestDTO;
import lt.techin.dto.shoppingList.ShoppingListResponseDTO;
import lt.techin.model.Ingredient;
import lt.techin.model.ShoppingList;
import lt.techin.model.ShoppingListItem;
import lt.techin.model.User;
import lt.techin.repository.IngredientRepository;
import lt.techin.repository.ShoppingListItemRepository;
import lt.techin.repository.UnitRepository;
import lt.techin.security.SecurityUtils;
import lt.techin.service.ShoppingListService;
import lt.techin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingListController {

  private final ShoppingListService shoppingListService;
  private final UserService userService;
  private final UnitRepository unitRepository;
  private final IngredientRepository ingredientRepository;
  private final ShoppingListItemRepository shoppingListItemRepository;

  @Autowired
  public ShoppingListController(ShoppingListService shoppingListService, UserService userService, UnitRepository unitRepository, IngredientRepository ingredientRepository, ShoppingListItemRepository shoppingListItemRepository) {
    this.shoppingListService = shoppingListService;
    this.userService = userService;
    this.unitRepository = unitRepository;
    this.ingredientRepository = ingredientRepository;
    this.shoppingListItemRepository = shoppingListItemRepository;
  }

//  @PostMapping("/shoppinglists")
//  public ResponseEntity<ShoppingListResponseDTO> createShoppingList(@RequestBody ShoppingListRequestDTO shoppingListRequestDTO) {
//    ShoppingListResponseDTO createdShoppingList = shoppingListService.createShoppingList(shoppingListRequestDTO);
//    return ResponseEntity.status(HttpStatus.CREATED).body(createdShoppingList);
//  }

//  @GetMapping("/shoppinglists")
//  public ResponseEntity<List<ShoppingListResponseDTO>> getAllShoppinglists() {
//    List<ShoppingList> allLists = shoppingListService.getAllShoppingLists();
//    return ResponseEntity.ok(ShoppingListMapper.toDTO(allLists));
//  }
//
//  @GetMapping("/shoppinglists/user")
//  public ResponseEntity<List<ShoppingListResponseDTO>> getShoppingListForUser() {
//    List<ShoppingListResponseDTO> shoppingLists = shoppingListService.getShoppingListsByUser();
//    return ResponseEntity.ok(shoppingLists);
//  }
//
//  @DeleteMapping("/shoppinglists/{id}")
//  public ResponseEntity<Void> deleteShoppingListById(@PathVariable Long id) {
//    shoppingListService.deleteShoppingListById(id);
//    return ResponseEntity.noContent().build();
//  }
//
//  @PostMapping("/shoppinglists/from-recipes")
//  public ResponseEntity<ShoppingListResponseDTO> createShoppingListFromRecipes(@RequestBody List<Long> recipeIds) {
//    ShoppingListResponseDTO shoppingList = shoppingListService.createShoppingListFromRecipes(recipeIds);
//    return ResponseEntity.status(HttpStatus.CREATED).body(shoppingList);
//  }

//  @GetMapping("/shoppinglists/{userId}")
//  public ResponseEntity<List<ShoppingListResponseDTO>> getShoppingLists(@PathVariable Long userId) {
//    List<ShoppingListResponseDTO> shoppingLists = shoppingListService.getShoppingListsByUserId(userId);
//    return ResponseEntity.ok(shoppingLists);
//  }

  @PostMapping("/shoppinglists")
  public ResponseEntity<?> createShoppingList(@RequestBody ShoppingListRequestDTO dto) {
    String username = SecurityUtils.getCurrentAuthenticatedUsername();

    User user = userService.findUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    ShoppingList shoppingList = new ShoppingList();
    shoppingList.setName(dto.name());
    shoppingList.setUser(user);
    shoppingList.setCreatedAt(LocalDateTime.now());

    List<ShoppingListItem> items = new ArrayList<>();

    for (ShoppingListItemRequestDTO itemDTO : dto.items()) {
      Ingredient ingredient = ingredientRepository.findById(itemDTO.ingredientId())
              .orElseThrow(() -> new RuntimeException("Ingredient not found"));

      ShoppingListItem latestItem = shoppingListItemRepository
              .findTopByIngredientIdOrderByIdDesc(ingredient.getId())
              .orElseThrow(() -> new RuntimeException("No previous ShoppingListItem found for ingredient"));

      if (latestItem.getQuantity() == null || latestItem.getUnit() == null) {
        throw new RuntimeException("Latest ShoppingListItem is missing quantity or unit");
      }

      ShoppingListItem item = new ShoppingListItem();
      item.setIngredient(ingredient);
      item.setQuantity(latestItem.getQuantity()); // Naudojame quantity iš paskutinio įrašo
      item.setUnit(latestItem.getUnit());         // Naudojame unit iš paskutinio įrašo
      item.setShoppingList(shoppingList);

      items.add(item);
    }

    shoppingList.setItems(items);

    ShoppingList savedList = shoppingListService.saveShoppingList(shoppingList);

    ShoppingListResponseDTO responseDTO = ShoppingListMapper.toDTO(savedList);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }
}

//  @GetMapping("/shoppinglists")
//  public ResponseEntity<List<ShoppingListResponseDTO>> getUserShoppingLists() {
//
//    List<ShoppingList> allLists = shoppingListService.findShoppingListsForCurrentUser();
//
//    return ResponseEntity.ok(ShoppingListMapper.toListDTO(allLists));
//  }
