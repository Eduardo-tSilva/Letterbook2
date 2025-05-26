package br.com.Letterbook.Letterbook.repository;

import br.com.Letterbook.Letterbook.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    // Encontra uma avaliação específica de um usuário para um livro
    Optional<Assessment> findByUsuarioIdAndLivroId(Long usuarioId, Integer livroId);

    // Lista todas as avaliações de um livro específico
    List<Assessment> findByLivroId(Integer livroId);

    // Lista todas as avaliações de um usuário específico
    List<Assessment> findByUsuarioId(Long usuarioId);

    // Calcula a média de avaliações de um livro
    @Query("SELECT AVG(a.nota) FROM Assessment a WHERE a.livroId = :livroId")
    Double calcularMediaAvaliacoesPorLivro(@Param("livroId") Integer livroId);

    // Conta o número de avaliações de um livro
    @Query("SELECT COUNT(a) FROM Assessment a WHERE a.livroId = :livroId")
    Long contarAvaliacoesPorLivro(@Param("livroId") Integer livroId);

    // Busca avaliações recentes (últimas 5)
    @Query("SELECT a FROM Assessment a WHERE a.livroId = :livroId ORDER BY a.dataAvaliacao DESC LIMIT 5")
    List<Assessment> findTop5ByLivroIdOrderByDataAvaliacaoDesc(@Param("livroId") Integer livroId);

    // Verifica se um usuário já avaliou um livro
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Assessment a WHERE a.usuarioId = :usuarioId AND a.livroId = :livroId")
    boolean existsByUsuarioIdAndLivroId(@Param("usuarioId") Long usuarioId, @Param("livroId") Integer livroId);
}