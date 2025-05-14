package lt.techin.dto.shoppinglist;

//public class ShoppingListMapper {
//
//  public static ShoppingList toShoppingList(ShoppingListRequestDTO shoppingListRequestDTO){
//    return new ShoppingList(
//            shoppingListRequestDTO.user(),
//            shoppingListRequestDTO.createdAt()
//    );
//  }
//
//  public static ShoppingListResponseDTO toDTO(ShoppingList shoppingList, List<ShoppingListItem> items) {
//    return new ShoppingListResponseDTO(
//            shoppingList.getId(),
//            List.of(shoppingList.getUser().getId()),
//            shoppingList.getUser().getUsername(),
//            shoppingList.getCreatedAt()
//    );
//  }
//
//  public static List<ShoppingListResponseDTO> toListDTO(List<ShoppingList> shoppingLists){
//    return shoppingLists.stream()
//            .map(s -> new ShoppingListResponseDTO(
//                    s.getId(),
//                    List.of(s.getUser().getId()),
//                    s.getUser().getUsername(),
//                    s.getCreatedAt()))
//            .toList();
//  }
//}
