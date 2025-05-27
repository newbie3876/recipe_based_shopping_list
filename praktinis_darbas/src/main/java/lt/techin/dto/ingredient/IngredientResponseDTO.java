package lt.techin.dto.ingredient;

import lt.techin.dto.ingredientCategory.IngredientCategoryResponseDTO;

public record IngredientResponseDTO(
        Long id,
        String name,
        IngredientCategoryResponseDTO ingredientCategory) {
}
