package lt.techin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 250)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ingredient_category_id")
  private IngredientCategory ingredientCategory;

  @JsonIgnore
  @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ShoppingListItem> shoppingListItems = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY) // Užtikrina teisingą ryšį
  @JoinColumn(name = "user_id", nullable = false) // Aiškiai nurodo DB stulpelį
  private User user;

  @ManyToOne
  @JoinColumn(name = "unit_id")
  private Unit unit;

  public Ingredient(String name) {
    this.name = name;
  }

  public Ingredient() {
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public IngredientCategory getIngredientCategory() {
    return ingredientCategory;
  }

  public void setIngredientCategory(IngredientCategory ingredientCategory) {
    this.ingredientCategory = ingredientCategory;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public Unit getUnit() {
    return unit;
  }

  public List<ShoppingListItem> getShoppingListItems() {
    return shoppingListItems;
  }

  public void setShoppingListItems(List<ShoppingListItem> shoppingListItems) {
    this.shoppingListItems = shoppingListItems;
  }
}
