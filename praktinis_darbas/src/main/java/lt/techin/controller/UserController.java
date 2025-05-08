package lt.techin.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lt.techin.dto.user.AdminRequestDTO;
import lt.techin.dto.user.UserMapper;
import lt.techin.dto.user.UserRegistrationDTO;
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


  @PostMapping("/admin")
  public ResponseEntity<Object> saveAdmin(@Valid @RequestBody AdminRequestDTO adminRequestDTO) {

    if (this.userService.existsUserByUsername(adminRequestDTO.username())) {
      Map<String, String> response = new HashMap<>();
      response.put("username", "Already exists");

      return ResponseEntity.badRequest().body(response);
    }

    String encodedPassword = passwordEncoder.encode(adminRequestDTO.password());

    User user = UserMapper.toAdmin(adminRequestDTO, encodedPassword);
    User savedUser = userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            // Use DTO Mapping!
            .body(UserMapper.toDTO(savedUser));
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@Valid @RequestBody UserRegistrationDTO dto) {
    if (userService.existsUserByUsername(dto.username())) {
      return ResponseEntity.badRequest().body(Map.of("username", "Already exists"));
    }

    String encodedPassword = passwordEncoder.encode(dto.password());
    User user = UserMapper.toUser(dto, encodedPassword);
    User saved = userService.saveUser(user);

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri()
    ).body(UserMapper.toDTO(saved));
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
