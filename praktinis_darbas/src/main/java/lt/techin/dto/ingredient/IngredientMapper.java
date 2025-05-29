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

  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
    List<IngredientCategoryResponseDTO> categoryDTOs = ingredient.getIngredientCategory() != null
            ? List.of(new IngredientCategoryResponseDTO(
            ingredient.getIngredientCategory().getId(),
            ingredient.getIngredientCategory().getName()
    ))
            : List.of(); // tuščias sąrašas, jei kategorijos nėra

    return new IngredientResponseDTO(
            //ingredient.getId(),
            ingredient.getName(),
            categoryDTOs
    );
  }

  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
    return ingredients.stream()
            .map(ingredient -> {
              List<IngredientCategoryResponseDTO> categoryDTOs = ingredient.getIngredientCategory() != null
                      ? List.of(new IngredientCategoryResponseDTO(
                      ingredient.getIngredientCategory().getId(),
                      ingredient.getIngredientCategory().getName()
              ))
                      : List.of(); // tuščias sąrašas, jei nėra kategorijos

              return new IngredientResponseDTO(
                      //ingredient.getId(),
                      ingredient.getName(),
                      categoryDTOs
              );
            })
            .toList();
  }

}


