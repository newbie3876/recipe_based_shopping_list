package lt.techin.dto.shoppingList;

import lt.techin.dto.ingredient.IngredientCategoryResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingListItemResponseDTO(

        //Long id,
        String ingredientName,
        BigDecimal quantity,
        String unit,
        List<IngredientCategoryResponseDTO> ingredientCategory

) {

}
