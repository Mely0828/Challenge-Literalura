package com.aluracursos.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,              // Nombre del autor, alias "name" en JSON
        @JsonAlias("birth_year") Integer fechaNacimiento,  // Año de nacimiento del autor, alias "birth_year" en JSON
        @JsonAlias("death_year") Integer fechaFallecimiento  // Año de fallecimiento del autor, alias "death_year" en JSON
) {
}
