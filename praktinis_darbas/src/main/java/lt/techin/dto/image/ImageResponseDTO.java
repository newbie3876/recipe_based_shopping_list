package lt.techin.dto.image;

public record ImageResponseDTO(
        long id,
        long userId,
        String imageName,
        String contentType,
        byte[] imageData
) {


}
