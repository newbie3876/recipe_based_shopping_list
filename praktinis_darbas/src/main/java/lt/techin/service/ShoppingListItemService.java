package lt.techin.service;

import lt.techin.model.ShoppingListItem;
import lt.techin.repository.ShoppingListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingListItemService {

  private final ShoppingListItemRepository shoppingListItemRepository;

  @Autowired
  public ShoppingListItemService(ShoppingListItemRepository shoppingListItemRepository) {
    this.shoppingListItemRepository = shoppingListItemRepository;
  }

  public List<ShoppingListItem> getAllShoppingListItems() {
    return shoppingListItemRepository.findAll();
  }

  public ShoppingListItem getShoppingListItemById(long id) {
    return shoppingListItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Shopping list item not found with id: " + id));
  }

  public ShoppingListItem saveShoppingListItem(ShoppingListItem ingredient) {
    return shoppingListItemRepository.save(ingredient);
  }

  public void deleteShoppingListItemById(long id) {
    if (!shoppingListItemRepository.existsById(id)) {
      throw new IllegalArgumentException("Shopping list item not found with id: " + id);
    }
    shoppingListItemRepository.deleteById(id);
  }
}
