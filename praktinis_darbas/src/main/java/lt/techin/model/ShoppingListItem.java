package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "shopping_list_items")
public class ShoppingListItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "shopping_list_id", nullable = false)
  private ShoppingList shoppingList;

  @ManyToOne
  @JoinColumn(name = "ingredient_id", nullable = false)
  private Ingredient ingredient;

  @NotNull
  @Min(value = 1, message = "Quantity must be greater than zero.")
  @Column(nullable = false)
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "unit_id", nullable = false)
  private Unit unit;

  public ShoppingListItem(ShoppingList shoppingList, Ingredient ingredient, int quantity, Unit unit) {
    this.shoppingList = shoppingList;
    this.ingredient = ingredient;
    this.quantity = quantity;
    this.unit = unit;
  }

  public ShoppingListItem() {
  }

  public Long getId() {
    return id;
  }

  public ShoppingList getShoppingList() {
    return shoppingList;
  }

  public void setShoppingList(ShoppingList shoppingList) {
    this.shoppingList = shoppingList;
  }

  public Ingredient getIngredient() {
    return ingredient;
  }

  public void setIngredient(Ingredient ingredient) {
    this.ingredient = ingredient;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }
}
