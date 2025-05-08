package lt.techin.dto.user;

import lt.techin.model.Role;
import lt.techin.model.User;

import java.util.List;

public class UserMapper {

  public static User toAdmin(AdminRequestDTO adminRequestDTO, String encodedPassword) {
    return new User(
            encodedPassword,
            adminRequestDTO.username(),
            adminRequestDTO.roles()
    );
  }

  public static User toUser(UserRegistrationDTO userRegistrationDTO, String encodedPassword) {
    Role defaultRole = new Role();
    defaultRole.setId(1L);

    return new User(encodedPassword, userRegistrationDTO.username(), List.of(defaultRole));
  }

  public static UserResponseDTO toDTO(User user) {
    return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getRoles().stream()
                    .map(r -> new UserRolesDTO(r.getId(), r.getName()))
                    .toList()
    );
  }

  public static List<UserResponseDTO> toListDTO(List<User> users) {
    return users.stream()
            .map(u -> new UserResponseDTO(
                    u.getId(),
                    u.getUsername(),
                    u.getRoles().stream().map(r -> new UserRolesDTO(r.getId(), r.getName()))
                            .toList()
            ))
            .toList();
  }
}
