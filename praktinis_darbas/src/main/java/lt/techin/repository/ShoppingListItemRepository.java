package lt.techin.repository;

import lt.techin.model.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
  List<ShoppingListItem> findByShoppingListId(Long shoppingListId);
}
