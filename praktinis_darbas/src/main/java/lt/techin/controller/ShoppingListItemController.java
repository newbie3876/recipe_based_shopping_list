package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.dto.shoppinglist.ShoppingListItemMapper;
import lt.techin.dto.shoppinglist.ShoppingListItemRequestDTO;
import lt.techin.dto.shoppinglist.ShoppingListMapper;
import lt.techin.model.*;
import lt.techin.repository.RecipeIngredientRepository;
import lt.techin.repository.UnitRepository;
import lt.techin.service.ShoppingListItemService;
import lt.techin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/shopping-lists/items")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingListItemController {

  private final ShoppingListItemService shoppingListItemService;
  private final UserService userService;
  private final RecipeIngredientRepository recipeIngredientRepository;
  private final UnitRepository unitRepository;
  private final ShoppingListMapper shoppingListMapper;

  public ShoppingListItemController(ShoppingListItemService shoppingListItemService,
                                    UserService userService,
                                    RecipeIngredientRepository recipeIngredientRepository,
                                    UnitRepository unitRepository,
                                    ShoppingListMapper shoppingListMapper) {
    this.shoppingListItemService = shoppingListItemService;
    this.userService = userService;
    this.recipeIngredientRepository = recipeIngredientRepository;
    this.unitRepository = unitRepository;
    this.shoppingListMapper = shoppingListMapper;
  }

  @Autowired
  @PostMapping("/items")
  public ResponseEntity<Object> addItemToShoppingList(@Valid @RequestBody ShoppingListItemRequestDTO shoppingListItemRequestDTO) {
    ShoppingListItem addedItem = this.shoppingListItemService.addItem(ShoppingListItemMapper.toShoppingListItem(shoppingListItemRequestDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(addedItem.getId())
                            .toUri())
            .body(ShoppingListItemMapper.toDTO(addedItem));
  }

  @PutMapping("/items/{id}")
  public ResponseEntity<Object> updateItemQuantity(@PathVariable long id, @Valid @RequestBody ShoppingListItemRequestDTO shoppingListItemRequestDTO) {
    Optional<ShoppingListItem> itemFromList = this.shoppingListItemService.findItemById(id);

//    if (itemFromList.isPresent()) {
//      ShoppingListItem updatedItem = itemFromList.get();
//
//      updatedItem.setQuantity(ShoppingListItemRequestDTO.quantity());
//
//      return ResponseEntity.ok(ShoppingListItemMapper.toDTO(i));
//    }

    ShoppingListItem addedItem = this.shoppingListItemService.addItem(ShoppingListItemMapper.toShoppingListItem(shoppingListItemRequestDTO));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .replacePath("/api/movies/{id}")
                            .buildAndExpand(addedItem.getId())
                            .toUri())
            .body(ShoppingListItemMapper.toDTO(addedItem));
  }

  @DeleteMapping("/items/{id}")
  public ResponseEntity<Void> removeItemFromShoppingList(@PathVariable long id) {
    Optional<ShoppingListItem> item = this.shoppingListItemService.findItemById(id);

    if(item.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    this.shoppingListItemService.removeItemById(id);
    return ResponseEntity.noContent().build();
  }
}
