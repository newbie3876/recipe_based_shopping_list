package lt.techin.dto.ingredient;

import lt.techin.model.Ingredient;
import lt.techin.model.IngredientCategory;
import lt.techin.model.ShoppingListItem;
import lt.techin.model.User;

import java.util.List;

public class IngredientMapper {

  public static Ingredient toIngredient(IngredientRequestDTO ingredientRequestDTO,
                                        IngredientCategory ingredientCategory,
                                        User user) {
    Ingredient ingredient = new Ingredient();

    // Nustatome pavadinimą
    ingredient.setName(ingredientRequestDTO.ingredientName());

    // Priskiriame kategoriją
    ingredient.setIngredientCategory(ingredientCategory);

    // Jei reikia shoppingListItems, naudokime tinkamą būdą
    if (ingredientRequestDTO.quantity() != null && ingredientRequestDTO.unitId() != null) {
      ShoppingListItem shoppingListItem = new ShoppingListItem(
              ingredientRequestDTO.ingredientName(),
              ingredientRequestDTO.quantity(),
              ingredientRequestDTO.unitId()
      );
      ingredient.setShoppingListItems(List.of(shoppingListItem)); // Jei ShoppingListItem yra kolekcija
    }

    // Priskiriame vartotoją
    ingredient.setUser(user);

    return ingredient;
  }
//  public static Ingredient toIngredient(IngredientRequestDTO ingredientRequestDTO,
//                                        IngredientCategory ingredientCategory,
//                                        User user) {
//    Ingredient ingredient = new Ingredient();
//
//    //ingredient.setName(ingredientRequestDTO.ingredientName());
//    //ingredient.setIngredientCategory(ingredientCategory);
//    ingredient.setShoppingListItems(new ShoppingListItem(
//            ingredientRequestDTO.ingredientCategory(),
//            ingredientRequestDTO.ingredientName(),
//            ingredientRequestDTO.quantity(),
//            ingredientRequestDTO.unitId()
//
//    ));
//    //ingredient.setShoppingListItems(ingredientRequestDTO.unitId());
//    //ingredient.setIngredientCategory(ingredientCategory);
//    ingredient.setUser(user);
//
//    return ingredient;
//  }

  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
    return new IngredientResponseDTO(
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getIngredientCategory() != null ?
                    new IngredientCategoryResponseDTO(
                            //ingredient.getIngredientCategory().getId(), // ID pridedamas, jei būtinas
                            ingredient.getIngredientCategory().getName()
                    )
                    : null // Jei nėra kategorijos, grąžinama null
    );
  }

//  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
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
            .map(ingredient -> new IngredientResponseDTO(
                    ingredient.getId(),
                    ingredient.getName(),
                    ingredient.getIngredientCategory() != null ?
                            new IngredientCategoryResponseDTO(
                                    //ingredient.getIngredientCategory().getId(),
                                    ingredient.getIngredientCategory().getName()
                            )
                            : null
            )).toList();
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


