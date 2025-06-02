package lt.techin.service;

import lt.techin.dto.shoppingList.ShoppingListItemRequestDTO;
import lt.techin.dto.shoppingList.ShoppingListMapper;
import lt.techin.dto.shoppingList.ShoppingListRequestDTO;
import lt.techin.dto.shoppingList.ShoppingListResponseDTO;
import lt.techin.model.*;
import lt.techin.repository.IngredientRepository;
import lt.techin.repository.ShoppingListRepository;
import lt.techin.repository.UnitRepository;
import lt.techin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {
  private final ShoppingListRepository shoppingListRepository;
  private final UserRepository userRepository;
  private final IngredientRepository ingredientRepository;
  private final UnitRepository unitRepository;

  @Autowired
  public ShoppingListService(ShoppingListRepository shoppingListRepository, UserRepository userRepository, IngredientRepository ingredientRepository, UnitRepository unitRepository) {
    this.shoppingListRepository = shoppingListRepository;
    this.userRepository = userRepository;
    this.ingredientRepository = ingredientRepository;
    this.unitRepository = unitRepository;
  }

  public List<ShoppingListResponseDTO> getShoppingListsByUserId(Long userId) {
    User user = getAuthenticatedUser();

    return shoppingListRepository.findByUserId(user.getId()).stream()
            .map(ShoppingListMapper::toDTO)
            .collect(Collectors.toList());
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

  public ShoppingListResponseDTO createShoppingList(ShoppingListRequestDTO requestDTO) {
    User user = getAuthenticatedUser();// Automatiškai gauname vartotoją iš Spring Security

    ShoppingList shoppingList = new ShoppingList(user, LocalDateTime.now(), new ArrayList<>());
    shoppingList.setItems(new ArrayList<>());

    if (requestDTO.items() == null || requestDTO.items().isEmpty()) {
      throw new IllegalArgumentException("Pirkinių krepšelis privalo turėti bent vieną ingredientą.");
    }

    for (ShoppingListItemRequestDTO itemDTO : requestDTO.items()) {
      Ingredient ingredient = ingredientRepository.findById(itemDTO.ingredientId())
              .orElseThrow(() -> new RuntimeException("Ingredient not found"));

      Unit unit = unitRepository.findById(itemDTO.unitId())
              .orElseThrow(() -> new RuntimeException("Unit not found"));

      ShoppingListItem item = new ShoppingListItem(shoppingList, ingredient, itemDTO.quantity(), unit);
      shoppingList.getItems().add(item);
    }

    ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);
    return ShoppingListMapper.toDTO(savedShoppingList);
  }

  public ShoppingList getOrCreateActiveShoppingListForUser(User user) {
    // Bandome rasti esamą aktyvų sąrašą
    return shoppingListRepository.findFirstByUserAndActiveTrue(user)
            .orElseGet(() -> {
              // Jei nerandam – sukuriam naują
              ShoppingList newList = new ShoppingList();
              newList.setUser(user);
              newList.setCreatedAt(LocalDateTime.now());
              newList.setItems(new ArrayList<>());
              newList.setActive(true); // labai svarbu!
              return shoppingListRepository.save(newList);
            });
  }

  public List<ShoppingListResponseDTO> getShoppingListsForAuthenticatedUser() {
    User user = getAuthenticatedUser();
    return shoppingListRepository.findByUserId(user.getId()).stream()
            .map(ShoppingListMapper::toDTO)
            .collect(Collectors.toList());
  }
}


