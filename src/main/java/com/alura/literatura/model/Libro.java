package com.alura.literatura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private Long id;
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String idioma;
    private Integer descargas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // Constructores, getters y setters
    public Libro() {}

    public Libro(Long id, String titulo, String idioma, Integer descargas) {
        this.id = id;
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Integer getDescargas() { return descargas; }
    public void setDescargas(Integer descargas) { this.descargas = descargas; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "Libro: " + titulo +
                "\nIdioma: " + idioma +
                "\nDescargas: " + descargas;
    }

    public String getNombreAutor() {
        return autor != null ? autor.getNombre() : "Desconocido";
    }
}
