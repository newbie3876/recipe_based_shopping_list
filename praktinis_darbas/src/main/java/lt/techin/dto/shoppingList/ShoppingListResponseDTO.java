package lt.techin.dto.shoppingList;

import java.util.List;

public record ShoppingListResponseDTO(

        Long id,
        Long userId,
        java.time.LocalDateTime createdAt,
        List<ShoppingListItemResponseDTO> items

) {
}
