package lt.techin.model;

import jakarta.persistence.*;

@Entity
@Table(name = " user_roles")
public class UserRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  public UserRole(User user, Role role) {
    this.user = user;
    this.role = role;
  }

  public UserRole() {
  }

  public long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
