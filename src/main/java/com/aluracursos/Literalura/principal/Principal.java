package com.aluracursos.Literalura.principal;

import com.aluracursos.Literalura.model.*;
import com.aluracursos.Literalura.repository.IAutorRepository;
import com.aluracursos.Literalura.repository.ILibroRepository;
import com.aluracursos.Literalura.service.ConsumoAPI;
import com.aluracursos.Literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class Principal {
    private final Scanner teclado = new Scanner(System.in); // Objeto Scanner para entrada de usuario
    private final ConsumoAPI consumoApi = new ConsumoAPI(); // Instancia para consumir la API externa
    private final String URL_BASE = "https://gutendex.com/books/"; // URL base de la API
    private final ConvierteDatos conversor = new ConvierteDatos(); // Instancia para convertir datos
    private final IAutorRepository iAutorRepository; // Repositorio de autores
    private final ILibroRepository iLibroRepository; // Repositorio de libros

    @Autowired
    public Principal(ILibroRepository iLibroRepository, IAutorRepository iAutorRepository) {
        this.iLibroRepository = iLibroRepository;
        this.iAutorRepository = iAutorRepository;
    }

    /**
     * Muestra el menú principal y gestiona las opciones seleccionadas por el usuario.
     */
    public void muestraMenu() {
        int opc = -1;
        while (opc != 0) {
            String menu = """
                    ----- SELECCIONE UNA OPCIÓN -----
                    1- Buscar libro por título
                    2- Ver lista de libros registrados
                    3- Ver lista de autores registrados
                    4- Ver lista de autores vivos en un año específico
                    5- Ver lista de libros por idioma
                    6- Top 10 libros más descargados
                    7- Estadísticas
                    0- Salir
                    """;

            // Mostrar menú y obtener opción del usuario
            System.out.println(menu);
            opc = teclado.nextInt();
            teclado.nextLine(); // Limpiar el buffer del scanner

            // Ejecutar opción seleccionada
            switch (opc) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivos();
                case 5 -> manejarMenuIdiomas();
                case 6 -> top10();
                case 7 -> estadisticas();
                case 0 -> System.out.println("Cerrando la aplicación. Gracias por sus consultas.");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    /**
     * Obtiene los datos de un libro basado en el título proporcionado por el usuario.
     * @return DatosLibro encontrado o null si no se encuentra.
     */
    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el título del libro: ");
        String nombreLibro = teclado.nextLine();
        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        Datos data = conversor.obtenerDatos(json, Datos.class);
        if (data != null && data.resultados() != null && !data.resultados().isEmpty()) {
            return data.resultados().get(0); // Tomar el primer resultado
        }
        return null;
    }

    /**
     * Almacena un libro en la base de datos.
     * @param datosLibro Datos del libro a almacenar.
     * @param autor Autor asociado al libro.
     * @return Libro almacenado.
     */
    private Libro almacenarLibro(DatosLibro datosLibro, Autor autor) {
        List<Libro> existingLibros = iLibroRepository.findByTitulo(datosLibro.titulo());
        if (!existingLibros.isEmpty()) {
            return existingLibros.get(0); // Devuelve el primer libro encontrado
        }
        Libro libro = new Libro(datosLibro, autor);
        return iLibroRepository.save(libro);
    }

    /**
     * Permite al usuario buscar un libro por título y almacenarlo en la base de datos si se encuentra.
     */
    private void buscarLibroPorTitulo() {
        DatosLibro data = getDatosLibro();
        if (data != null) {
            Autor autor = data.autor().stream()
                    .map(da -> {
                        Autor autorExiste = iAutorRepository.findByNombre(da.nombre());
                        if (autorExiste != null) {
                            return autorExiste;
                        } else {
                            Autor nuevoAutor = new Autor(da);
                            return iAutorRepository.save(nuevoAutor);
                        }
                    })
                    .findFirst()
                    .orElse(null);
            if (autor != null) {
                Libro libro = almacenarLibro(data, autor);
                System.out.println("- Libro almacenado: " + libro.getTitulo());
            }
        } else {
            System.out.println("---------------------------------------------");
            System.out.println("- No se encontraron datos para el título proporcionado -");
            System.out.println("---------------------------------------------");
        }
    }

    /**
     * Muestra la lista de todos los libros registrados en la base de datos.
     */
    private void listarLibrosRegistrados() {
        System.out.println("Lista de libros registrados:");
        List<Libro> libros = iLibroRepository.findAll();
        if (!libros.isEmpty()) {
            libros.forEach(libro -> System.out.println("- " + libro.getTitulo()));
        } else {
            System.out.println("-------------------------------");
            System.out.println("- No hay libros registrados. -");
            System.out.println("-------------------------------");
        }
    }

    /**
     * Muestra la lista de todos los autores registrados en la base de datos.
     */
    private void listarAutoresRegistrados() {
        System.out.println("Lista de autores registrados:");
        List<Autor> autores = iAutorRepository.findAll();
        if (!autores.isEmpty()) {
            autores.forEach(autor -> System.out.println("- " + autor.getNombre()));
        } else {
            System.out.println("No hay autores registrados.");
        }
    }

    /**
     * Muestra la lista de autores vivos en un año específico según la base de datos.
     */
    private void listarAutoresVivos() {
        System.out.println("Escribe el año para verificar autores vivos: ");
        int fecha = teclado.nextInt();
        teclado.nextLine(); // Limpiar el buffer del scanner
        List<Autor> autores = iAutorRepository.findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(fecha, fecha);
        if (!autores.isEmpty()) {
            autores.forEach(autor -> System.out.println("- " + autor.getNombre()));
        } else {
            System.out.println("--------------------------------------------------");
            System.out.println("- NO SE ENCONTRARON AUTORES VIVOS PARA EL AÑO PROPORCIONADO -");
            System.out.println("--------------------------------------------------");
        }
    }

    /**
     * Muestra el menú de selección de idiomas y gestiona las opciones seleccionadas por el usuario.
     */
    private void manejarMenuIdiomas() {
        int opcionIdioma = -1;
        while (opcionIdioma != 0) {
            mostrarMenuIdiomas();
            opcionIdioma = teclado.nextInt();
            teclado.nextLine(); // Limpiar el buffer del scanner

            switch (opcionIdioma) {
                case 1 -> listarLibrosPorIdioma("es");
                case 2 -> listarLibrosPorIdioma("fr");
                case 3 -> listarLibrosPorIdioma("en");
                case 4 -> listarLibrosPorIdioma("pt");
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    /**
     * Muestra el menú de selección de idiomas.
     */
    private void mostrarMenuIdiomas() {
        System.out.println("----- ELIJA UN IDIOMA -----");
        System.out.println("1. Español ");
        System.out.println("2. Francés ");
        System.out.println("3. Inglés ");
        System.out.println("4. Portugués ");
        System.out.println("0. Volver al menú principal");
        System.out.println("---------------------------");
        System.out.println("Digite el menú: ");
    }

    /**
     * Obtiene el nombre completo de un idioma basado en su sigla.
     * @param siglaIdioma Sigla del idioma.
     * @return Nombre completo del idioma.
     */
    private String obtenerNombreIdioma(String siglaIdioma) {
        switch (siglaIdioma) {
            case "es":
                return "Español";
            case "fr":
                return "Francés";
            case "en":
                return "Inglés";
            case "pt":
                return "Portugués";
            default:
                return "Desconocido";
        }
    }

    /**
     * Muestra la lista de libros en un idioma específico según la base de datos.
     * @param idioma Sigla del idioma.
     */
    private void listarLibrosPorIdioma(String idioma) {
        String nombreIdioma = obtenerNombreIdioma(idioma);
        List<Libro> libros = iLibroRepository.buscarLibrosPorIdioma(idioma);
        if (!libros.isEmpty()) {
            System.out.println("Los libros en " + nombreIdioma + " son:");
            libros.forEach(libro -> System.out.println("- " + libro.getTitulo()));
        } else {
            System.out.println("No se encontraron libros en el idioma " + nombreIdioma + ".");
        }
    }

    /**
     * Muestra los top 10 libros más descargados según la base de datos.
     */
    public void top10() {
        System.out.println("Top 10 libros más descargados:");
        iLibroRepository.findAll().stream()
                .sorted(Comparator.comparing(Libro::getNumero_Descargas).reversed())
                .limit(10)
                .forEach(libro -> System.out.println("- " + libro.getTitulo()));
    }

    /**
     * Muestra estadísticas sobre los libros almacenados en la base de datos.
     */
    public void estadisticas() {
        System.out.println("Estadísticas de libros: ");
        DoubleSummaryStatistics est = iLibroRepository.findAll().stream()
                .filter(libro -> libro.getNumero_Descargas() > 0)
                .collect(Collectors.summarizingDouble(Libro::getNumero_Descargas));
        System.out.println("Cantidad media de descargas: " + Math.round(est.getAverage()));
        System.out.println("Cantidad máxima de descargas: " + Math.round(est.getMax()));
        System.out.println("Cantidad mínima de descargas: " + Math.round(est.getMin()));
        System.out.println("Cantidad de registros evaluados: " + est.getCount());
    }
}
