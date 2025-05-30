package lt.techin.dto.ingredient;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IngredientCategoryRequestDTO(

        @NotNull
        Long id,

        @NotNull
        @Size(max = 150)
        String categoryName

) {
}
