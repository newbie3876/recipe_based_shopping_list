package lt.techin.repository;

import lt.techin.model.Ingredient;
import lt.techin.model.ShoppingList;
import lt.techin.model.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {

  List<ShoppingListItem> findByShoppingList(ShoppingList shoppingList);

  Optional<ShoppingListItem> findByShoppingListAndIngredient(ShoppingList shoppingList, Ingredient ingredient);

  void deleteByShoppingList(ShoppingList shoppingList);

  List<ShoppingListItem> findByShoppingListId(Long shoppingListId);
}
