package lt.techin.dto.ingredient;

import lt.techin.model.IngredientCategory;

public class IngredientCategoryMapper {

  public static IngredientCategoryResponseDTO toDTO(IngredientCategory category) {
    return new IngredientCategoryResponseDTO(
            category.getId(),
            category.getName()
    );
  }
}
