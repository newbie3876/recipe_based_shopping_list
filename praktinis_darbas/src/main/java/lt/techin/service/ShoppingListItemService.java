package lt.techin.service;

import jakarta.transaction.Transactional;
import lt.techin.model.Ingredient;
import lt.techin.model.ShoppingList;
import lt.techin.model.ShoppingListItem;
import lt.techin.model.Unit;
import lt.techin.repository.ShoppingListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListItemService {

  private final ShoppingListItemRepository shoppingListItemRepository;

  @Autowired
  public ShoppingListItemService(ShoppingListItemRepository shoppingListItemRepository) {
    this.shoppingListItemRepository = shoppingListItemRepository;
  }

  public List<ShoppingListItem> getShoppingListItemsById(Long shoppingListId) {
    return shoppingListItemRepository.findByShoppingListId(shoppingListId);
  }

  private ShoppingListItem createNewItem(ShoppingList shoppingList, Ingredient ingredient, int quantity, Unit unit) {
    ShoppingListItem newItem = new ShoppingListItem(shoppingList, ingredient, quantity, unit);
    return shoppingListItemRepository.save(newItem);
  }

  public Optional<ShoppingListItem> findItemById(long id) {
    return this.shoppingListItemRepository.findById(id);
  }

  public void removeItemById(long id) {
    this.shoppingListItemRepository.deleteById(id);
  }

  public ShoppingListItem addItem(ShoppingListItem shoppingListItem) {
    return this.shoppingListItemRepository.save(shoppingListItem);
  }

}
