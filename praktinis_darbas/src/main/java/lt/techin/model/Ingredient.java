package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ingredients")
public class Ingredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(max = 250)
  @Column(nullable = false, length = 250)
  private String name;

  @ManyToOne
  @JoinColumn(name = "ingredient_category_id", nullable = false)
  private IngredientCategory ingredientCategory;

  public Ingredient(String name, IngredientCategory ingredientCategory) {
    this.name = name;
    this.ingredientCategory = ingredientCategory;
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
}
