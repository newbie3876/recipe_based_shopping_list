package lt.techin.dto.shoppingList;

import java.util.List;

public record ShoppingListRequestDTO(

        String name,
//        LocalDateTime createdAt,
        Long userId,
//        List<IngredientRequestDTO> ingredientRequestDTOs
        List<ShoppingListItemRequestDTO> items

) {
}
