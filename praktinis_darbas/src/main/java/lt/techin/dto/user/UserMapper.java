package lt.techin.dto.user;

import lt.techin.model.User;

import java.util.List;

public class UserMapper {

  public static User toUser(UserRequestDTO userRequestDTO, String encodedPassword) {
    return new User(
            encodedPassword,
            userRequestDTO.username(),
            userRequestDTO.roles()
    );
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
