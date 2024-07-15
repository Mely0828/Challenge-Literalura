package com.aluracursos.Literalura.repository;

import com.aluracursos.Literalura.model.Libro;
import com.aluracursos.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long> {
    //Busqueda de los elementos en el servidor SQL
    Optional<Libro> findById(Long id);

    List<Libro> findByTitulo(String titulo);

    List<Libro> findByIdioma(String idioma);

    //Busqueda de los elementos en el servidor SQL
    @Query("SELECT l.autor FROM Libro l WHERE l.autor.nombre LIKE %:nombre%")
    List<Autor> buscarAutorPorNombre(@Param("nombre") String nombre);

    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo%")
    Optional<Libro> buscarLibroPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> buscarLibrosPorIdioma(@Param("idioma") String idioma);
}
