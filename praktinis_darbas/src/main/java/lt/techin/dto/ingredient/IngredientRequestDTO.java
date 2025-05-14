package lt.techin.dto.ingredient;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IngredientRequestDTO(

        @NotNull
        @Size(min = 2, max = 250)
        @Column(nullable = false, length = 250)
        String name,

        @NotNull
        Long ingredientCategoryId

) {
}
