package lt.techin.repository;

import lt.techin.model.Image;
import lt.techin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

  List<Image> findByUser(User user);
}
