package lt.techin.dto.ingredient;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record IngredientRequestDTO(

        Long ingredientId,
        @NotNull
        @Size(min = 2, max = 250)
        String ingredientName,
        @NotNull
        Long ingredientCategoryId,
        @NotNull
        BigDecimal quantity,
        @NotNull
        Long unitId,
        List<IngredientCategoryRequestDTO> ingredientCategory

) {
}
