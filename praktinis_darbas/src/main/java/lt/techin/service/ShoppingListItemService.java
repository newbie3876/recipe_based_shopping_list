package lt.techin.service;

import lt.techin.dto.shoppingList.ShoppingListItemRequestDTO;
import lt.techin.model.*;
import lt.techin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ShoppingListItemService {

  private final ShoppingListRepository shoppingListRepository;
  private final IngredientRepository ingredientRepository;
  private final UnitRepository unitRepository;
  private final ShoppingListItemRepository shoppingListItemRepository;
  private final UserRepository userRepository;

  @Autowired
  public ShoppingListItemService(
          ShoppingListRepository shoppingListRepository,
          IngredientRepository ingredientRepository,
          UnitRepository unitRepository,
          ShoppingListItemRepository shoppingListItemRepository,
          UserRepository userRepository) {
    this.shoppingListRepository = shoppingListRepository;
    this.ingredientRepository = ingredientRepository;
    this.unitRepository = unitRepository;
    this.shoppingListItemRepository = shoppingListItemRepository;
    this.userRepository = userRepository;
  }

  public ShoppingListItem addItemToShoppingList(Long shoppingListId, ShoppingListItemRequestDTO itemDTO) {
    User user = getAuthenticatedUser();

    // Find the shopping list and validate ownership
    ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
            .filter(list -> list.getUser().getId().equals(user.getId()))
            .orElseThrow(() -> new RuntimeException("Shopping list not found or unauthorized"));

    Ingredient ingredient = ingredientRepository.findById(itemDTO.ingredientId())
            .orElseThrow(() -> new RuntimeException("Ingredient not found"));

    Unit unit = unitRepository.findById(itemDTO.unitId())
            .orElseThrow(() -> new RuntimeException("Unit not found"));

    // sukuriam ingredientą
    ShoppingListItem item = new ShoppingListItem();
    item.setShoppingList(shoppingList);
    item.setIngredient(ingredient);
    item.setQuantity(itemDTO.quantity());
    item.setUnit(unit);

    return shoppingListItemRepository.save(item);
  }

  public User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new RuntimeException("User is not authenticated");
    }

    String username = authentication.getName(); // Gausime prisijungusio vartotojo vardą

    return userRepository.findByUsername(username) // Surandame vartotoją pagal vardą
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
