package lt.techin.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lt.techin.dto.user.UserMapper;
import lt.techin.dto.user.UserResponseDTO;
import lt.techin.model.User;
import lt.techin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/users")
  // Use UserRequestDTO!
  public ResponseEntity<Object> saveUser(@RequestBody User user) {

    if (this.userService.existsUserByUsername(user.getUsername())) {
      Map<String, String> response = new HashMap<>();
      response.put("username", "Already exists");

      return ResponseEntity.badRequest().body(response);
    }

    // Šifruojame slaptažodį prieš saugant duomenų bazėje
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));

    // Use DTO mapping!
    User savedUser = this.userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            // Use DTO Mapping!
            .body(savedUser);
  }


  @GetMapping("/users")
  public ResponseEntity<List<UserResponseDTO>> getUsers() {
    List<User> users = this.userService.findAllUsers();

    return ResponseEntity.ok(UserMapper.toListDTO(users));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponseDTO> getUser(@Valid @PathVariable @Min(1) long id) {
    Optional<User> user = this.userService.findUserById(id);

    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(UserMapper.toDTO(user.get()));

  }


  @DeleteMapping("/users/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable long id) {
    Optional<User> userOptional = this.userService.findUserById(id);
    if (userOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    
    this.userService.deleteUserByID(id);
    return ResponseEntity.noContent().build();
  }
}
