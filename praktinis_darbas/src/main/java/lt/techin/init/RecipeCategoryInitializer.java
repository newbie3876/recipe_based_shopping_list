package lt.techin.init;

import lt.techin.model.RecipeCategory;
import lt.techin.repository.RecipeCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeCategoryInitializer implements CommandLineRunner {

  private final RecipeCategoryRepository categoryRepository;

  public RecipeCategoryInitializer(RecipeCategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void run(String... args) {
    List<String> defaultCategories = List.of(
            "Desertas", "Gėrimas", "Salotos", "Užkandis", "Sriuba", "Pagrindinis patiekalas"
    );

    for (String categoryName : defaultCategories) {
      if (categoryRepository.findByName(categoryName).isEmpty()) {
        categoryRepository.save(new RecipeCategory(categoryName));
      }
    }
  }
}
