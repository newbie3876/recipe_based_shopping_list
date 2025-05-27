package lt.techin.exceptions;

public class RecipeNotFoundException extends RuntimeException {
  public RecipeNotFoundException(Long id) {
    super("Receptas nerastas su ID: " + id);
  }
}
