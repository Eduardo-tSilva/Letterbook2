package br.com.Letterbook.Letterbook.repository;

import br.com.Letterbook.Letterbook.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    // Busca produtos pelo nome com busca parcial, ignorando maiúsculas/minúsculas
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByGenreIgnoreCase(String genre);
    List<Book> findByTitleContainingIgnoreCaseAndGenreIgnoreCase(String title, String genre);

}
