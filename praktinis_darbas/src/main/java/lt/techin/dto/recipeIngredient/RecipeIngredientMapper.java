package lt.techin.dto.recipeIngredient;

import lt.techin.dto.ingredientCategory.IngredientCategoryResponseDTO;
import lt.techin.model.Ingredient;
import lt.techin.model.Recipe;
import lt.techin.model.RecipeIngredient;
import lt.techin.model.Unit;

public class RecipeIngredientMapper {
  public static RecipeIngredient toEntity(RecipeIngredientRequestDTO dto, Recipe recipe, Ingredient ingredient, Unit unit) {
    RecipeIngredient ri = new RecipeIngredient();
    ri.setRecipe(recipe);
    ri.setIngredient(ingredient);
    ri.setQuantity(dto.quantity());
    ri.setUnit(unit);
    return ri;
  }

  public static RecipeIngredientResponseDTO toDTO(RecipeIngredient ri) {
    Ingredient ingredient = ri.getIngredient();

    return new RecipeIngredientResponseDTO(
            ri.getId(),
            ingredient.getName(),
            ingredient.getIngredientCategory() != null
                    ? new IngredientCategoryResponseDTO(
                    ingredient.getIngredientCategory().getId(),
                    ingredient.getIngredientCategory().getName()
            )
                    : null,
            ri.getQuantity(),
            ri.getUnit() != null ? ri.getUnit().getId() : null,
            ri.getUnit() != null ? ri.getUnit().getName() : null
    );
  }
}
