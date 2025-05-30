package lt.techin.dto.recipeIngredient;

import lt.techin.dto.ingredientCategory.IngredientCategoryResponseDTO;

public record RecipeIngredientResponseDTO(
        Long id,
        String ingredientName,
        IngredientCategoryResponseDTO category,
        Double quantity,
        Long unitId,
        String unitName) {
}
