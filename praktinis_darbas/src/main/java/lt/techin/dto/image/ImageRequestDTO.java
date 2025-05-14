package lt.techin.dto.image;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImageRequestDTO(
        @NotBlank
        String imageName,

        @NotNull
        String contentType,

        @NotNull
        byte[] imageData
) {
}
