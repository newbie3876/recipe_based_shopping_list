package lt.techin.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.techin.model.Role;

import java.util.List;

public record AdminRequestDTO(

        @NotNull
        @Size(min = 5, max = 100)
        String password,

        @NotNull
        @Size(min = 5, max = 100)
        String username,

        @NotNull
        List<Role> roles
) {
}
