package lt.techin.controller;


import jakarta.validation.Valid;
import lt.techin.dto.shoppinglist.ShoppingListMapper;
import lt.techin.dto.shoppinglist.ShoppingListResponseDTO;
import lt.techin.model.ShoppingList;
import lt.techin.model.ShoppingListItem;
import lt.techin.model.User;
import lt.techin.service.ShoppingListService;
import lt.techin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingListController {

  private final ShoppingListService shoppingListService;
  private final UserService userService;
  private final ShoppingListMapper shoppingListMapper;

  @Autowired
  public ShoppingListController(ShoppingListService shoppingListService,
                                UserService userService,
                                ShoppingListMapper shoppingListMapper) {
    this.shoppingListService = shoppingListService;
    this.userService = userService;
    this.shoppingListMapper = shoppingListMapper;
  }

  @PostMapping("/shopping-lists")
  public ResponseEntity<Object> createShoppingList(@Valid @RequestBody ShoppingListResponseDTO dto) {
//    tikrinam ar username egzistuoja
    if (userService.existsUserByUsername(dto.username())) {
      return ResponseEntity.badRequest().body(Map.of("username", "Already exists"));
    }

//    tikrinam ar nėra tuščias user
    Optional<User> user = userService.findUserByUsername(dto.username());
    if (user.isEmpty()) {
      return ResponseEntity.badRequest().body(Map.of("username", "User not found"));
    }
//    sukuriam shopping list
    ShoppingList createdList = this.shoppingListService.createShoppingList(user.get());
//    fetchinam shopping list itemus
    List<ShoppingListItem> items = shoppingListService.getShoppingListItems(createdList);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createdList.getId())
                            .toUri())
            .body(shoppingListMapper.toDTO(createdList, items));
  }

  @GetMapping
  public ResponseEntity<List<ShoppingListResponseDTO>> getUserShoppingLists(@AuthenticationPrincipal UserDetails userDetails) {
    User user = userService.findUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    List<ShoppingList> shoppingLists = shoppingListService.getUserShoppingLists(user);

    List<ShoppingListResponseDTO> dtos = shoppingLists.stream()
            .map(list -> {
              List<ShoppingListItem> items = shoppingListService.getShoppingListItems(list);
              return shoppingListMapper.toDTO(list, items);
            })
            .collect(Collectors.toList());

    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/shoppinglists/{id}")
  public ResponseEntity<ShoppingListResponseDTO> getShoppingList(@PathVariable Long id,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
    User user = userService.findUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    ShoppingList shoppingList = shoppingListService.getShoppingListById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopping list not found"));

    // Security check - make sure the shopping list belongs to the logged-in user
    if (!shoppingList.getUser().getId().equals(user.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this shopping list");
    }

    List<ShoppingListItem> items = shoppingListService.getShoppingListItems(shoppingList);
    return ResponseEntity.ok(shoppingListMapper.toDTO(shoppingList, items));
  }

  @DeleteMapping("/shoppinglists/{id}")
  public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
    // Ieškome shopping list
    ShoppingList shoppingList = shoppingListService.getShoppingListById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopping list not found"));

    // Patikriname apsaugą
    User user = userService.findUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    if (!shoppingList.getUser().getId().equals(user.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this shopping list");
    }

    shoppingListService.deleteShoppingList(id);
    return ResponseEntity.noContent().build();
  }
}