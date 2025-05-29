package lt.techin.dto.shoppingList;

import lt.techin.dto.ingredient.IngredientCategoryResponseDTO;
import lt.techin.model.*;
import lt.techin.repository.IngredientRepository;
import lt.techin.repository.UnitRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ShoppingListMapper {
  public static ShoppingList toShoppingList(ShoppingListRequestDTO requestDTO, User user,
                                            IngredientRepository ingredientRepository, UnitRepository unitRepository) {

    // 1. Sukuriame naują pirkinių sąrašą su vartotoju
    ShoppingList shoppingList = new ShoppingList(user, LocalDateTime.now(), new ArrayList<>());

    // 2️. Iteruojame per gautus ingredientus ir konvertuojame į ShoppingListItem
    for (ShoppingListItemRequestDTO itemDTO : requestDTO.items()) {
      Ingredient ingredient = ingredientRepository.findById(itemDTO.ingredientId())
              .orElseThrow(() -> new RuntimeException("Ingredient not found"));

      Unit unit = unitRepository.findById(itemDTO.unitId())
              .orElseThrow(() -> new RuntimeException("Unit not found"));

      ShoppingListItem item = new ShoppingListItem(shoppingList, ingredient, itemDTO.quantity(), unit);
      shoppingList.getItems().add(item);
    }

    return shoppingList;
  }


  public static ShoppingListResponseDTO toDTO(ShoppingList shoppingList) {
    List<ShoppingListItemResponseDTO> items = shoppingList.getItems() != null
            ? shoppingList.getItems().stream()
            .map(item -> new ShoppingListItemResponseDTO(
                    item.getId(),
                    item.getIngredient().getName(),
                    item.getQuantity(),
                    item.getUnit().getName(),
                    item.getUnit().getId(),
                    Collections.singletonList(new IngredientCategoryResponseDTO(
                            item.getIngredient().getIngredientCategory().getId(),
                            item.getIngredient().getIngredientCategory().getName()
                    ))))
            .collect(Collectors.toList())
            : new ArrayList<>(); // Jei items yra null, grąžiname tuščią sąrašą

    return new ShoppingListResponseDTO(
            //shoppingList.getId(),
            //shoppingList.getUser().getId(),
            shoppingList.getCreatedAt(),
            items);
  }
}