package lt.techin.dto.recipe;

import lt.techin.dto.recipeIngredient.RecipeIngredientResponseDTO;

import java.util.List;

public record RecipeResponseDTO(Long id,
                                String name,
                                String description,
                                int portions,
                                String link,
                                String categoryName,
                                List<RecipeIngredientResponseDTO> ingredients
) {
}
