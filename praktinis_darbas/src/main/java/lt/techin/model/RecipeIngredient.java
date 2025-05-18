package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Min(value = 1, message = "Kiekis negali būti mažesnis nei 1.")
  private int quantity;

  @Column(nullable = false, length = 255)
  private String name;

  public RecipeIngredient(int quantity, String name) {
    this.quantity = quantity;
    this.name = name;
  }

  public RecipeIngredient() {
  }

  public Long getId() {
    return id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
