package br.com.Letterbook.Letterbook.controller;

import br.com.Letterbook.Letterbook.model.Assessment;
import br.com.Letterbook.Letterbook.model.Book;
import br.com.Letterbook.Letterbook.model.DTO.AssessmentDTO;
import br.com.Letterbook.Letterbook.model.Users;
import br.com.Letterbook.Letterbook.repository.AssessmentRepository;
import br.com.Letterbook.Letterbook.repository.BookRepository;
import br.com.Letterbook.Letterbook.repository.UsersRepository;
import br.com.Letterbook.Letterbook.service.AssessmentService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/books/view")
public class AssessmentController {

    @Autowired
    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/{bookId}/review")
    public String submitReview(
            @PathVariable Integer bookId,
            @RequestParam Double nota,
            @RequestParam String comentario,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Users user = (Users) session.getAttribute("usuarioLogado");
        if (user == null) {
            return "redirect:/loginUsers";
        }

        // Verifica se o livro existe
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        // Verifica se já existe uma avaliação deste usuário para este livro
        Optional<Assessment> existingReview = assessmentRepository
                .findByUsuarioIdAndLivroId(user.getId(), bookId);

        if (existingReview.isPresent()) {
            // Atualiza a avaliação existente
            Assessment review = existingReview.get();
            review.setNota(nota);
            review.setComentario(comentario);
            assessmentRepository.save(review);
            redirectAttributes.addFlashAttribute("message",
                    "Sua avaliação foi atualizada com sucesso!");
        } else {
            // Cria nova avaliação
            Assessment review = new Assessment();
            review.setLivroId(bookId);
            review.setUsuarioId(user.getId());
            review.setNota(nota);
            review.setComentario(comentario);
            assessmentRepository.save(review);
            redirectAttributes.addFlashAttribute("message",
                    "Avaliação enviada com sucesso!");
        }

        return "redirect:/books/view/" + bookId;
    }


    @GetMapping("/check-review")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkUserReview(
            @RequestParam Integer bookId,
            @RequestParam Long userId) {

        Optional<Assessment> review = assessmentRepository
                .findByUsuarioIdAndLivroId(userId, bookId);

        Map<String, Object> response = new HashMap<>();

        if (review.isPresent()) {
            response.put("hasReview", true);
            response.put("review", review.get());
        } else {
            response.put("hasReview", false);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable Long reviewId,
            HttpSession session) {

        Users user = (Users) session.getAttribute("usuarioLogado");
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<Assessment> review = assessmentRepository.findById(reviewId);
        if (review.isPresent() && review.get().getUsuarioId().equals(user.getId())) {
            assessmentRepository.delete(review.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{bookId}/average-rating")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable Integer bookId) {
        Double average = assessmentRepository.calcularMediaAvaliacoesPorLivro(bookId);
        Long count = assessmentRepository.contarAvaliacoesPorLivro(bookId);

        Map<String, Object> response = new HashMap<>();
        response.put("average", average != null ? Math.round(average * 10) / 10.0 : 0);
        response.put("count", count);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bookId}/all-reviews")
    @ResponseBody
    public ResponseEntity<List<AssessmentDTO>> getAllReviews(@PathVariable Integer bookId) {
        List<Assessment> reviews = assessmentRepository.findByLivroId(bookId);
        List<AssessmentDTO> dtos = reviews.stream()
                .map(assessmentService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


}