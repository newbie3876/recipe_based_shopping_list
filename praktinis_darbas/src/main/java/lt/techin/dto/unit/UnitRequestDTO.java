package lt.techin.dto.unit;

import jakarta.validation.constraints.NotNull;

public record UnitRequestDTO(
        Long id,
        @NotNull
        String unitName
) {
}
