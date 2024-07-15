package com.aluracursos.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora propiedades desconocidas durante la deserialización JSON
public record DatosLibro( // Define un record llamado DatosLibro
        @JsonAlias("id") Long id, // Alias JSON "id" mapeado al campo id de tipo Long
        @JsonAlias("title") String titulo, // Alias JSON "title" mapeado al campo titulo de tipo String
        @JsonAlias("authors") List<DatosAutor> autor, // Alias JSON "authors" mapeado al campo autor de tipo List<DatosAutor>
        @JsonAlias("languages") List<DatosIdioma> idioma, // Alias JSON "languages" mapeado al campo idioma de tipo List<DatosIdioma>
        @JsonAlias("download_count") Double numero_Descargas) { // Alias JSON "download_count" mapeado al campo numero_Descargas de tipo Double
}
