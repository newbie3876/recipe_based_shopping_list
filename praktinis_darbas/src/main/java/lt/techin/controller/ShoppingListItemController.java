package lt.techin.controller;

import jakarta.validation.Valid;
import lt.techin.dto.shoppingList.ShoppingListItemRequestDTO;
import lt.techin.dto.shoppingListItem.ShoppingListItemMapper;
import lt.techin.dto.shoppingListItem.ShoppingListItemResponseDTO;
import lt.techin.model.ShoppingListItem;
import lt.techin.service.ShoppingListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ShoppingListItemController {

  private final ShoppingListItemService shoppingListItemService;

  @Autowired
  public ShoppingListItemController(ShoppingListItemService shoppingListItemService) {
    this.shoppingListItemService = shoppingListItemService;
  }

  @PostMapping("/{listId}/items")
  public ResponseEntity<?> addItemToShoppingList(
          @PathVariable Long listId,
          @RequestBody @Valid ShoppingListItemRequestDTO itemDTO) {

    ShoppingListItem savedItem = shoppingListItemService.addItemToShoppingList(listId, itemDTO);

    ShoppingListItemResponseDTO responseDTO = ShoppingListItemMapper.toDTO(savedItem);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }
}
