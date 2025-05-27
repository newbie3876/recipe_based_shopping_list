package lt.techin.dto.ingredient;

import lt.techin.dto.ingredientCategory.IngredientCategoryResponseDTO;
import lt.techin.model.Ingredient;
import lt.techin.model.IngredientCategory;
import lt.techin.model.Unit;

import java.util.List;

public class IngredientMapper {
  public static Ingredient toIngredient(IngredientRequestDTO ingredientRequestDTO, IngredientCategory ingredientCategory, Unit unit) {
    Ingredient ingredient = new Ingredient();
    ingredient.setName(ingredientRequestDTO.name());
    ingredient.setIngredientCategory(ingredientCategory);
    ingredient.setUnit(unit);
    return ingredient;
  }

  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
    return new IngredientResponseDTO(
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getIngredientCategory() != null
                    ? new IngredientCategoryResponseDTO(
                    ingredient.getIngredientCategory().getId(),
                    ingredient.getIngredientCategory().getName())
                    : null
    );
  }

  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
    return ingredients.stream()
            .map(IngredientMapper::toDTO)
            .toList();
  }
}


