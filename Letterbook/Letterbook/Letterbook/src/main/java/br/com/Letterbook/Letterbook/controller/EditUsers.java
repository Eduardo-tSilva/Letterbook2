package br.com.Letterbook.Letterbook.controller;

import br.com.Letterbook.Letterbook.model.Users;
import br.com.Letterbook.Letterbook.repository.UsersRepository;
import br.com.Letterbook.Letterbook.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class EditUsers {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/editUsers")
    public String showEditProfile(HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("usuarioLogado");

        if (user == null) {
            return "redirect:/loginUsers";
        }

        model.addAttribute("user", user);
        return "books/editUsers";
    }

    @PostMapping("/editUsers")
    public String updateProfile(@ModelAttribute Users updatedUser, HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("usuarioLogado");

        if (user == null) {
            return "redirect:/loginUsers";
        }

        // Verifica se o e-mail foi alterado
        if (isEmailChanged(updatedUser.getEmail(), user.getId())) {

            if (emailExists(updatedUser.getEmail())) {
                model.addAttribute("error", "E-mail já está em uso.");
                model.addAttribute("user", user);
                return "books/editUsers";
            }
        }

        updatedUser.setId(user.getId());
        updatedUser.setCpf(user.getCpf());

        usersService.updateUser(updatedUser);
        session.setAttribute("usuarioLogado", updatedUser);

        return "redirect:/books";
    }

    private boolean isEmailChanged(String newEmail, Long userId) {
        Users currentUser = usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return !currentUser.getEmail().equals(newEmail);
    }

    private boolean emailExists(String email) {
        return usersRepository.existsByEmail(email);
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(Long userId) {
            super("Usuário não encontrado com o ID: " + userId);
        }
    }
}
