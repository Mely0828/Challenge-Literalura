package com.aluracursos.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único del autor

    private String nombre;  // Nombre del autor

    @JsonAlias("fechaNacimiento")
    private Integer fechaNacimiento;  // Fecha de nacimiento del autor, mapeada desde "fechaNacimiento" en JSON

    @JsonAlias("fechaFallecimiento")
    private Integer fechaFallecimiento;  // Fecha de fallecimiento del autor, mapeada desde "fechaFallecimiento" en JSON

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;  // Lista de libros escritos por el autor

    public Autor() {}  // Constructor vacío requerido por JPA

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();  // Inicializa el nombre del autor desde datosAutor
        this.fechaNacimiento = datosAutor.fechaNacimiento();  // Inicializa la fecha de nacimiento desde datosAutor
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();  // Inicializa la fecha de fallecimiento desde datosAutor
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------- Autor -----------\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Fecha nacimiento: ").append(fechaNacimiento).append("\n");
        sb.append("Fecha fallecimiento: ").append(fechaFallecimiento).append("\n");
        sb.append("------------------------------\n");
        return sb.toString();
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

}
