package lt.techin.dto.ingredient;

import lt.techin.model.Ingredient;
import lt.techin.model.IngredientCategory;

import java.util.List;

public class IngredientMapper {

  public static Ingredient toIngredient(IngredientRequestDTO ingredientRequestDTO, IngredientCategory category) {
    Ingredient ingredient = new Ingredient();
    ingredient.setName(ingredientRequestDTO.name());
    ingredient.setIngredientCategory(category);
    return ingredient;
  }

  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
    return new IngredientResponseDTO(
            ingredient.getId(),
            ingredient.getName(),
            new IngredientCategoryDto(
                    ingredient.getIngredientCategory().getId(),
                    ingredient.getIngredientCategory().getName()
            )
    );
  }

  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
    return ingredients.stream()
            .map(ingredient -> new IngredientResponseDTO(
                    ingredient.getId(),
                    ingredient.getName(),
                    new IngredientCategoryDto(
                            ingredient.getIngredientCategory().getId(),
                            ingredient.getIngredientCategory().getName()
                    )))
            .toList();
  }

}


