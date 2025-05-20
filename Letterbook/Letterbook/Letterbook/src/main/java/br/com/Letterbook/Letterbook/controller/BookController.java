package br.com.Letterbook.Letterbook.controller;

import br.com.Letterbook.Letterbook.model.Book;
import br.com.Letterbook.Letterbook.model.DTO.BookDTO;
import br.com.Letterbook.Letterbook.model.Image;
import br.com.Letterbook.Letterbook.model.Users;
import br.com.Letterbook.Letterbook.repository.BookRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    @GetMapping({"","/"})
    public String showBooksUser(@RequestParam(value = "search", required = false, defaultValue = "") String search,
                                Model model) {
        List<Book> books;
        if(search.isEmpty()) {
            books = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } else {
            books = bookRepository.findByTitle(search);
        }
        model.addAttribute("books", books);
        model.addAttribute("search", search);
        return "books/userBooks";  // template usuário sem botões
    }

    @GetMapping({"/admin"})
    public String showBooks(@RequestParam(value = "search", required = false, defaultValue = "") String search, Model model, HttpSession session) {
        Users user = (Users) session.getAttribute("usuarioLogado");

        if (user != null) {
            String name = user.getNome();
            model.addAttribute("userName", name);
        }
        List<Book> books;

        if(search.isEmpty()) {
            books = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } else {
            books = bookRepository.findByTitle(search);
        }

        model.addAttribute("books", books);
        model.addAttribute("search", search);
        return "books/allBooks";
    }

    @GetMapping("/createBook")
    public String createBook(Model model) {
        BookDTO bookDTO = new BookDTO();
        model.addAttribute("book", bookDTO);
        return "books/createBook";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable int id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setSynopsis(book.getSynopsis());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setYear(book.getApplicationyear());

        model.addAttribute("book", bookDTO);
        model.addAttribute("existingImages", book.getImages());

        // Obtém URL da imagem principal e adiciona ao model
        String principalImageUrl = book.getImages().stream()
                .filter(Image::isPrincipal)
                .findFirst()
                .map(Image::getUrl)
                .orElse("");
        model.addAttribute("principalImageUrl", principalImageUrl);

        return "books/createBook";
    }

    @PostMapping("/createBook")
    public String createBook(@Valid BookDTO bookDTO,
                             BindingResult bindingResult,
                             @RequestParam(value = "principalImage", required = false) String principalImage,
                             @RequestParam(value = "deleteImages", required = false) List<Integer> deleteImages,
                             RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            return "books/createBook";
        }

        Book book;

        if (bookDTO.getId() != 0) {
            book = bookRepository.findById(bookDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));
        } else {
            book = new Book();
        }

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setSynopsis(bookDTO.getSynopsis());
        book.setGenre(bookDTO.getGenre());
        book.setApplicationyear(bookDTO.getYear());

        if (deleteImages != null && !deleteImages.isEmpty()) {
            List<Image> toRemove = new ArrayList<>();
            for (Image img : book.getImages()) {
                if (deleteImages.contains(img.getId())) {
                    try {
                        Files.deleteIfExists(Paths.get("uploads/" + img.getUrl()));
                    } catch (IOException e) {
                        e.printStackTrace(); // ou logue
                    }
                    toRemove.add(img);
                }
            }
            book.getImages().removeAll(toRemove);
        }

        // Adicionar novas imagens
        if (bookDTO.getImages() != null && !bookDTO.getImages().isEmpty()) {
            String uploadDir = "uploads/";
            for (MultipartFile file : bookDTO.getImages()) {
                if (file.isEmpty()) continue;

                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);

                if (!Files.exists(Paths.get(uploadDir))) {
                    Files.createDirectories(Paths.get(uploadDir));
                }

                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                Image newImage = new Image();
                newImage.setUrl(fileName);
                newImage.setBook(book);
                newImage.setPrincipal(false);
                book.getImages().add(newImage);
            }
        }

        boolean principalImageFound = false;
        for (Image img : book.getImages()) {
            if (img.getUrl().equals(principalImage)) {
                img.setPrincipal(true);
                principalImageFound = true;
            } else {
                img.setPrincipal(false);
            }
        }
// Se não foi marcada nenhuma, marque a primeira imagem como principal (se existir)
        if (!principalImageFound && !book.getImages().isEmpty()) {
            book.getImages().get(0).setPrincipal(true);
        }

        bookRepository.save(book);

        redirectAttributes.addFlashAttribute("message", "Book saved successfully");
        return "redirect:/books";
    }



    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        for (Image image : book.getImages()) {
            try {
                Path path = Paths.get("uploads/" + image.getUrl());
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bookRepository.delete(book);

        redirectAttributes.addFlashAttribute("message", "Book deleted successfully");
        return "redirect:/books";
    }

    @GetMapping("/view/{id}")
    public String viewBook(@PathVariable int id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Image principalImage = book.getImages().stream()
                .filter(Image::isPrincipal)
                .findFirst()
                .orElse(null);

        model.addAttribute("book", book);
        model.addAttribute("principalImage", principalImage);

        return "books/viewBook";
    }

}
