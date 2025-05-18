package lt.techin.dto.ingredient;

import lt.techin.model.Ingredient;
import lt.techin.model.IngredientCategory;
import lt.techin.model.User;
import lt.techin.repository.IngredientCategoryRepository;

import java.util.List;

public class IngredientMapper {


  public static Ingredient toIngredient(IngredientRequestDTO ingredientRequestDTO, User user, IngredientCategoryRepository ingredientCategoryRepository) {
    Ingredient ingredient = new Ingredient();

    ingredient.setName(ingredientRequestDTO.ingredientName());
    IngredientCategory ingredientCategory = ingredientCategoryRepository.findById(ingredientRequestDTO.ingredientCategoryId())
            .orElseThrow(() -> new RuntimeException("Ingredient category not found"));

    ingredient.setIngredientCategory(ingredientCategory);
    ingredient.setUser(user);

    return ingredient;
  }

  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
    IngredientCategory category = ingredient.getIngredientCategory();
    IngredientCategoryResponseDTO categoryDTO = category != null
            ? new IngredientCategoryResponseDTO(category.getId(), category.getName())
            : null;

    return new IngredientResponseDTO(
            ingredient.getUser().getId(),
            ingredient.getId(),
            ingredient.getName(),
            categoryDTO
    );
  }

//    public static IngredientResponseDTO toDTO(Ingredient ingredient) {
//    return new IngredientResponseDTO(
//            ingredient.getId(),
//            ingredient.getName(),
//            new IngredientCategoryResponseDTO(
//                    //ingredient.getIngredientCategory().getId(),
//                    ingredient.getIngredientCategory().getName()
//            )
//    );
//  }

  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
    return ingredients.stream()
            .map(ingredient -> {
              IngredientCategory category = ingredient.getIngredientCategory();
              IngredientCategoryResponseDTO categoryDTO = category != null
                      ? new IngredientCategoryResponseDTO(category.getId(), category.getName())
                      : null;

              return new IngredientResponseDTO(
                      ingredient.getUser().getId(),
                      ingredient.getId(),
                      ingredient.getName(),
                      categoryDTO
              );
            }).toList();
  }

  //  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
//    return ingredients.stream()
//            .map(ingredient -> new IngredientResponseDTO(
//                    ingredient.getId(),
//                    ingredient.getName(),
//                    new IngredientCategoryResponseDTO(
//                            //ingredient.getIngredientCategory().getId(),
//                            ingredient.getIngredientCategory().getName()
//                    )
//            )).toList();
//  }
}


