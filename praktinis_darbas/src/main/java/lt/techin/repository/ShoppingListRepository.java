package lt.techin.repository;

import lt.techin.model.ShoppingList;
import lt.techin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
  List<ShoppingList> findByUserId(Long userId);

  Optional<ShoppingList> findFirstByUserAndActiveTrue(User user);
}