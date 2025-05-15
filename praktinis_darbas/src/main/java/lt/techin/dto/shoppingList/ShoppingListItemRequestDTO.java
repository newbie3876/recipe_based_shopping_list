package lt.techin.dto.shoppingList;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ShoppingListItemRequestDTO(

        @NotNull
        Long ingredientId,
        @NotNull
        BigDecimal quantity,
        @NotNull
        Long unitId

) {
}
