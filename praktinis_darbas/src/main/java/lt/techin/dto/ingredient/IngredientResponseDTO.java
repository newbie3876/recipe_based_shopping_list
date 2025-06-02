package lt.techin.dto.ingredient;

import java.math.BigDecimal;

public record IngredientResponseDTO(

        Long id,
        String ingredientName,
        String categoryName,
        BigDecimal quantity,
        String unitName

) {
}
