package lt.techin.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredient_categories")
public class IngredientCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 255)
  private String name;

  @JsonManagedReference
  @OneToMany(mappedBy = "ingredientCategory", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Ingredient> ingredients = new ArrayList<>();

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
}
