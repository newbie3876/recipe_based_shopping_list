package lt.techin.praktinis_darbas.service;


import lt.techin.praktinis_darbas.model.Book;
import lt.techin.praktinis_darbas.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }
}

