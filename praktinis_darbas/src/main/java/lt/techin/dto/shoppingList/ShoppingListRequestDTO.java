package lt.techin.dto.shoppingList;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ShoppingListRequestDTO(
        //@NotNull
        //Long userId,
        //String name,
        //List<ShoppingListItemRequestDTO> items
        String name,
//        LocalDateTime createdAt,
        Long userId,
//        List<IngredientRequestDTO> ingredientRequestDTOs
        List<ShoppingListItemRequestDTO> items

) {
}
