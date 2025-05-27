package lt.techin.dto.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IngredientRequestDTO(
        Long id,
        @NotBlank
        @Size(min = 2, max = 250)
        String name,
        Long unitId,
        Double quantity,
        Long ingredientCategoryId) {
}
