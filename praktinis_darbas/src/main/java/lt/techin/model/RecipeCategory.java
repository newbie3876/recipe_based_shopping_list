package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "recipe_categories")
public class RecipeCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Column(nullable = false, length = 100, unique = true)
  private String name;

  public RecipeCategory(String name) {
    this.name = name;
  }

  public RecipeCategory() {
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
