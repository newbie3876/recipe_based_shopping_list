package lt.techin.dto.ingredient;

import java.math.BigDecimal;
//import lt.techin.dto.ingredientCategory.IngredientCategoryResponseDTO;

public record IngredientResponseDTO(

//        Long id,
//        String name,
//        IngredientCategoryResponseDTO ingredientCategory

        Long id,
        String ingredientName,
        String categoryName,
        BigDecimal quantity,
        String unitName

) {
}
