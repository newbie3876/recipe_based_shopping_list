package lt.techin.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;

  public Role(String name) {
    this.name = name;
  }

  public Role() {
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // Sakome Spring Security, kuris laukas nustato rolės pavadinimą
  @Override
  public String getAuthority() {
    return this.name;
  }
}
