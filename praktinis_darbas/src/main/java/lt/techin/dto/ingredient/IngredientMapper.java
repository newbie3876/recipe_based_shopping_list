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

  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
    List<IngredientResponseDTO> dtos = new ArrayList<>();
    for (Ingredient ingredient : ingredients) {
      dtos.add(toDTO(ingredient));
    }
    return dtos;
  }

  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
    String unitName = (ingredient.getUnit() != null) ? ingredient.getUnit().getName() : null;
    return new IngredientResponseDTO(
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getIngredientCategory().getName(),
            null,
            unitName
    );
  }

//  public static IngredientResponseDTO toDTO(Ingredient ingredient, ShoppingListItem item) {
//    return new IngredientResponseDTO(
//            ingredient.getId(),
//            ingredient.getName(),
//            ingredient.getIngredientCategory().getName(),
//            item.getQuantity(),
//            item.getUnit().getName()
//    );
//  }

  //  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
//    List<IngredientResponseDTO> dtos = new ArrayList<>();
//
//    for (Ingredient ingredient : ingredients) {
//      List<ShoppingListItem> items = ingredient.getShoppingListItems();
//
//      if (items != null && !items.isEmpty()) {
//        for (ShoppingListItem item : items) {
//          IngredientResponseDTO dto = new IngredientResponseDTO(
//                  ingredient.getId(),
//                  ingredient.getName(),
//                  ingredient.getIngredientCategory().getName(),
//                  item.getQuantity(),
//                  item.getUnit().getName()
//          );
//          dtos.add(dto);
//        }
//      } else {
//        // Jei nėra ShoppingListItem, vis tiek kuriam atsakymą (quantity = null)
//        dtos.add(toDTO(ingredient));
//      }
//    }
//
//    return dtos;
//  }

}

