package lt.techin.dto.image;

import lt.techin.model.Image;
import lt.techin.model.User;

import java.util.List;

public class ImageMapper {

  public static Image toImage(ImageRequestDTO imageRequestDTO, User user) {
    return new Image(
            imageRequestDTO.imageName(),
            imageRequestDTO.contentType(),
            imageRequestDTO.imageData(),
            user
    );
  }

  public static ImageResponseDTO toDTO(Image image) {
    return new ImageResponseDTO(
            image.getId(),
            image.getUser().getId(),
            image.getImageName(),
            image.getContentType(),
            image.getImageData()

    );
  }

  public static List<ImageResponseDTO> toListDTO(List<Image> images) {
    return images.stream()
            .map(i -> new ImageResponseDTO(
                    i.getId(),
                    i.getUser().getId(),
                    i.getImageName(),
                    i.getContentType(),
                    i.getImageData()
            ))
            .toList();
  }

}
