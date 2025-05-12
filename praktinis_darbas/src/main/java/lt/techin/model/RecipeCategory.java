package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "recipe_categories")
public class RecipeCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 150)
  @NotBlank(message = "Category name cannot be blank.")
  @Column(nullable = false, length = 150, unique = true)
  private String name;

  @OneToMany(mappedBy = "recipeCategory")
  private List<Recipe> recipes;

  public RecipeCategory(String name, List<Recipe> recipes) {
    this.name = name;
    this.recipes = recipes;
  }

  public RecipeCategory(String name) {
    this.name = name;
  }

  public RecipeCategory() {
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

  public List<Recipe> getRecipes() {
    return recipes;
  }

  public void setRecipes(List<Recipe> recipes) {
    this.recipes = recipes;
  }
}
