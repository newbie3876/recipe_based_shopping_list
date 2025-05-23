package lt.techin.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String name) {
    super("Vartotojas nerastas: " + name);
  }
}
