//package lt.techin.dto.shoppingList;
//
//import lt.techin.dto.ingredientCategory.IngredientCategoryResponseDTO;
//import lt.techin.dto.shoppingListItem.ShoppingListItemRequestDTO;
//import lt.techin.dto.shoppingListItem.ShoppingListItemResponseDTO;
//import lt.techin.model.*;
//import lt.techin.repository.IngredientRepository;
//import lt.techin.repository.UnitRepository;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//public class ShoppingListMapper {
//  public static ShoppingList toShoppingList(ShoppingListRequestDTO requestDTO,
//                                            User user,
//                                            IngredientRepository ingredientRepository,
//                                            UnitRepository unitRepository) {
//    // 1. Sukuriame naują pirkinių sąrašą su vartotoju
//    ShoppingList shoppingList = new ShoppingList(user, LocalDateTime.now(), new ArrayList<>());
//
//    // 2️. Iteruojame per gautus ingredientus ir konvertuojame į ShoppingListItem
//    for (ShoppingListItemRequestDTO itemDTO : requestDTO.items()) {
//      Ingredient ingredient = ingredientRepository.findById(itemDTO.ingredientId())
//              .orElseThrow(() -> new RuntimeException("Ingredient not found"));
//
//      Unit unit = unitRepository.findById(itemDTO.unitId())
//              .orElseThrow(() -> new RuntimeException("Unit not found"));
//
//      ShoppingListItem item = new ShoppingListItem(shoppingList, ingredient, itemDTO.quantity(), unit);
//      shoppingList.getItems().add(item);
//    }
//
//    return shoppingList;
//  }
//
//  public static ShoppingListResponseDTO toDTO(ShoppingList shoppingList) {
//    List<ShoppingListItemResponseDTO> items = shoppingList.getItems() != null
//            ? shoppingList.getItems().stream()
//            .map(item -> new ShoppingListItemResponseDTO(
//                    item.getId(),
//                    item.getIngredient().getName(),
//                    item.getQuantity(),
//                    item.getUnit().getName(),
//                    Collections.singletonList(new IngredientCategoryResponseDTO(
//                            item.getIngredient().getIngredientCategory().getId(),
//                            item.getIngredient().getIngredientCategory().getName()
//                    ))))
//            .collect(Collectors.toList())
//            : new ArrayList<>(); // Jei items yra null, grąžiname tuščią sąrašą
//
//    return new ShoppingListResponseDTO(
//            shoppingList.getId(),
//            shoppingList.getUser().getId(),
//            shoppingList.getCreatedAt(),
//            items);
//  }
//
//  public static List<ShoppingListResponseDTO> toDTO(List<ShoppingList> shoppingLists) {
//    return shoppingLists.stream()
//            .map(ShoppingListMapper::toDTO)
//            .collect(Collectors.toList());
//  }
//}

//package lt.techin.dto.shoppingList;
//
//import lt.techin.model.ShoppingList;
//import lt.techin.model.User;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//public class ShoppingListMapper {
//  // ✅ Konvertuoja iš DTO į Entity (sukuriant naują ShoppingList)
//  public static ShoppingList toShoppingList(ShoppingListRequestDTO dto, User user) {
//    ShoppingList shoppingList = new ShoppingList();
//    shoppingList.setName(dto.name());
//    shoppingList.setUser(user); // <- būtina
//    shoppingList.setCreatedAt(LocalDateTime.now());
//    return shoppingList;
//  }
//
//  // ✅ Konvertuoja iš Entity į DTO (naudojama Controller'yje)
//  public static ShoppingListResponseDTO toDTO(ShoppingList shoppingList) {
//    return new ShoppingListResponseDTO(
//            shoppingList.getId(),
//            //shoppingList.getUser().getId(),
//            shoppingList.getName(),
//            shoppingList.getCreatedAt()
//    );
//  }
//
//  // ✅ Konvertuoja sąrašą Entity į sąrašą DTO
//  public static List<ShoppingListResponseDTO> toListDTO(List<ShoppingList> lists) {
//    return lists.stream()
//            .map(ShoppingListMapper::toDTO)
//            .collect(Collectors.toList());
//  }
//}

package lt.techin.dto.shoppingList;

import lt.techin.model.ShoppingList;
import lt.techin.model.ShoppingListItem;
import lt.techin.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingListMapper {

  // ✅ Sukuria ShoppingList iš DTO ir jau susietų Entity
  public static ShoppingList toShoppingList(ShoppingListRequestDTO dto, List<ShoppingListItem> items, User user) {
    ShoppingList shoppingList = new ShoppingList();
    shoppingList.setName(dto.name());
    shoppingList.setUser(user);
    shoppingList.setCreatedAt(LocalDateTime.now());

    // Priskiria kiekvienam item'ui šį sąrašą
    for (ShoppingListItem item : items) {
      item.setShoppingList(shoppingList);
    }

    shoppingList.setItems(items); // susieja visus itemus
    return shoppingList;
  }

  // ✅ Vieno sąrašo konvertavimas į DTO
  public static ShoppingListResponseDTO toDTO(ShoppingList shoppingList) {
    List<ShoppingListItemResponseDTO> itemDTOs = new ArrayList<>();

    for (ShoppingListItem item : shoppingList.getItems()) {
      ShoppingListItemResponseDTO dto = new ShoppingListItemResponseDTO(
              item.getId(),
              item.getIngredient().getName(),
              item.getQuantity(),
              item.getUnit().getName()
      );
      itemDTOs.add(dto);
    }

    return new ShoppingListResponseDTO(
            shoppingList.getId(),
            shoppingList.getName(),
            shoppingList.getCreatedAt(),
            itemDTOs
    );
  }

  // ✅ Sąrašo Entity į sąrašo DTO
  public static List<ShoppingListResponseDTO> toListDTO(List<ShoppingList> lists) {
    return lists.stream()
            .map(ShoppingListMapper::toDTO)
            .collect(Collectors.toList());
  }
}
