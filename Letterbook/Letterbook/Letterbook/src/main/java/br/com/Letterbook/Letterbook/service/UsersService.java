package br.com.Letterbook.Letterbook.service;

import br.com.Letterbook.Letterbook.model.DTO.UsersDTO;
import br.com.Letterbook.Letterbook.model.Users;
import br.com.Letterbook.Letterbook.repository.UsersRepository;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Users autenticar(String email, String senha) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(senha, user.getSenha())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        return user;
    }

    public Users addUsers(UsersDTO usuarioDTO) {
        if (!isCpfValido(usuarioDTO.getCpf())) {
            throw new ValidationException("CPF inválido.");
        }

        if (usersRepository.findByCpf(usuarioDTO.getCpf()) != null) {
            throw new ValidationException("Este CPF já está cadastrado.");
        }

        if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmarSenha())) {
            throw new ValidationException("As senhas não conferem.");
        }

        Users users = new Users();
        users.setEmail(usuarioDTO.getEmail());
        users.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        users.setNome(usuarioDTO.getNome());
        users.setCpf(usuarioDTO.getCpf());
        users.setGenero(usuarioDTO.getGenero());
        users.setDataNascimento(usuarioDTO.getDataNascimento());

        return usersRepository.save(users); // ← agora retornando
    }

    // Validação do CPF (mantida)
    public boolean isCpfValido(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++)
                soma += (cpf.charAt(i) - '0') * (10 - i);

            int digito1 = 11 - (soma % 11);
            if (digito1 >= 10) digito1 = 0;

            if (digito1 != (cpf.charAt(9) - '0')) return false;

            soma = 0;
            for (int i = 0; i < 10; i++)
                soma += (cpf.charAt(i) - '0') * (11 - i);

            int digito2 = 11 - (soma % 11);
            if (digito2 >= 10) digito2 = 0;

            return digito2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }

    public void updateUser(Users updatedUser) {
        usersRepository.save(updatedUser);
    }
}
