package lt.techin.exceptions;

public class IngredientNotFoundException extends RuntimeException {
  public IngredientNotFoundException(Long id) {
    super("Ingredientas nerastas su ID: " + id);
  }
}
