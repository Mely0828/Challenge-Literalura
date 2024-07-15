package com.aluracursos.Literalura.repository;

import com.aluracursos.Literalura.model.Autor;
import com.aluracursos.Literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAutorRepository extends JpaRepository<Autor, Long> {
    //Busqueda de los elementos en el servidor SQL
    @Query("SELECT a FROM Autor a WHERE a.fechaFallecimiento > :fecha OR a.fechaFallecimiento IS NULL")
    List<Autor> buscarAutoresVivos(@Param("fecha") Integer fecha);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> buscarLibrosPorIdioma(@Param("idioma") String idioma);

    @Query("SELECT l FROM Libro l")
    List<Libro> buscarTodosLosLibros();

    //Busqueda de los elementos en el servidor SQL
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(int fechaNacimiento, int fechaFallecimiento);
    List<Autor> findByFechaFallecimientoIsNullOrFechaFallecimientoGreaterThanEqual(int fechaFallecimiento);

    Autor findByNombre(String nombre);

    //Busqueda de los elementos en el servidor SQL
    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Autor> buscarAutorPorNombre(@Param("nombre") String nombre);
}
