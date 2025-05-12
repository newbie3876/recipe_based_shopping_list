package lt.techin.dto.shoppinglist;

import lt.techin.model.ShoppingListItem;

import java.util.List;

public class ShoppingListItemMapper {

  public static ShoppingListItem toShoppingListItem(ShoppingListItemRequestDTO dto){
    return new ShoppingListItem(
            dto.shoppingLists().get(0),
            dto.ingredients().get(0),
            dto.quantity(),
            dto.units().get(0)
    );
  }

  public static ShoppingListItemResponseDTO toDTO(ShoppingListItem item) {
    return new ShoppingListItemResponseDTO(
            item.getId(),
            List.of(item.getShoppingList().getId()),
            List.of(item.getIngredient().getId()),
            item.getQuantity(),
            List.of(item.getUnit().getId())
    );

  }

  public static List<ShoppingListItemResponseDTO> toItemListDTO(List<ShoppingListItem> items) {
    return items.stream()
            .map(ShoppingListItemMapper::toDTO)
            .toList();
  }
}
