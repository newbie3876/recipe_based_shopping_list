package lt.techin.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lt.techin.dto.image.ImageMapper;
import lt.techin.dto.image.ImageRequestDTO;
import lt.techin.dto.image.ImageResponseDTO;
import lt.techin.model.Image;
import lt.techin.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api")
public class ImageController {

  private final ImageService imageService;

  @Autowired
  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @GetMapping("/images")
  public ResponseEntity<List<ImageResponseDTO>> getUserImages() {
    List<Image> images = this.imageService.findImagesForCurrentUser();
    return ResponseEntity.ok(ImageMapper.toListDTO(images));
  }

  @GetMapping("/images/{id}")
  public ResponseEntity<ImageResponseDTO> getImage(@Valid @PathVariable @Min(1) long id) {
    Optional<Image> image = this.imageService.findImageById(id);

    if (image.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(ImageMapper.toDTO(image.get()));
  }

  @PostMapping("/images")
  public ResponseEntity<Object> saveImage(@Valid @RequestBody ImageRequestDTO imageRequestDTO) {

    Image savedImage = this.imageService.saveImage(imageRequestDTO);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedImage.getId())
                            .toUri())
            .body(ImageMapper.toDTO(savedImage));
  }

  @DeleteMapping("/images/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable long id) {
    Optional<Image> userOptional = this.imageService.findImageById(id);
    if (userOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }


    this.imageService.deleteImageById(id);
    return ResponseEntity.noContent().build();
  }
}
