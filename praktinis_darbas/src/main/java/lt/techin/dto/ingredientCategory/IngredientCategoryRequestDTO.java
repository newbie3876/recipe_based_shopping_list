package lt.techin.dto.ingredientCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IngredientCategoryRequestDTO(
        Long id,

        @NotBlank
        @Size(max = 255)
        String name
) {
}
