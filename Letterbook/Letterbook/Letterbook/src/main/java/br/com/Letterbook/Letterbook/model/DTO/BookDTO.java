package br.com.Letterbook.Letterbook.model.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Transient;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private int id;

    @NotEmpty(message = "Campo obrigatório")
    private String title;

    @NotEmpty(message = "Campo obrigatório")
    private String author;

    @NotEmpty(message = "Campo obrigatório")
    private String synopsis;

    @NotNull(message = "Campo obrigatório")  // Substitua @NotBlank por @NotNull para Long
    private Long year;

    @NotEmpty(message = "Campo obrigatório")
    private String genre;

    @Transient
    private List<MultipartFile> images;

    // Getters e Setters manualmente definidos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
