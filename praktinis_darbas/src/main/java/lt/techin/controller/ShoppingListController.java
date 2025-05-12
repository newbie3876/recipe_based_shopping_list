package lt.techin.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lt.techin.dto.shoppinglist.ShoppingListMapper;
import lt.techin.dto.shoppinglist.ShoppingListRequestDTO;
import lt.techin.dto.shoppinglist.ShoppingListResponseDTO;
import lt.techin.model.ShoppingList;
import lt.techin.service.RecipeIngredientService;
import lt.techin.service.ShoppingListService;
import lt.techin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingListController {

  private final ShoppingListService shoppingListService;
  private final UserService userService;
  private final ShoppingListMapper shoppingListMapper;
  private final RecipeIngredientService recipeIngredientService;

  @Autowired
  public ShoppingListController(ShoppingListService shoppingListService,
                                UserService userService,
                                ShoppingListMapper shoppingListMapper,
                                RecipeIngredientService recipeIngredientService) {
    this.shoppingListService = shoppingListService;
    this.userService = userService;
    this.shoppingListMapper = shoppingListMapper;
    this.recipeIngredientService = recipeIngredientService;
  }

  @PostMapping("/shopping-lists")
  public ResponseEntity<Object> createShoppingList(@Valid @RequestBody ShoppingListRequestDTO dto) {

    ShoppingList createdList = this.shoppingListService.createShoppingList(ShoppingListMapper.toShoppingList(dto));

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdList.getId())
                            .toUri())
            .body(shoppingListMapper.toDTO(createdList));
  }

  @GetMapping("/shoppinglists")
  public ResponseEntity<List<ShoppingList>> showAllShoppingLists(@RequestBody @Valid ShoppingList shoppingList) {
    List<ShoppingList> allLists = shoppingListService.getAllShoppingLists();
    return ResponseEntity.ok(allLists);
  }

  @GetMapping("/shoppinglists/{id}")
  public ResponseEntity<ShoppingListResponseDTO> getShoppingList(@Valid @PathVariable @Min(1) long id) {
    Optional<ShoppingList> list = this.shoppingListService.getShoppingListById(id);
    if (list.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(ShoppingListMapper.toDTO(list.get()));
  }

  @DeleteMapping("/shoppinglists/{id}")
  public ResponseEntity<Void> deleteByShoppingList(@PathVariable long id) {
    Optional<ShoppingList> list = this.shoppingListService.getShoppingListById(id);

    if(list.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    this.shoppingListService.deleteShoppingListById(id);
    return ResponseEntity.noContent().build();
  }
}