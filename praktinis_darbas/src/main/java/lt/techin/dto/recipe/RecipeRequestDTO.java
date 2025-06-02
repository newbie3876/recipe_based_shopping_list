package lt.techin.dto.recipe;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lt.techin.dto.recipeIngredient.RecipeIngredientRequestDTO;

import java.util.List;

public record RecipeRequestDTO(
        String name,
        String description,
        String link,
        @NotNull(message = "Porcijų skaičius yra privalomas")
        @Min(value = 1, message = "Porcijų turi būti bent 1")
        Integer portions,
        Long categoryId,
        @JsonSetter(nulls = Nulls.AS_EMPTY)
        List<RecipeIngredientRequestDTO> ingredients) {
}
