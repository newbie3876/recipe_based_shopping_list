package lt.techin.dto.shoppingList;

import java.math.BigDecimal;

public record ShoppingListItemResponseDTO(

        Long id,
        String ingredientName,
        BigDecimal quantity,
        String unitName
        //Long unitId
        //List<IngredientCategoryResponseDTO> ingredientCategory

) {

}
