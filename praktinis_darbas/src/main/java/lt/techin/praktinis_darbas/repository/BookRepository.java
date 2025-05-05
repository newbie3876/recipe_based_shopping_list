package lt.techin.praktinis_darbas.repository;


import lt.techin.praktinis_darbas.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

