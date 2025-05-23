package lt.techin.dto.shoppingListItem;

import jakarta.validation.constraints.NotNull;
import lt.techin.dto.ingredientCategory.IngredientCategoryRequestDTO;

import java.util.List;

public record ShoppingListItemRequestDTO(

        @NotNull
        Long ingredientId,
        @NotNull
        Double quantity,
        @NotNull
        Long unitId,
        List<IngredientCategoryRequestDTO> ingredientCategory
) {
}
