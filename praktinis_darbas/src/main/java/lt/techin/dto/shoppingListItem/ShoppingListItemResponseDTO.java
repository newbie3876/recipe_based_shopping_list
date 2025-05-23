package lt.techin.dto.shoppingListItem;

import lt.techin.dto.ingredientCategory.IngredientCategoryResponseDTO;

import java.util.List;

public record ShoppingListItemResponseDTO(
        Long id,
        String ingredientName,
        Double quantity,
        String unit,
        List<IngredientCategoryResponseDTO> ingredientCategory
) {

}
