package lt.techin.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "shopping_list_items")
public class ShoppingListItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shopping_list_id", nullable = false)
  private ShoppingList shoppingList;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ingredient_id", nullable = false)
  private Ingredient ingredient;


  //@Min(value = 1, message = "Quantity must be greater than zero.")
  @Column(nullable = false)
  private BigDecimal quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_id", nullable = false)
  private Unit unit;

  public ShoppingListItem(ShoppingList shoppingList, Ingredient ingredient, BigDecimal quantity, Unit unit) {
    this.shoppingList = shoppingList;
    this.ingredient = ingredient;
    this.quantity = quantity;
    this.unit = unit;
  }

  public ShoppingListItem() {
  }

  public ShoppingListItem(Object o, ShoppingList shoppingList, Ingredient ingredient, BigDecimal quantity, Unit unit) {
  }

  public ShoppingListItem(String s, BigDecimal quantity, Long aLong) {
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

  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }
}
