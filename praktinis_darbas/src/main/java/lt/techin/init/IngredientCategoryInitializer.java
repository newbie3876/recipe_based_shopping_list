package lt.techin.init;

import lt.techin.model.IngredientCategory;
import lt.techin.repository.IngredientCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientCategoryInitializer implements CommandLineRunner {
  private final IngredientCategoryRepository ingredientCategoryRepository;

  public IngredientCategoryInitializer(IngredientCategoryRepository ingredientCategoryRepository) {
    this.ingredientCategoryRepository = ingredientCategoryRepository;
  }

  @Override
  public void run(String... args) {
    List<String> defaultCategories = List.of(
            "Pienas ir jo gaminiai", "Mėsa, žuvis ir kiaušiniai", "Bulvės, ankštiniai augalai ir riešutai", "Daržovės", "Vaisiai", "Duona, makaronai, grūdai, cukrus ir saldainiai", "Riebalai, aliejus ir sviestas"
    );

    for (String categoryName : defaultCategories) {
      if (ingredientCategoryRepository.findByName(categoryName).isEmpty()) {
        ingredientCategoryRepository.save(new IngredientCategory(categoryName));
      }
    }
  }
}
