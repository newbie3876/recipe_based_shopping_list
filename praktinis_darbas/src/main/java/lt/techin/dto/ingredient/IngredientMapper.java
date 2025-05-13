package lt.techin.dto.ingredient;

import lt.techin.model.Ingredient;

import java.util.List;

public class IngredientMapper {

  public static Ingredient toIngredient(IngredientRequestDTO ingredientRequestDTO) {
    return new Ingredient(
            ingredientRequestDTO.name());
  }

  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
    return new IngredientResponseDTO(
            ingredient.getId(),
            ingredient.getName()
    );
  }

  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
    return ingredients.stream()
            .map(ingredient -> new IngredientResponseDTO(
                    ingredient.getId(),
                    ingredient.getName())).toList();
  }

}


