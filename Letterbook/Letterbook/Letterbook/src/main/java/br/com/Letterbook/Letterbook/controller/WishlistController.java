package br.com.Letterbook.Letterbook.controller;

import br.com.Letterbook.Letterbook.model.Book;
import br.com.Letterbook.Letterbook.model.Users;
import br.com.Letterbook.Letterbook.repository.BookRepository;
import br.com.Letterbook.Letterbook.repository.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WishlistController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/wishlist/add/{id}")
    public String addToWishlist(@PathVariable("id") int bookId, HttpSession session, RedirectAttributes redirectAttributes) {
        Users user = (Users) session.getAttribute("usuarioLogado");
        if (user == null) {
            return "redirect:/loginUsers";
        }

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Users dbUser = usersRepository.findById(user.getId()).orElseThrow();
        if (!dbUser.getWishlist().contains(book)) {
            dbUser.getWishlist().add(book);
            usersRepository.save(dbUser);
        }

        redirectAttributes.addFlashAttribute("message", "Livro adicionado à sua lista de desejos!");
        return "redirect:/wishlist";
    }


    @GetMapping("/wishlist")
    public String viewWishlist(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("usuarioLogado");
        if (user == null) {
            return "redirect:/loginUsers";
        }

        Users dbUser = usersRepository.findById(user.getId()).orElseThrow();
        model.addAttribute("wishlist", dbUser.getWishlist());

        return "books/wishlist";
    }

    @PostMapping("/wishlist/remove/{id}")
    public String removeFromWishlist(@PathVariable("id") int bookId, HttpSession session, RedirectAttributes redirectAttributes) {
        Users user = (Users) session.getAttribute("usuarioLogado");
        if (user == null) {
            return "redirect:/loginUsers";
        }

        Users dbUser = usersRepository.findById(user.getId()).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (dbUser.getWishlist().contains(book)) {
            dbUser.getWishlist().remove(book);
            usersRepository.save(dbUser);
        }

        redirectAttributes.addFlashAttribute("message", "Livro removido da sua lista de desejos.");
        return "redirect:/wishlist";
    }

}
