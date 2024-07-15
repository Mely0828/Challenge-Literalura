package com.aluracursos.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("results") List<DatosLibro> resultados,  // Lista de resultados de libros, alias "results" en JSON
        @JsonAlias("count") Double total  // Total de resultados, alias "count" en JSON
) {
}
