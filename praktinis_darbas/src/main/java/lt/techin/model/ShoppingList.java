package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_lists")
public class ShoppingList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) // Užtikrina teisingą ryšį
  @JoinColumn(name = "user_id", nullable = false) // Aiškiai nurodo DB stulpelį
  private User user;

  @Column(name = "created_at", nullable = false)
  @PastOrPresent(message = "Creation date cannot be in the future!")
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    if (this.createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }

  @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ShoppingListItem> items = new ArrayList<>();

  public ShoppingList() {
  }

  public ShoppingList(User user, LocalDateTime createdAt, List<ShoppingListItem> items) {
    this.user = user;
    this.createdAt = createdAt;
    this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public List<ShoppingListItem> getItems() {
    return items;
  }

  public void setItems(List<ShoppingListItem> items) {
    this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
  }
}
