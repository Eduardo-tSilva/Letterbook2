package br.com.Letterbook.Letterbook.controller;

import br.com.Letterbook.Letterbook.model.Assessment;
import br.com.Letterbook.Letterbook.model.Book;
import br.com.Letterbook.Letterbook.model.DTO.AssessmentDTO;
import br.com.Letterbook.Letterbook.model.Users;
import br.com.Letterbook.Letterbook.repository.AssessmentRepository;
import br.com.Letterbook.Letterbook.repository.BookRepository;
import br.com.Letterbook.Letterbook.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {
    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BookRepository bookRepository;

    // Listar avaliações de um usuário específico
    @GetMapping("/usuario/{usuarioId}")
    public List<Assessment> getByUsuarioId(@PathVariable Long usuarioId) {
        return assessmentRepository.findByUsuarioId(usuarioId);
    }

    @PostMapping
    public ResponseEntity<?> createAssessment(@RequestBody AssessmentDTO dto) {
        try {
            Users usuario = usersRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Book livro = bookRepository.findById(dto.getLivroId())
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

            Assessment assessment = new Assessment();
            assessment.setUsuarioId(usuario.getId()); // só setar o id do usuário
            assessment.setLivroId(dto.getLivroId().intValue()); // converte Long para Integer
            assessment.setNota(dto.getNota());
            assessment.setComentario(dto.getComentario());

            Assessment saved = assessmentRepository.save(assessment);
            return ResponseEntity.ok(saved);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Atualizar avaliação (opcional)
    @PutMapping("/{id}")
    public ResponseEntity<Assessment> updateAssessment(@PathVariable Long id, @RequestBody Assessment updatedAssessment) {
        return assessmentRepository.findById(id)
                .map(assessment -> {
                    assessment.setNota(updatedAssessment.getNota());
                    assessment.setComentario(updatedAssessment.getComentario());
                    return ResponseEntity.ok(assessmentRepository.save(assessment));
                }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar avaliação (opcional)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
        return assessmentRepository.findById(id)
                .map(assessment -> {
                    assessmentRepository.delete(assessment);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
