package com.alura.literatura.service;

import com.alura.literatura.model.Libro;
import com.alura.literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class LibroService {
    private final LibroRepository libroRepository;

    @Autowired
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }


    public List<Libro> buscarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }

    public List<Libro> obtenerTop10Libros() {
        return libroRepository.findTop10ByOrderByDescargasDesc();
    }

    public DoubleSummaryStatistics obtenerEstadisticasDescargas() {
        return libroRepository.findAll().stream()
                .mapToDouble(Libro::getDescargas)
                .summaryStatistics();
    }
}
