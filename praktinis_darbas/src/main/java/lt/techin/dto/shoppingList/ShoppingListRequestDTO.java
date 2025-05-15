package lt.techin.dto.shoppingList;

import org.springframework.lang.NonNull;

import java.util.List;

public record ShoppingListRequestDTO(

        @NonNull
        Long userId,
        List<ShoppingListItemRequestDTO> items

) {
}
