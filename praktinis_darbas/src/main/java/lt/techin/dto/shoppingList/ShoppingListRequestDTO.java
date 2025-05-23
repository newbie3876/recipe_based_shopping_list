package lt.techin.dto.shoppingList;

import jakarta.validation.constraints.NotNull;
import lt.techin.dto.shoppingListItem.ShoppingListItemRequestDTO;

import java.util.List;

public record ShoppingListRequestDTO(
        @NotNull
        Long userId,
        String name,
        List<ShoppingListItemRequestDTO> items
) {
}
