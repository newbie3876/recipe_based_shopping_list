package lt.techin.dto.shoppingList;

//import lt.techin.dto.shoppingListItem.ShoppingListItemResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ShoppingListResponseDTO(
        Long id,
        //Long userId,
        String name,
        LocalDateTime createdAt,
        List<ShoppingListItemResponseDTO> items
) {
}
