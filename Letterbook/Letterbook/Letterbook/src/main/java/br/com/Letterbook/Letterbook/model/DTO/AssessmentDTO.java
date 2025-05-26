package br.com.Letterbook.Letterbook.model.DTO;

import br.com.Letterbook.Letterbook.model.Assessment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AssessmentDTO {
    private Long id;
    private Long usuarioId;
    private Integer livroId;
    private Double nota;  // Mudado para Double para bater com a entidade
    private String comentario;
    private LocalDateTime dataAvaliacao; // Mudado para LocalDateTime
    private String userName;

    public AssessmentDTO() {
    }

    public AssessmentDTO(Assessment assessment) {
        this.id = assessment.getId();
        this.usuarioId = assessment.getUsuarioId();
        this.livroId = assessment.getLivroId();
        this.nota = assessment.getNota();
        this.comentario = assessment.getComentario();
        this.dataAvaliacao = assessment.getDataAvaliacao();
        // userName precisa ser carregado de outra forma (via service)
    }

    // Getters e Setters atualizados
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Integer getLivroId() { return livroId; }
    public void setLivroId(Integer livroId) { this.livroId = livroId; }

    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDateTime getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}
