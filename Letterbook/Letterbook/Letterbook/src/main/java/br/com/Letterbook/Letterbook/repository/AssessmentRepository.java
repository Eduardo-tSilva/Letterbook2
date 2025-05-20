package br.com.Letterbook.Letterbook.repository;

import br.com.Letterbook.Letterbook.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    // Buscar todas avaliações de um usuário específico
    List<Assessment> findByUsuarioId(Long usuarioId);
}
