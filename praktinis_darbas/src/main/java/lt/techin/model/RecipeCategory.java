package lt.techin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
  @JsonIgnoreProperties("recipeCategory")
  private final List<Recipe> recipes = new ArrayList<>();

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
}