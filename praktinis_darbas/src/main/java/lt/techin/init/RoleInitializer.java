package lt.techin.init;

import lt.techin.model.Role;
import lt.techin.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleInitializer implements CommandLineRunner {
  private final RoleRepository roleRepository;

  public RoleInitializer(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public void run(String... args) {
    List<String> defaultCategories = List.of(
            "ROLE_USER", "ROLE_ADMIN"
    );

    for (String roleName : defaultCategories) {
      if (roleRepository.findByName(roleName).isEmpty()) {
        roleRepository.save(new Role(roleName));
      }
    }
  }
}
