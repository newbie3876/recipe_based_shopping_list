package lt.techin.dto.shoppingList;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ShoppingListRequestDTO(

        @NotNull
        Long userId,
        List<ShoppingListItemRequestDTO> items
) {
}
