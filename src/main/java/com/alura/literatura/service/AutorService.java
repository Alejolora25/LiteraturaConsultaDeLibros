package com.alura.literatura.service;

import com.alura.literatura.model.Autor;
import com.alura.literatura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> buscarAutoresPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre);
    }


    public List<Autor> buscarAutoresVivosEnAnio(Integer anio) {
        return autorRepository.findAutoresVivosEnAnio(anio);
    }

    public List<Autor> buscarAutoresPorRangoNacimiento(Integer anioInicio, Integer anioFin) {
        return autorRepository.findAutoresPorRangoNacimiento(anioInicio, anioFin);
    }
}
