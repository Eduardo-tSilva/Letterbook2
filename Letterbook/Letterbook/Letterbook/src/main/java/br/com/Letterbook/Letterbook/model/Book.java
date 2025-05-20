package br.com.Letterbook.Letterbook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOOK")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    @NotBlank
    private String title;  // A nomenclatura corrigida para 'title'

    @Column(nullable = false)
    @NotBlank
    private String author;  // 'author' também

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank
    private String synopsis;  // 'synopsis'

    @Column(nullable = false)
    @NotNull
    private Long applicationyear;  // 'year'

    @Column(nullable = false)
    @NotBlank
    private String genre;  // 'genre'

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @NotBlank String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank String author) {
        this.author = author;
    }

    public @NotBlank String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(@NotBlank String synopsis) {
        this.synopsis = synopsis;
    }

    public Long getApplicationyear() {
        return applicationyear;
    }

    public void setApplicationyear(Long applicationyear) {
        this.applicationyear = applicationyear;
    }

    public @NotBlank String getGenre() {
        return genre;
    }

    public void setGenre(@NotBlank String genre) {
        this.genre = genre;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
