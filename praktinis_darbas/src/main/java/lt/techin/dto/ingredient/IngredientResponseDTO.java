package lt.techin.dto.ingredient;

public record IngredientResponseDTO(

        Long userId,
        Long ingredientId,
        String ingredientName,
        IngredientCategoryResponseDTO ingredientCategory

) {
}
