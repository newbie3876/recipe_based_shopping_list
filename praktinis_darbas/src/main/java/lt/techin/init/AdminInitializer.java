package lt.techin.init;

import lt.techin.model.Role;
import lt.techin.model.User;
import lt.techin.repository.RoleRepository;
import lt.techin.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdminInitializer implements CommandLineRunner {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public AdminInitializer(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }


  @Override
  public void run(String... args) {
    String adminUsername = "admin";

    if (userRepository.findByUsername(adminUsername).isEmpty()) {
      String encodedPassword = passwordEncoder.encode("admin");

      Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
      Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");

      if (userRole.isPresent() && adminRole.isPresent()) {
        User adminUser = new User(
                encodedPassword,
                adminUsername,
                List.of(userRole.get(), adminRole.get())
        );

        userRepository.save(adminUser);
        System.out.println("Admin user created.");
      } else {
        System.err.println("Required roles not found in DB.");
      }
    }
  }
}
