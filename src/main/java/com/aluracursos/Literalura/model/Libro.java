package com.aluracursos.Literalura.model;

import jakarta.persistence.*;

@Entity // Indica que esta clase es una entidad JPA y se mapea a una tabla en la base de datos
@Table(name= "libro") // Especifica el nombre de la tabla en la base de datos donde se almacenarán los libros
public class Libro {

    @Id // Indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática de valores para la clave primaria
    private Long id;

    @Column(name = "titulo") // Mapea este atributo a la columna 'titulo' en la tabla 'libro'
    private String titulo;

    @Column(name = "idioma") // Mapea este atributo a la columna 'idioma' en la tabla 'libro'
    private String idioma;

    @ManyToOne(fetch = FetchType.EAGER) // Establece una relación muchos a uno con la entidad Autor, con carga ansiosa
    @JoinColumn(name = "autor_id") // Especifica la columna 'autor_id' como la clave foránea que referencia al autor del libro
    private Autor autor;

    @Column(name = "numero_descargas") // Mapea este atributo a la columna 'numero_descargas' en la tabla 'libro'
    private Double numero_Descargas;

    public Libro() {
    }

    // Constructor que utiliza un objeto DatosLibro y un Autor para inicializar un libro
    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        // Verifica si el idioma está vacío y establece "Desconocido" si es así, de lo contrario, obtiene el idioma del primer elemento
        this.idioma = datosLibro.idioma().isEmpty() ? "Desconocido" : (String) datosLibro.idioma().get(0).idioma();
        this.numero_Descargas = Double.valueOf(datosLibro.numero_Descargas());
        this.autor = autor;
    }

    // Constructor adicional que inicializa un libro con título, idioma, autor y número de descargas
    public Libro(String titulo, String idioma, Autor autor, Double aDouble) {
    }

    // Override del método toString para imprimir información detallada del libro
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("*********** Libro ***********\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autor: ").append(autor != null ? autor.getNombre() : "Desconocido").append("\n");
        sb.append("Idioma: ").append(idioma).append("\n");
        sb.append("Número de descargas: ").append(numero_Descargas).append("\n");
        sb.append("****************************\n");
        return sb.toString();
    }

    // Métodos getter y setter para acceder y modificar los atributos privados de la clase

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumero_Descargas() {
        return numero_Descargas;
    }

    public void setNumero_Descargas(Double numero_Descargas) {
        this.numero_Descargas = numero_Descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Double numero_Descargas() {
        return numero_Descargas;
    }
}
