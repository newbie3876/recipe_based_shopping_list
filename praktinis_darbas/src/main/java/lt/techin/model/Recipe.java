package lt.techin.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "recipes")
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(max = 150)
  @Column(nullable = false, length = 150)
  private String name;

  @NotNull
  @Size(max = 350)
  @Column(nullable = false, length = 350)
  private String description;

  @NotNull
  @Size(max = 255)
  @Column(nullable = false, length = 255)
  private String link;

  @Column(nullable = false)
  @Min(value = 0, message = "Portions cannot be a negative.")
  private int portions;

  @NotNull
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference
  private User user;

  @NotNull
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "recipe_category_id", nullable = false)
  @JsonBackReference
  private RecipeCategory recipeCategory;

  public Recipe(String name, String description, String link, int portions, User user, RecipeCategory recipeCategory) {
    this.name = name;
    this.description = description;
    this.link = link;
    this.portions = portions;
    this.user = user;
    this.recipeCategory = recipeCategory;
  }

  public Recipe() {
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public int getPortions() {
    return portions;
  }

  public void setPortions(int portions) {
    this.portions = portions;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public RecipeCategory getRecipeCategory() {
    return recipeCategory;
  }

  public void setRecipeCategory(RecipeCategory recipeCategory) {
    this.recipeCategory = recipeCategory;
  }
}
