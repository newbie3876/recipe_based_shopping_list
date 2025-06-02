package lt.techin.dto.shoppingListItem;

import java.math.BigDecimal;

public record ShoppingListItemResponseDTO(
        Long id,
        String ingredientName,
        BigDecimal quantity,
        String unit
        //List<IngredientCategoryResponseDTO> ingredientCategory
) {

}
