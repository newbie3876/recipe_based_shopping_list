package lt.techin.dto.user;

import java.util.List;

public record UserResponseDTO(
        long id,
        String username,
        List<UserRolesDTO> roles
) {
}
