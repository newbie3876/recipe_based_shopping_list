package lt.techin.repository;

import lt.techin.model.ShoppingList;
import lt.techin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

  List<ShoppingList> findByUser(User user);

  List<ShoppingList> findByUserId(Long userId);
}
