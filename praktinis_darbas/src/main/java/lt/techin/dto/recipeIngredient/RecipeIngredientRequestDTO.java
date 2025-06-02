package lt.techin.dto.recipeIngredient;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record RecipeIngredientRequestDTO(
        Long id,
        Long ingredientId,            // ingredientas pagal ID (jei žinomas)
        String ingredientName,        // arba naujas ingredientas (jei ingredientId nėra)
        @DecimalMin(value = "0.0", inclusive = false, message = "Kiekis turi būti didesnis už nulį.")
        Double quantity,
        @NotNull(message = "Vieneto ID privalomas")
        Long unitId,
        @NotNull(message = "Recepto ID privalomas")
        Long recipeId,
        Long ingredientCategoryId     // kategorijos ID naujam ingredientui
) {
  @AssertTrue(message = "IngredientId arba ingredientName privalomas")
  public boolean isValidIngredientReference() {
    return ingredientId != null || (ingredientName != null && !ingredientName.trim().isEmpty());
  }
}

