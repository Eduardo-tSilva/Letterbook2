package br.com.Letterbook.Letterbook.repository;

import br.com.Letterbook.Letterbook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitle(String Title);
    List<Book> findByAuthor(String Author);
    List<Book> findByTitleAndAuthor(String title, String author);
    List<Book> findByApplicationyear(Long year);

}
