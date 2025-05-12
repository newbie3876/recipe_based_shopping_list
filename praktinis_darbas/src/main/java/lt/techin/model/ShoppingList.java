package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Entity
@Table(name = "shopping_lists")
public class ShoppingList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @NotNull
  @Column(nullable = false, name = "created_at")
  @PastOrPresent(message = "Creation date cannot be in the future!")
  private LocalDate createdAt;

  public ShoppingList(User user, LocalDate createdAt) {
    this.user = user;
    this.createdAt = createdAt;
  }

  public ShoppingList() {
  }

  public Long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }
}
