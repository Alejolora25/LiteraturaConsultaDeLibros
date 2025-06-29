package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    // Derived query para autores vivos en un año específico
    @Query("SELECT a FROM Autor a " +
            "WHERE (:anio BETWEEN a.nacimiento AND a.fallecimiento) OR " +
            "(:anio >= a.nacimiento AND a.fallecimiento IS NULL)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);

    @Query("SELECT a FROM Autor a WHERE " +
            "(:anioInicio IS NULL OR a.nacimiento >= :anioInicio) AND " +
            "(:anioFin IS NULL OR a.nacimiento <= :anioFin)")
    List<Autor> findAutoresPorRangoNacimiento(
            @Param("anioInicio") Integer anioInicio,
            @Param("anioFin") Integer anioFin
    );
}