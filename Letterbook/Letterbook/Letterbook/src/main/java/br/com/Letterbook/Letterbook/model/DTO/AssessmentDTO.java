package br.com.Letterbook.Letterbook.model.DTO;

public class AssessmentDTO {
    private Long usuarioId;      // continua Long porque o ID de Users é Long
    private Integer livroId;     // mudou para Integer para bater com o ID do Book
    private Integer nota;
    private String comentario;

    public AssessmentDTO() {

    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Integer getLivroId() { return livroId; }
    public void setLivroId(Integer livroId) { this.livroId = livroId; }

    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
