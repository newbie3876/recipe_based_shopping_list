package lt.techin.exceptions;

public class UserNotAuthenticatedException extends RuntimeException {
  public UserNotAuthenticatedException(String message) {
    super("Vartotojas neautentifikuotas: " + message);
  }
}
