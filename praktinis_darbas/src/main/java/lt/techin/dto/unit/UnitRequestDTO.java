package lt.techin.dto.unit;

import jakarta.validation.constraints.NotNull;


public record UnitRequestDTO(
        //@NotNull
        Long id,

        @NotNull
        String unitName
) {
}
