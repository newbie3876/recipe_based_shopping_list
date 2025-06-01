package lt.techin.dto.recipe;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lt.techin.dto.recipeIngredient.RecipeIngredientRequestDTO;

import java.util.List;

public record RecipeRequestDTO(
        String name,
        String description,
        String link,
        Integer portions,
        Long categoryId,
        @JsonSetter(nulls = Nulls.AS_EMPTY)
        List<RecipeIngredientRequestDTO> ingredients) {
}
