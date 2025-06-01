//package lt.techin.service;
//
//import jakarta.transaction.Transactional;
//import lt.techin.dto.ingredient.IngredientMapper;
//import lt.techin.dto.ingredient.IngredientResponseDTO;
//import lt.techin.exceptions.IngredientNotFoundException;
//import lt.techin.model.Ingredient;
//import lt.techin.model.User;
//import lt.techin.repository.IngredientRepository;
//import lt.techin.repository.UserRepository;
//import lt.techin.security.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class IngredientService {
//  private final IngredientRepository ingredientRepository;
//  private final UserRepository userRepository;
//
//  @Autowired
//  public IngredientService(IngredientRepository ingredientRepository, UserRepository userRepository) {
//    this.ingredientRepository = ingredientRepository;
//    this.userRepository = userRepository;
//  }
//
//  public List<IngredientResponseDTO> getAllIngredientDTO() {
//    List<Ingredient> allIngredients = ingredientRepository.findAll();
//    return IngredientMapper.toListDTO(allIngredients);
//  }
//
//  public IngredientResponseDTO getIngredientDTOById(Long id) {
//    Ingredient match = ingredientRepository.findById(id)
//            .orElseThrow(() -> new IngredientNotFoundException(id));
//    return IngredientMapper.toDTO(match);
//  }
//
//  public Ingredient getIngredientById(Long id) {
//    return ingredientRepository.findById(id)
//            .orElseThrow(() -> new IngredientNotFoundException(id));
//  }
//
//  public boolean existsIngredientByName(String name) {
//    return ingredientRepository.existsByName(name);
//  }
//
//  @Transactional
//  public IngredientResponseDTO saveIngredient(Ingredient ingredient) {
//    if (ingredient.getName() == null || ingredient.getName().isBlank()) {
//      throw new IllegalArgumentException("Ingrediento pavadinimas negali būti tuščias");
//    }
//    if (ingredient.getIngredientCategory() == null) {
//      throw new IllegalArgumentException("Ingrediento kategorija turi būti nurodyta");
//    }
//
//    if (ingredient.getUnit() == null) {
//      throw new IllegalArgumentException("Ingrediento matavimo vienetas turi būti nurodytas");
//    }
//
//    String username = SecurityUtils.getCurrentAuthenticatedUsername();
//    User currentUser = userRepository.findByUsername(username)
//            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vartotojas nerastas"));
//    ingredient.setUser(currentUser);
//
//    Ingredient savedIngredient = ingredientRepository.save(ingredient);
//    return IngredientMapper.toDTO(savedIngredient);
//  }
//
//  @Transactional
//  public void deleteIngredientById(Long id) {
//    if (!ingredientRepository.existsById(id)) {
//      throw new IngredientNotFoundException(id);
//    }
//    ingredientRepository.deleteById(id);
//  }
//}

package lt.techin.service;

import lt.techin.model.Ingredient;
import lt.techin.model.User;
import lt.techin.repository.IngredientRepository;
import lt.techin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

  private final IngredientRepository ingredientRepository;
  private final UserRepository userRepository;

  @Autowired
  public IngredientService(IngredientRepository ingredientRepository, UserRepository userRepository) {
    this.ingredientRepository = ingredientRepository;
    this.userRepository = userRepository;
  }

  public Optional<Ingredient> findIngredientById(Long id) {
    return this.ingredientRepository.findById(id);
  }

  public boolean existsIngredientByName(String name) {
    return this.ingredientRepository.existsByName(name);
  }

  public void deleteIngredientById(Long id) {
    this.ingredientRepository.deleteById(id);
  }

  public List<Ingredient> findAllIngredients() {
    return this.ingredientRepository.findAll();
  }

  public Ingredient saveIngredient(Ingredient newIngredient) {
    return this.ingredientRepository.save(newIngredient);
  }

  public List<Ingredient> findIngredientForCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Jwt jwt = (Jwt) authentication.getPrincipal();
    String username = jwt.getSubject();

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return ingredientRepository.findByUser(user);
  }
}
