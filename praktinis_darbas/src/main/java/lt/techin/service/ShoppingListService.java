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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    return shoppingListRepository.findByUserId(userId).stream()
            .map(ShoppingListMapper::toDTO)
            .collect(Collectors.toList());
  }

  public ShoppingListResponseDTO createShoppingList(ShoppingListRequestDTO requestDTO) {


    // 1. Surandame vartotoją pagal userId (jei neegzistuoja, išmetame klaidą)
    User user = userRepository.findById(requestDTO.userId())
            .orElseThrow(() -> new RuntimeException("User not found"));

    // 2. Sukuriame naują ShoppingList objektą su vartotoju
    ShoppingList shoppingList = new ShoppingList(user, LocalDateTime.now(), new ArrayList<>());
    shoppingList.setItems(new ArrayList<>());

    // 3. Pridedame produktus į pirkinių sąrašą
    for (ShoppingListItemRequestDTO itemDTO : requestDTO.items()) {
      Ingredient ingredient = ingredientRepository.findById(itemDTO.ingredientId())
              .orElseThrow(() -> new RuntimeException("Ingredient not found"));

      Unit unit = unitRepository.findById(itemDTO.unitId())
              .orElseThrow(() -> new RuntimeException("Unit not found"));

      ShoppingListItem item = new ShoppingListItem(shoppingList, ingredient, itemDTO.quantity(), unit);
      shoppingList.getItems().add(item);
    }

    // 4. Išsaugome sąrašą į duomenų bazę
    ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);

    return ShoppingListMapper.toDTO(savedShoppingList);
  }


  public Optional<ShoppingList> findShoppingListById(Long id) {
    return shoppingListRepository.findById(id);
  }

  public ShoppingList saveShoppingList(ShoppingListRequestDTO shoppingListRequestDTO) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Jwt jwt = (Jwt) authentication.getPrincipal();
    String username = jwt.getSubject();

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    ShoppingList shoppingList = ShoppingListMapper.toShoppingList(shoppingListRequestDTO, user, ingredientRepository, unitRepository);

    return shoppingListRepository.save(shoppingList);
  }

  public void deleteShoppingListById(long id) {
    shoppingListRepository.deleteById(id);
  }

  public List<ShoppingList> findShoppingListsForCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Jwt jwt = (Jwt) authentication.getPrincipal();
    String username = jwt.getSubject();

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return shoppingListRepository.findByUser(user);
  }
}
