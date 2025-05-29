package lt.techin.dto.ingredient;

import java.util.List;

public record IngredientResponseDTO(

        //Long userId,
        //Long ingredientId,
        String ingredientName,
        List<IngredientCategoryResponseDTO> ingredientCategory

) {
}
