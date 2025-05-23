package lt.techin.dto.recipeIngredient;

import jakarta.validation.constraints.DecimalMin;

public record RecipeIngredientRequestDTO(
        Long id,
        String ingredientName,
        Long ingredientId,
        @DecimalMin(value = "0.0", inclusive = false, message = "Kiekis turi būti didesnis už nulį.")
        Double quantity,
        Long unitId
) {
}
