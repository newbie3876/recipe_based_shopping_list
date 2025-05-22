package lt.techin.dto.shoppingList;

import java.util.List;

public record ShoppingListRequestDTO(

        List<ShoppingListItemRequestDTO> items
) {
}
