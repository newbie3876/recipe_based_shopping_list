package lt.techin.service;

import jakarta.transaction.Transactional;
import lt.techin.model.*;
import lt.techin.repository.ShoppingListItemRepository;
import lt.techin.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShoppingListService {

  private final ShoppingListRepository shoppingListRepository;
  private final ShoppingListItemRepository shoppingListItemRepository;

  @Autowired
  public ShoppingListService(ShoppingListRepository shoppingListRepository,
                             ShoppingListItemRepository shoppingListItemRepository) {
    this.shoppingListRepository = shoppingListRepository;
    this.shoppingListItemRepository = shoppingListItemRepository;
  }

  public ShoppingList createShoppingList(User user) {
    ShoppingList shoppingList = new ShoppingList(user, LocalDate.now());
    return shoppingListRepository.save(shoppingList);
  }

  public List<ShoppingList> getUserShoppingLists(User user) {
    return shoppingListRepository.findByUserOrderByCreatedAtDesc(user);
  }

  public ShoppingList getShoppingListById(Long id) {
    return shoppingListRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prekių sąrašas nerastas pagal id: " + id));
  }

  public List<ShoppingListItem> getShoppingListItems(ShoppingList shoppingList) {
    return shoppingListItemRepository.findByShoppingList(shoppingList);
  }

  public List<ShoppingListItem> getShoppingListItemsById(Long shoppingListId) {
    return shoppingListItemRepository.findByShoppingListId(shoppingListId);
  }

  @Transactional
  public ShoppingListItem addItemToShoppingList(ShoppingList shoppingList, Ingredient ingredient, int quantity, Unit unit) {
    // Check if item already exists, if so update quantity
    return shoppingListItemRepository.findByShoppingListAndIngredient(shoppingList, ingredient)
            .map(existingItem -> {
              if (existingItem.getUnit().equals(unit)) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                return shoppingListItemRepository.save(existingItem);
              }
              // Optional: Handle unit mismatch if needed
              return createNewItem(shoppingList, ingredient, quantity, unit);
            })
            .orElseGet(() -> createNewItem(shoppingList, ingredient, quantity, unit));
  }

    // Create new item
    private ShoppingListItem createNewItem(ShoppingList shoppingList, Ingredient ingredient, int quantity, Unit unit) {
      ShoppingListItem newItem = new ShoppingListItem(shoppingList, ingredient, quantity, unit);
      return shoppingListItemRepository.save(newItem);
    }

  @Transactional
  public void removeItemFromShoppingList(Long itemId) {
//    patikrinam, ar egzistuoja Item pagal id
    if (!shoppingListItemRepository.existsById(itemId)) {
      throw new IllegalArgumentException("Toks prekės id neegzistuoja: " + itemId);
    }
    shoppingListItemRepository.deleteById(itemId);
  }

  @Transactional
  public void updateItemQuantity(Long itemId, int newQuantity) {
    ShoppingListItem item = shoppingListItemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Prekė nerasta"));
    item.setQuantity(newQuantity);
    shoppingListItemRepository.save(item);
  }

  @Transactional
  public void deleteShoppingList(Long id) {
    ShoppingList shoppingList = shoppingListRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Toks prekių sąrašas neegzistuoja"));

    // Pirmiausia ištrinam visas prekes
    shoppingListItemRepository.deleteByShoppingList(shoppingList);

    // Tada ištrinam patį sąrašą
    shoppingListRepository.delete(shoppingList);
  }
}