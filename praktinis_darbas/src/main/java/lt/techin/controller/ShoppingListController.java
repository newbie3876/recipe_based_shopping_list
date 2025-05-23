package lt.techin.controller;

import lt.techin.dto.shoppingList.ShoppingListMapper;
import lt.techin.dto.shoppingList.ShoppingListRequestDTO;
import lt.techin.dto.shoppingList.ShoppingListResponseDTO;
import lt.techin.model.ShoppingList;
import lt.techin.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingListController {

  private final ShoppingListService shoppingListService;

  @Autowired
  public ShoppingListController(ShoppingListService shoppingListService) {
    this.shoppingListService = shoppingListService;
  }

  @PostMapping("/shoppinglists")
  public ResponseEntity<ShoppingListResponseDTO> createShoppingList(@RequestBody ShoppingListRequestDTO shoppingListRequestDTO) {
    ShoppingListResponseDTO createdShoppingList = shoppingListService.createShoppingList(shoppingListRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdShoppingList);
  }

  @GetMapping("/shoppinglists")
  public ResponseEntity<List<ShoppingListResponseDTO>> getAllShoppinglists() {
    List<ShoppingList> allLists = shoppingListService.getAllShoppingLists();
    return ResponseEntity.ok(ShoppingListMapper.toDTO(allLists));
  }

  @GetMapping("/shoppinglists/user")
  public ResponseEntity<List<ShoppingListResponseDTO>> getShoppingListForUser() {
    List<ShoppingListResponseDTO> shoppingLists = shoppingListService.getShoppingListsByUser();
    return ResponseEntity.ok(shoppingLists);
  }

  @DeleteMapping("/shoppinglists/{id}")
  public ResponseEntity<Void> deleteShoppingListById(@PathVariable Long id) {
    shoppingListService.deleteShoppingListById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/shoppinglists/from-recipes")
  public ResponseEntity<ShoppingListResponseDTO> createShoppingListFromRecipes(@RequestBody List<Long> recipeIds) {
    ShoppingListResponseDTO shoppingList = shoppingListService.createShoppingListFromRecipes(recipeIds);
    return ResponseEntity.status(HttpStatus.CREATED).body(shoppingList);
  }
}

