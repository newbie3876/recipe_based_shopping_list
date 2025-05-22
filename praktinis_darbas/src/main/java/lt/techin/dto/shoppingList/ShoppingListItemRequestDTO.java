package lt.techin.dto.shoppingList;

import jakarta.validation.constraints.NotNull;
import lt.techin.dto.ingredient.IngredientCategoryRequestDTO;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingListItemRequestDTO(

        @NotNull
        Long ingredientId,
        @NotNull
        BigDecimal quantity,
        @NotNull
        Long unitId,
        List<IngredientCategoryRequestDTO> ingredientCategory
) {
}
