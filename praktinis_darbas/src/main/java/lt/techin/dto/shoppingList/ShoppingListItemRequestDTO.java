package lt.techin.dto.shoppingList;

import jakarta.validation.constraints.NotNull;

public record ShoppingListItemRequestDTO(

        @NotNull
        Long ingredientId
        //@NotNull
        //BigDecimal quantity,
//        @NotNull
//        String unit,
        //@NotNull
        //Long unitId
//        List<IngredientCategoryRequestDTO> ingredientCategory

) {
}
