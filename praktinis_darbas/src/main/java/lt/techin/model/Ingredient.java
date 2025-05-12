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
  private List<ShoppingListItem> shopping_list_items;

  public Ingredient(String name) {
    this.name = name;
  }

  public Ingredient(String name, IngredientCategory ingredientCategory, List<ShoppingListItem> shopping_list_items) {
    this.name = name;
    this.ingredientCategory = ingredientCategory;
    this.shopping_list_items = shopping_list_items;
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

  public List<ShoppingListItem> getShopping_list_items() {
    return shopping_list_items;
  }

  public void setShopping_list_items(List<ShoppingListItem> shopping_list_items) {
    this.shopping_list_items = shopping_list_items;
  }
}
