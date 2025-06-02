package lt.techin.dto.shoppingListItem;

import lt.techin.dto.ingredient.IngredientCategoryMapper;
import lt.techin.model.ShoppingListItem;

import java.util.List;


public class ShoppingListItemMapper {

  public static ShoppingListItemResponseDTO toDTO(ShoppingListItem item) {
    return new ShoppingListItemResponseDTO(
            item.getId(),
            item.getIngredient().getName(),
            item.getQuantity(),
            item.getUnit().getName(),
            List.of(
                    IngredientCategoryMapper.toDTO(item.getIngredient().getIngredientCategory())
            )
    );
  }
}
