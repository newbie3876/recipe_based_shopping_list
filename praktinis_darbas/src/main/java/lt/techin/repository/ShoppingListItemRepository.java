package lt.techin.repository;

import lt.techin.model.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {

  List<ShoppingListItem> findByShoppingListId(Long shoppingListId);

  List<ShoppingListItem> findByIngredientId(Long ingredientId);

  Optional<ShoppingListItem> findTopByIngredientIdOrderByIdDesc(Long ingredientId);
}
