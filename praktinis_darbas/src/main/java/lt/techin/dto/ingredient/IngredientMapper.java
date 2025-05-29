package lt.techin.dto.ingredient;

import lt.techin.model.*;

import java.util.ArrayList;
import java.util.List;

public class IngredientMapper {

  public static Ingredient toIngredient(IngredientRequestDTO dto, IngredientCategory category, Unit unit, User user) {
    Ingredient ingredient = new Ingredient();
    ingredient.setName(dto.ingredientName());
    ingredient.setIngredientCategory(category);
    ingredient.setUser(user);

    // Sukuriam ShoppingListItem
    ShoppingListItem item = new ShoppingListItem();
    item.setIngredient(ingredient); // svarbu – susieti atgal
    item.setQuantity(dto.quantity());
    item.setUnit(unit);
    item.setShoppingList(null); // jei nenaudojam ShoppingList

    // Pridedam į sąrašą
    List<ShoppingListItem> items = new ArrayList<>();
    items.add(item);
    ingredient.setShoppingListItems(items);

    return ingredient;
  }

//  public static Ingredient toIngredient(IngredientRequestDTO ingredientRequestDTO,
//                                        IngredientCategory ingredientCategory,
//                                        User user) {
//    Ingredient ingredient = new Ingredient();
//
//    // Nustatome pavadinimą
//    ingredient.setName(ingredientRequestDTO.ingredientName());
//
//    // Priskiriame kategoriją
//    ingredient.setIngredientCategory(ingredientCategory);
//
//    // Jei reikia shoppingListItems, naudokime tinkamą būdą
//    if (ingredientRequestDTO.quantity() != null && ingredientRequestDTO.unitId() != null) {
//      ShoppingListItem shoppingListItem = new ShoppingListItem(
//              ingredientRequestDTO.ingredientName(),
//              ingredientRequestDTO.quantity(),
//              ingredientRequestDTO.unitId()
//      );
//      ingredient.setShoppingListItems(List.of(shoppingListItem)); // Jei ShoppingListItem yra kolekcija
//    }
//
//    // Priskiriame vartotoją
//    ingredient.setUser(user);
//
//    return ingredient;
//  }
//
//  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
//    List<IngredientCategoryResponseDTO> categoryDTOs = ingredient.getIngredientCategory() != null
//            ? List.of(new IngredientCategoryResponseDTO(
//            ingredient.getIngredientCategory().getId(),
//            ingredient.getIngredientCategory().getName()
//    ))
//            : List.of(); // tuščias sąrašas, jei kategorijos nėra
//
//    return new IngredientResponseDTO(
//            //ingredient.getId(),
//            ingredient.getName(),
//            categoryDTOs
//    );
//  }

  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
    List<IngredientResponseDTO> dtos = new ArrayList<>();

    for (Ingredient ingredient : ingredients) {
      for (ShoppingListItem item : ingredient.getShoppingListItems()) {
        IngredientResponseDTO dto = new IngredientResponseDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getIngredientCategory().getName(),
                item.getQuantity(),
                item.getUnit().getName()
        );
        dtos.add(dto);
      }
    }

    return dtos;
  }

//
//  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
//    return ingredients.stream()
//            .map(ingredient -> {
//              List<IngredientCategoryResponseDTO> categoryDTOs = ingredient.getIngredientCategory() != null
//                      ? List.of(new IngredientCategoryResponseDTO(
//                      ingredient.getIngredientCategory().getId(),
//                      ingredient.getIngredientCategory().getName()
//              ))
//                      : List.of(); // tuščias sąrašas, jei nėra kategorijos
//
//              return new IngredientResponseDTO(
//                      //ingredient.getId(),
//                      ingredient.getName(),
//                      categoryDTOs
//              );
//            })
//            .toList();
//  }


  public static IngredientResponseDTO toDTO(Ingredient ingredient, ShoppingListItem item) {
    return new IngredientResponseDTO(
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getIngredientCategory().getName(),
            item.getQuantity(),
            item.getUnit().getName()
    );
  }

}


