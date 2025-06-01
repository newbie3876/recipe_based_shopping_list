//package lt.techin.service;
//
//import lt.techin.dto.shoppingList.ShoppingListMapper;
//import lt.techin.dto.shoppingList.ShoppingListRequestDTO;
//import lt.techin.dto.shoppingList.ShoppingListResponseDTO;
//import lt.techin.dto.shoppingListItem.ShoppingListItemRequestDTO;
//import lt.techin.exceptions.IngredientNotFoundException;
//import lt.techin.exceptions.UnitNotFoundException;
//import lt.techin.exceptions.UserNotAuthenticatedException;
//import lt.techin.exceptions.UserNotFoundException;
//import lt.techin.model.*;
//import lt.techin.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ShoppingListService {
//  private final RecipeService recipeService;
//  private final ShoppingListRepository shoppingListRepository;
//  private final ShoppingListItemRepository shoppingListItemRepository;
//  private final UserService userService;
//  private final UserRepository userRepository;
//  private final IngredientRepository ingredientRepository;
//  private final UnitRepository unitRepository;
//  private final RecipeRepository recipeRepository;
//
//  @Autowired
//  public ShoppingListService(RecipeService recipeService, ShoppingListRepository shoppingListRepository, ShoppingListItemRepository shoppingListItemRepository, UserService userService, IngredientRepository ingredientRepository, UnitRepository unitRepository, UserRepository userRepository, RecipeRepository recipeRepository) {
//    this.recipeService = recipeService;
//    this.shoppingListRepository = shoppingListRepository;
//    this.shoppingListItemRepository = shoppingListItemRepository;
//    this.userService = userService;
//    this.userRepository = userRepository;
//    this.ingredientRepository = ingredientRepository;
//    this.unitRepository = unitRepository;
//    this.recipeRepository = recipeRepository;
//  }
//
//  public List<ShoppingListResponseDTO> getShoppingListsByUser() {
//    User user = getAuthenticatedUser();
//    return shoppingListRepository.findByUserId(user.getId())
//            .stream()
//            .map(ShoppingListMapper::toDTO)
//            .toList();
//  }
//
//  public ShoppingListResponseDTO createShoppingList(ShoppingListRequestDTO requestDTO) {
//    User user = getAuthenticatedUser();
//
//    ShoppingList shoppingList = new ShoppingList(user, LocalDateTime.now(), new ArrayList<>());
//
//    for (ShoppingListItemRequestDTO itemDTO : requestDTO.items()) {
//      Ingredient ingredient = ingredientRepository.findById(itemDTO.ingredientId())
//              .orElseThrow(() -> new IngredientNotFoundException(itemDTO.ingredientId()));
//      Unit unit = unitRepository.findById(itemDTO.unitId())
//              .orElseThrow(() -> new UnitNotFoundException(itemDTO.unitId()));
//
//      ShoppingListItem item = new ShoppingListItem(shoppingList, ingredient, itemDTO.quantity(), unit);
//      shoppingList.getItems().add(item);
//    }
//
//    ShoppingList saved = shoppingListRepository.save(shoppingList);
//    return ShoppingListMapper.toDTO(saved);
//  }
//
//  public ShoppingListResponseDTO createShoppingListFromRecipes(List<Long> recipeIds) {
//    User user = getAuthenticatedUser();
//    ShoppingList shoppingList = new ShoppingList(user, LocalDateTime.now(), new ArrayList<>());
//
//    Map<String, ShoppingListItem> combinedItems = new HashMap<>();
//
//    for (Long recipeId : recipeIds) {
//      List<RecipeIngredient> recipeIngredients = recipeService.getRecipeIngredientsByRecipeId(recipeId);
//
//      for (RecipeIngredient ri : recipeIngredients) {
//        Ingredient ingredient = ri.getIngredient();
//        Unit unit = ri.getUnit();
//        Double quantity = ri.getQuantity();
//
//        String key = ingredient.getName() + "-" + (unit != null ? unit.getId() : "none");
//
//        if (combinedItems.containsKey(key)) {
//          ShoppingListItem existing = combinedItems.get(key);
//          existing.setQuantity(existing.getQuantity() + quantity);
//        } else {
//          ShoppingListItem item = new ShoppingListItem(shoppingList, ingredient, quantity, unit);
//          combinedItems.put(key, item);
//        }
//      }
//    }
//
//    shoppingList.setItems(new ArrayList<>(combinedItems.values()));
//    ShoppingList saved = shoppingListRepository.save(shoppingList);
//    return ShoppingListMapper.toDTO(saved);
//  }
//
//  public List<ShoppingList> getAllShoppingLists() {
//    return shoppingListRepository.findAll();
//  }
//
//  public void deleteShoppingListById(long id) {
//    shoppingListRepository.deleteById(id);
//  }
//
//  public ShoppingList createShoppingListFromRecipes(Long userId, List<Long> recipeIds) {
//    User user = userRepository.findById(userId)
//            .orElseThrow(() -> new IllegalArgumentException("Vartotojas nerastas su ID: " + userId));
//
//    ShoppingList shoppingList = new ShoppingList();
//    shoppingList.setUser(user);
//    shoppingList.setCreatedAt(LocalDateTime.now());
//    shoppingList = shoppingListRepository.save(shoppingList); // užtikrinam, kad objektas būtų išsaugotas ir su ID
//
//    List<Recipe> recipes = recipeRepository.findAllById(recipeIds);
//
//    for (Recipe recipe : recipes) {
//      for (RecipeIngredient ri : recipe.getRecipeIngredients()) {
//        ShoppingListItem item = new ShoppingListItem();
//        item.setShoppingList(shoppingList);
//        item.setIngredient(ri.getIngredient());
//        item.setQuantity(ri.getQuantity());
//        item.setUnit(ri.getUnit());
//
//        shoppingListItemRepository.save(item);
//      }
//    }
//    return shoppingList;
//  }
//
//  public User getAuthenticatedUser() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    if (authentication == null || !authentication.isAuthenticated()) {
//      throw new UserNotAuthenticatedException("");
//    }
//    String username = authentication.getName();
//    return userService.findUserByUsername(username)
//            .orElseThrow(() -> new UserNotFoundException(username));
//  }
//}

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
    User user = getAuthenticatedUser();// ✅ Automatiškai gauname vartotoją iš Spring Security

    ShoppingList shoppingList = new ShoppingList(user, LocalDateTime.now(), new ArrayList<>());
    shoppingList.setItems(new ArrayList<>());

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


}


