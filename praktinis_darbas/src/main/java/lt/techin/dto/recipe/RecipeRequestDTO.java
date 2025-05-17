package lt.techin.dto.recipe;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RecipeRequestDTO {
  @NotBlank
  @Size(max = 150)
  private String name;

  @Size(max = 350)
  private String description;

  @Min(value = 1, message = "Turi būti bent viena porcija.")
  private int portions;

  @Size(max = 255)
  private String link;

  private Long categoryId;

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

  public int getPortions() {
    return portions;
  }

  public void setPortions(int portions) {
    this.portions = portions;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
}

