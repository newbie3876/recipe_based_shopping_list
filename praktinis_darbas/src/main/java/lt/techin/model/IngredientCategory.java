package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "ingredient_categories")
public class IngredientCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(max = 150)
  @Column(nullable = false, length = 150)
  private String name;

  @OneToMany(mappedBy = "ingredientCategory")
  private List<Ingredient> ingredients;

  public IngredientCategory(String name, List<Ingredient> ingredients) {
    this.name = name;
    this.ingredients = ingredients;
  }

  public IngredientCategory(String name) {
    this.name = name;
  }

  public IngredientCategory() {
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

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }
}
