package com.alura.literatura.service;

import com.alura.literatura.dto.GutendexResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.literatura.dto.*;
import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Libro;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class GutendexService {
    private static final String API_URL = "https://gutendex.com/books";
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public String buscarYGuardarLibroPorTitulo(String titulo) {
        // Normalizar título: eliminar espacios extras y caracteres especiales
        String tituloNormalizado = titulo.trim()
                .replaceAll("[^a-zA-Z0-9\\s]", "") // Elimina signos de puntuación
                .replaceAll("\\s+", " ")          // Reduce espacios múltiples
                .toLowerCase();

        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("search", tituloNormalizado)
                .toUriString();

        try {
            GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);

            if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
                return "No se encontraron libros con esa palabra clave.";
            }

            Optional<BookDTO> libroExacto = response.getResults().stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(tituloNormalizado))
                    .findFirst();

            boolean guardado = libroExacto.isPresent()
                    ? guardarLibroDesdeDTO(libroExacto.get())
                    : guardarLibroDesdeDTO(response.getResults().get(0));

            return guardado
                    ? "Libro guardado exitosamente!"
                    : "El libro ya existe en la base de datos.";
        } catch (RestClientException e) {
            return "Error al conectar con la API: " + e.getMessage();
        }
    }

    private boolean guardarLibroDesdeDTO(BookDTO bookDTO) {
        // Verificar si el libro ya existe
        if (libroRepository.existsById(bookDTO.getId())) {
            return false;
        }

        // Crear nuevo libro
        Libro libro = new Libro();
        libro.setId(bookDTO.getId());
        libro.setTitulo(bookDTO.getTitle());
        libro.setDescargas(bookDTO.getDownloadCount());
        libro.setIdioma(bookDTO.getLanguages().isEmpty() ? "es" : bookDTO.getLanguages().get(0));

        // Procesar solo el primer autor
        if (!bookDTO.getAuthors().isEmpty()) {
            AuthorDTO primerAutorDTO = bookDTO.getAuthors().get(0);
            Autor autor = procesarAutor(primerAutorDTO);
            libro.setAutor(autor);
        }

        libroRepository.save(libro);
        return true;
    }

    private Autor procesarAutor(AuthorDTO authorDTO) {
        // Buscar autor existente por nombre
        List<Autor> autoresExistentes = autorRepository.findByNombreContainingIgnoreCase(authorDTO.getName());
        if (!autoresExistentes.isEmpty()) {
            return autoresExistentes.get(0);
        }

        // Crear nuevo autor
        Autor autor = new Autor();
        autor.setNombre(authorDTO.getName());
        autor.setNacimiento(authorDTO.getBirthYear());
        autor.setFallecimiento(authorDTO.getDeathYear());

        return autorRepository.save(autor);
    }


}
