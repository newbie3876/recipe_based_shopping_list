//package lt.techin.dto.ingredient;
//
//import lt.techin.dto.ingredientCategory.IngredientCategoryResponseDTO;
//import lt.techin.model.Ingredient;
//import lt.techin.model.IngredientCategory;
//import lt.techin.model.Unit;
//
//import java.util.List;
//
//public class IngredientMapper {
//  public static Ingredient toIngredient(IngredientRequestDTO ingredientRequestDTO, IngredientCategory ingredientCategory, Unit unit) {
//    Ingredient ingredient = new Ingredient();
//    ingredient.setName(ingredientRequestDTO.name());
//    ingredient.setIngredientCategory(ingredientCategory);
//    ingredient.setUnit(unit);
//    return ingredient;
//  }
//
//  public static IngredientResponseDTO toDTO(Ingredient ingredient) {
//    return new IngredientResponseDTO(
//            ingredient.getId(),
//            ingredient.getName(),
//            ingredient.getIngredientCategory() != null
//                    ? new IngredientCategoryResponseDTO(
//                    ingredient.getIngredientCategory().getId(),
//                    ingredient.getIngredientCategory().getName())
//                    : null
//    );
//  }
//
//  public static List<IngredientResponseDTO> toListDTO(List<Ingredient> ingredients) {
//    return ingredients.stream()
//            .map(IngredientMapper::toDTO)
//            .toList();
//  }
//}


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

