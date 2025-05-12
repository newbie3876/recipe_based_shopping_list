package lt.techin.service;

import lt.techin.model.*;
import lt.techin.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {

  private final ShoppingListRepository shoppingListRepository;

  @Autowired
  public ShoppingListService(ShoppingListRepository shoppingListRepository) {
    this.shoppingListRepository = shoppingListRepository;
  }

  public ShoppingList createShoppingList(ShoppingList shoppingList) {
    return this.shoppingListRepository.save(shoppingList);
  }

  public Optional<ShoppingList> getShoppingListById(long id) {
    return this.shoppingListRepository.findById(id);
  }

  public void deleteShoppingListById(long id) {
    if (!shoppingListRepository.existsById(id)) {
      throw new IllegalArgumentException("Prekių sąrašas su tokiu id nerasšas: " + id);
    }
    shoppingListRepository.deleteById(id);
  }

  public List<ShoppingList> getAllShoppingLists() {
    return this.shoppingListRepository.findAll();
  }
}