package lt.techin.controller;

import lt.techin.dto.shoppinglist.ShoppingListMapper;
import lt.techin.model.*;
import lt.techin.repository.RecipeIngredientRepository;
import lt.techin.repository.UnitRepository;
import lt.techin.service.ShoppingListService;
import lt.techin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/shopping-lists/items")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingListItemController {

  private final ShoppingListService shoppingListService;
  private final UserService userService;
  private final RecipeIngredientRepository recipeIngredientRepository;
  private final UnitRepository unitRepository;
  private final ShoppingListMapper shoppingListMapper;

  @Autowired
  public ShoppingListItemController(ShoppingListService shoppingListService,
                                    UserService userService,
                                    RecipeIngredientRepository recipeIngredientRepository,
                                    UnitRepository unitRepository,
                                    ShoppingListMapper shoppingListMapper) {
    this.shoppingListService = shoppingListService;
    this.userService = userService;
    this.recipeIngredientRepository = recipeIngredientRepository;
    this.unitRepository = unitRepository;
    this.shoppingListMapper = shoppingListMapper;
  }

  @PostMapping("/")
  public ResponseEntity<Object> addItemToShoppingList(@PathVariable Long listId,
                                                      @RequestBody ShoppingListItemDTO itemDto,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
    User user = userService.findUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    ShoppingList shoppingList = shoppingListService.getShoppingListById(listId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopping list not found"));

    if (!shoppingList.getUser().getId().equals(user.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this shopping list");
    }

    Ingredient ingredient = recipeIngredientRepository.findById(itemDto.getIngredientId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found"));

    Unit unit = unitRepository.findById(itemDto.getUnitId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unit not found"));

    ShoppingListItem savedItem = shoppingListService.addItemToShoppingList(
            shoppingList, ingredient, itemDto.getQuantity(), unit);

    return ResponseEntity.status(HttpStatus.CREATED).body(shoppingListMapper.toDTO(savedItem));
  }

  @PutMapping("/")
  public ResponseEntity<ShoppingListItemDTO> updateItemQuantity(@PathVariable Long itemId,
                                                                @RequestParam int quantity,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
    ShoppingListItem item = shoppingListService.getShoppingListItemsById(itemId)
            .stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

    User user = userService.findUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    if (!item.getShoppingList().getUser().getId().equals(user.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this shopping list item");
    }

    shoppingListService.updateItemQuantity(itemId, quantity);

    ShoppingListItem updatedItem = shoppingListService.getShoppingListItemsById(item.getShoppingList().getId())
            .stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found after update"));

    return ResponseEntity.ok(shoppingListMapper.toDTO(updatedItem));
  }

  @DeleteMapping("/")
  public ResponseEntity<Void> removeItemFromShoppingList(@PathVariable Long itemId,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
    ShoppingListItem item = shoppingListService.getShoppingListItemsById(itemId)
            .stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

    User user = userService.findUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    if (!item.getShoppingList().getUser().getId().equals(user.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this shopping list item");
    }

    shoppingListService.removeItemFromShoppingList(itemId);
    return ResponseEntity.noContent().build();
  }
}
