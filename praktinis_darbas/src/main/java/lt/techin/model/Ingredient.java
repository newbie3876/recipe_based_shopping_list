package lt.techin.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ingredients")
public class Ingredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "ingredient_category_id", nullable = false)
  private IngredientCategory ingredientCategory;

  @OneToMany(mappedBy = "ingredient")
  private List<ShoppingListItem> shoppingListItems;

  public Ingredient(String name) {
    this.name = name;
  }
  
  public Ingredient(String name, IngredientCategory ingredientCategory, List<ShoppingListItem> shoppingListItems) {
    this.name = name;
    this.ingredientCategory = ingredientCategory;
    this.shoppingListItems = shoppingListItems;
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

  public List<ShoppingListItem> getShoppingListItems() {
    return shoppingListItems;
  }

  public void setShoppingListItems(List<ShoppingListItem> shoppingListItems) {
    this.shoppingListItems = shoppingListItems;
  }
}
