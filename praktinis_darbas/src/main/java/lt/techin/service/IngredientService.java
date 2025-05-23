package lt.techin.service;

import jakarta.transaction.Transactional;
import lt.techin.dto.ingredient.IngredientMapper;
import lt.techin.dto.ingredient.IngredientResponseDTO;
import lt.techin.exceptions.IngredientNotFoundException;
import lt.techin.model.Ingredient;
import lt.techin.model.User;
import lt.techin.repository.IngredientRepository;
import lt.techin.repository.UserRepository;
import lt.techin.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IngredientService {
  private final IngredientRepository ingredientRepository;
  private final UserRepository userRepository;

  @Autowired
  public IngredientService(IngredientRepository ingredientRepository, UserRepository userRepository) {
    this.ingredientRepository = ingredientRepository;
    this.userRepository = userRepository;
  }

  public List<IngredientResponseDTO> getAllIngredientDTO() {
    List<Ingredient> allIngredients = ingredientRepository.findAll();
    return IngredientMapper.toListDTO(allIngredients);
  }

  public IngredientResponseDTO getIngredientDTOById(Long id) {
    Ingredient match = ingredientRepository.findById(id)
            .orElseThrow(() -> new IngredientNotFoundException(id));
    return IngredientMapper.toDTO(match);
  }

  public Ingredient getIngredientById(Long id) {
    return ingredientRepository.findById(id)
            .orElseThrow(() -> new IngredientNotFoundException(id));
  }

  public boolean existsIngredientByName(String name) {
    return ingredientRepository.existsByName(name);
  }

  @Transactional
  public IngredientResponseDTO saveIngredient(Ingredient ingredient) {
    if (ingredient.getName() == null || ingredient.getName().isBlank()) {
      throw new IllegalArgumentException("Ingrediento pavadinimas negali būti tuščias");
    }
    if (ingredient.getIngredientCategory() == null) {
      throw new IllegalArgumentException("Ingrediento kategorija turi būti nurodyta");
    }

    if (ingredient.getUnit() == null) {
      throw new IllegalArgumentException("Ingrediento matavimo vienetas turi būti nurodytas");
    }

    String username = SecurityUtils.getCurrentAuthenticatedUsername();
    User currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Vartotojas nerastas"));
    ingredient.setUser(currentUser);

    Ingredient savedIngredient = ingredientRepository.save(ingredient);
    return IngredientMapper.toDTO(savedIngredient);
  }

  @Transactional
  public void deleteIngredientById(Long id) {
    if (!ingredientRepository.existsById(id)) {
      throw new IngredientNotFoundException(id);
    }
    ingredientRepository.deleteById(id);
  }
}

