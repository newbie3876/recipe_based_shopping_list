package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "recipes_ingredients")
public class RecipeIngredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Column(nullable = false)
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "unit_id", nullable = false)
  private Unit unit;

  @ManyToOne
  @JoinColumn(name = "ingredient_id", nullable = false)
  private Ingredient ingredient;

  @ManyToOne
  @JoinColumn(name = "recipe_id", nullable = false)
  private Recipe recipe;

  public RecipeIngredient(int quantity, Unit unit, Ingredient ingredient, Recipe recipe) {
    this.quantity = quantity;
    this.unit = unit;
    this.ingredient = ingredient;
    this.recipe = recipe;
  }

  public RecipeIngredient() {
  }

  public long getId() {
    return id;
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

  public Ingredient getIngredient() {
    return ingredient;
  }

  public void setIngredient(Ingredient ingredient) {
    this.ingredient = ingredient;
  }

  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }
}
