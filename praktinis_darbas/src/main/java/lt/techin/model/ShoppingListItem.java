package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "shopping_list_items")
public class ShoppingListItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shopping_list_id", nullable = false)
  private ShoppingList shoppingList;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ingredient_id", nullable = false)
  private Ingredient ingredient;

  @DecimalMin(value = "1.0", message = "Kiekis turi būti ne mažesnis nei 1.")
  private Double quantity;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_id", nullable = false)
  private Unit unit;

  public ShoppingListItem(ShoppingList shoppingList, Ingredient ingredient, Double quantity, Unit unit) {
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

  public Ingredient getIngredient() {
    return ingredient;
  }

  public void setIngredient(Ingredient ingredient) {
    this.ingredient = ingredient;
  }

  public Double getQuantity() {
    return quantity;
  }

  public Unit getUnit() {
    return unit;
  }
  

  public void setShoppingList(ShoppingList shoppingList) {
    this.shoppingList = shoppingList;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }
}