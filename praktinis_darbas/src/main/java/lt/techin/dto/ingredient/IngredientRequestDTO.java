package lt.techin.dto.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IngredientRequestDTO(
        @NotBlank
        @Size(min = 2, max = 250)
        String name,

        @NotNull
        Long ingredientCategoryId) {
}
