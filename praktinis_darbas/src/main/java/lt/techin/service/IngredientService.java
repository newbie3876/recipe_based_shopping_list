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

