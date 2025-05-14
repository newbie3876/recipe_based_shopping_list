package lt.techin.init;

import lt.techin.model.Unit;
import lt.techin.repository.UnitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitInitializer implements CommandLineRunner {
  private final UnitRepository unitRepository;

  public UnitInitializer(UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
  }

  @Override
  public void run(String... args) {
    List<String> defaultCategories = List.of(
            "g", "kg", "l", "ml", "pc."
    );

    for (String unitName : defaultCategories) {
      if (unitRepository.findByName(unitName).isEmpty()) {
        unitRepository.save(new Unit(unitName));
      }
    }
  }
}
