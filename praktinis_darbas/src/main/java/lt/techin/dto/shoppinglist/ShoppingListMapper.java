package lt.techin.dto.shoppinglist;

import lt.techin.model.ShoppingList;
import lt.techin.model.ShoppingListItem;

import java.util.List;

public class ShoppingListMapper {

  public static ShoppingList toShoppingList(ShoppingListRequestDTO shoppingListRequestDTO){
    return new ShoppingList(
            shoppingListRequestDTO.user(),
            shoppingListRequestDTO.createdAt()
    );
  }

  public static ShoppingListResponseDTO toDTO(ShoppingList shoppingList, List<ShoppingListItem> items) {
    return new ShoppingListResponseDTO(
            shoppingList.getId(),
            List.of(shoppingList.getUser().getId()),
            shoppingList.getUser().getUsername(), // Assuming User has a username field
            shoppingList.getCreatedAt()
    );
  }

  public static List<ShoppingListResponseDTO> toListDTO(List<ShoppingList> shoppingLists){
    return shoppingLists.stream()
            .map(s -> new ShoppingListResponseDTO(
                    s.getId(),
                    List.of(s.getUser().getId()),
                    s.getUser().getUsername(),
                    s.getCreatedAt()))
            .toList();
  }
}
