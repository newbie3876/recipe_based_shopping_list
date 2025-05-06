package lt.techin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "recipes")
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Column(nullable = false, length = 100)
  private String name;

  @NotNull
  @Column(nullable = false, length = 100)
  private String description;

  @NotNull
  @Column(nullable = false, length = 255)
  private String link;

  @NotNull
  @Column(nullable = false)
  private int portions;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "recipe_category_id", nullable = false)
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

  public long getId() {
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
