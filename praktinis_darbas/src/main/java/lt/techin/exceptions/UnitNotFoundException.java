package lt.techin.exceptions;

public class UnitNotFoundException extends RuntimeException {
  public UnitNotFoundException(Long id) {
    super("Matavimo vienetas nerastas su ID: " + id);
  }
}
