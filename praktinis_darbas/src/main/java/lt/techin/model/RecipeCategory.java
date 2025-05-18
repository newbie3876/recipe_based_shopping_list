package lt.techin.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe_categories")
public class RecipeCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 150)
  private String name;

  @OneToMany(mappedBy = "recipeCategory")
  private List<Recipe> recipes = new ArrayList<>();

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