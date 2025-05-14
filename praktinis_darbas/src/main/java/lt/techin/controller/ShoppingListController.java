package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.model.ShoppingList;
import lt.techin.model.User;
import lt.techin.service.RecipeIngredientService;
import lt.techin.service.ShoppingListService;
import lt.techin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingListController {

  private final ShoppingListService shoppingListService;
  private final UserService userService;
  private final RecipeIngredientService recipeIngredientService;

  @Autowired
  public ShoppingListController(ShoppingListService shoppingListService,
                                UserService userService,
                                RecipeIngredientService recipeIngredientService) {
    this.shoppingListService = shoppingListService;
    this.userService = userService;
    this.recipeIngredientService = recipeIngredientService;
  }

  @GetMapping("/shoppinglists")
  public ResponseEntity<List<ShoppingList>> showAllShoppingLists() {
    List<ShoppingList> allLists = shoppingListService.getAllShoppingLists();
    return ResponseEntity.ok(allLists);
  }

  @GetMapping("shoppinglists/{id}")
  public ResponseEntity<ShoppingList> getShoppingList(@PathVariable long id) {
    Optional<ShoppingList> list = this.shoppingListService.getShoppingListById(id);
    if (list.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(list.get());
  }

  @PostMapping("/shoppinglists")
  public ResponseEntity<Object> createShoppingList(@Valid @RequestBody ShoppingList shoppingList,
                                                   @AuthenticationPrincipal UserDetails userDetails) {

    User user = userService.findUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("Vartotojas nerastas"));

    shoppingList.setUser(user);
    shoppingList.setCreatedAt(LocalDate.now());

    ShoppingList createdList = this.shoppingListService.createShoppingList(shoppingList);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdList.getId())
                            .toUri())
            .body(createdList);
  }

  @PutMapping("/shoppinglists/{id}")
  public ResponseEntity<Object> updateShoppingList(@PathVariable long id, @RequestBody ShoppingList shoppingList,
                                                   @AuthenticationPrincipal UserDetails userDetails) {

    Optional<ShoppingList> shoppingListFromDb = this.shoppingListService.getShoppingListById(id);
    if (shoppingListFromDb.isPresent()) {

      ShoppingList updatedList = shoppingListFromDb.get();

      User user = userService.findUserByUsername(userDetails.getUsername())
              .orElseThrow(() -> new UsernameNotFoundException("Vartotojas nerastas"));

      updatedList.setUser(shoppingList.getUser());
      updatedList.setCreatedAt(shoppingList.getCreatedAt());

      return ResponseEntity.ok(this.shoppingListService.createShoppingList(updatedList));
    }

    ShoppingList updatedList = this.shoppingListService.createShoppingList(shoppingList);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .replacePath("/api/shoppinglists/{id}")
                            .buildAndExpand(updatedList.getId())
                            .toUri())
            .body(shoppingList);
  }

  @DeleteMapping("/shoppinglists/{id}")
  public ResponseEntity<Void> deleteByShoppingList(@PathVariable long id) {
    Optional<ShoppingList> list = this.shoppingListService.getShoppingListById(id);

    if (list.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    this.shoppingListService.deleteShoppingListById(id);
    return ResponseEntity.noContent().build();
  }
}