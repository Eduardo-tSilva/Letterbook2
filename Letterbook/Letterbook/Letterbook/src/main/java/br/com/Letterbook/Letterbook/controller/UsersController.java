package br.com.Letterbook.Letterbook.controller;

import br.com.Letterbook.Letterbook.model.DTO.UsersDTO;
import br.com.Letterbook.Letterbook.model.Users;
import br.com.Letterbook.Letterbook.service.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/loginUsers")
    public String showLogin() {
        return "books/loginUsers";
    }

    @PostMapping("/loginUsers")
    public String processaLogin(@ModelAttribute UsersDTO loginDTO, Model model, HttpSession session) {
        try {
            // Autentica o usuário (isso já deve retornar um objeto Users)
            Users user = usersService.autenticar(loginDTO.getEmail(), loginDTO.getSenha());

            // Salva o objeto Users completo na sessão
            session.setAttribute("usuarioLogado", user);

            // Agora sim, redireciona para a página de livros
            return "redirect:/books";

        } catch (RuntimeException e) {
            model.addAttribute("erro", "Login inválido.");
            return "books/loginUsers";
        }
    }


    @GetMapping("/registerUsers")
    public String moreUsers(Model model) {
        model.addAttribute("usersDTO", new UsersDTO());
        return "books/registerUsers";
    }

    @PostMapping("/registerUsers")
    public String registerUsers(@Valid @ModelAttribute UsersDTO usersDTO,
                                BindingResult result,
                                Model model,
                                @RequestParam(value = "retorno", required = false) String retorno) {
        // Verifica erros de validação
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
            }
            model.addAttribute("usersDTO", usersDTO); // Adicionando explicitamente ao modelo
            return "books/registerUsers";
        }

        // Tenta cadastrar o user usando o DTO
        try {
            usersService.addUsers(usersDTO);
        } catch (ValidationException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("usersDTO", usersDTO); // Adicionando explicitamente ao modelo
            return "books/registerUsers";
        }

        // Verifica o retorno para redirecionar para a página correta
        if ("allBooks".equals(retorno)) {
            return "redirect:/books/allBooks";
        }

        return "redirect:/loginUsers";

    }
}
