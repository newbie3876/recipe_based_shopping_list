package lt.techin.service;

import lt.techin.dto.image.ImageMapper;
import lt.techin.dto.image.ImageRequestDTO;
import lt.techin.model.Image;
import lt.techin.model.User;
import lt.techin.repository.ImageRepository;
import lt.techin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

  private final ImageRepository imageRepository;
  private final UserRepository userRepository;

  @Autowired
  public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
    this.imageRepository = imageRepository;
    this.userRepository = userRepository;
  }

  public List<Image> findAllImages() {
    return this.imageRepository.findAll();
  }

  public Optional<Image> findImageById(Long id) {
    return this.imageRepository.findById(id);
  }

  public Image saveImage(ImageRequestDTO imageRequestDTO) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Jwt jwt = (Jwt) authentication.getPrincipal();
    String username = jwt.getSubject();

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    Image image = ImageMapper.toImage(imageRequestDTO, user);

    return this.imageRepository.save(image);
  }

  public void deleteImageById(long id) {
    this.imageRepository.deleteById(id);
  }

  public List<Image> findImagesForCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Jwt jwt = (Jwt) authentication.getPrincipal();
    String username = jwt.getSubject();

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return imageRepository.findByUser(user);
  }

}
