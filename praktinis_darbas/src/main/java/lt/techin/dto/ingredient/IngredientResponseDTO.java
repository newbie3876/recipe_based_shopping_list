package lt.techin.dto.ingredient;

public record IngredientResponseDTO(

        long id,
        String name,
        IngredientCategoryDto ingredientCategory

) {
}
