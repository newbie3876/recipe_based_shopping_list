package lt.techin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 150)
  private String name;

  @Column(nullable = false, length = 350)
  private String description;

  @Column(length = 255)
  private String link;

  private int portions;

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "recipe_category_id")
  @JsonIgnoreProperties("recipes")
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
