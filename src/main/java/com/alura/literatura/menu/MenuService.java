package com.alura.literatura.menu;

import com.alura.literatura.model.Autor;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.Scanner;

import com.alura.literatura.model.Libro;
import com.alura.literatura.service.GutendexService;
import com.alura.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alura.literatura.service.AutorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final Scanner scanner = new Scanner(System.in);

    private final GutendexService gutendexService;
    private final LibroService libroService;
    private final AutorService autorService;


    @Autowired
    public MenuService(GutendexService gutendexService, LibroService libroService, AutorService autorService) {
        this.gutendexService = gutendexService;
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Buscar autores por nombre");
            System.out.println("4. Listar autores registrados");
            System.out.println("5. Listar libros por idioma");
            System.out.println("6. Top 10 libros más descargados");
            System.out.println("7. Buscar autores vivos en año específico");
            System.out.println("8. Estadísticas de descargas");
            System.out.println("9. Buscar autores por rango de nacimiento");
            System.out.println("10. Salir");
            System.out.print("Elija una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número válido.");
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    buscarAutorPorNombre();
                    break;
                case 4:
                    listarAutoresRegistrados();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    mostrarTop10Libros();
                    break;
                case 7:
                    buscarAutoresVivosEnAnio();
                    break;
                case 8:
                    mostrarEstadisticasDescargas();
                    break;
                case 9:
                    buscarAutoresPorRangoNacimiento();
                    break;
                case 10:
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor elija entre 1 y 10.");
            }
        } while (opcion != 10);
    }

    private void buscarLibroPorTitulo() {
        System.out.print("\nIngrese una palabra clave del título del libro (por ejemplo, 'Quijote' para 'Don Quijote'): ");
        String titulo = scanner.nextLine().trim();

        System.out.println("Buscando libros con título que contenga: " + titulo);
        String resultado = gutendexService.buscarYGuardarLibroPorTitulo(titulo);
        System.out.println(resultado);
    }

    private void buscarAutorPorNombre() {
        System.out.print("\nIngrese el nombre del autor: ");
        String nombre = scanner.nextLine();
        System.out.println("Buscando autores con nombre: " + nombre);

        List<Autor> autores = autorService.buscarAutoresPorNombre(nombre);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores con ese nombre.");
        } else {
            System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
            autores.forEach(autor -> {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Nacimiento: " + (autor.getNacimiento() != null ? autor.getNacimiento() : "Desconocido"));
                System.out.println("Fallecimiento: " + (autor.getFallecimiento() != null ? autor.getFallecimiento() : "Desconocido"));

                System.out.println("Libros:");
                if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
                    autor.getLibros().forEach(libro ->
                            System.out.println(" - " + libro.getTitulo() + " (" + libro.getIdioma() + ")")
                    );
                } else {
                    System.out.println(" - No se encontraron libros asociados");
                }
                System.out.println("--------------------");
            });
        }
    }

    private void listarLibrosRegistrados() {
        System.out.println("\n=== LIBROS REGISTRADOS ===");
        List<Libro> libros = libroService.obtenerTodosLosLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados aún.");
        } else {
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Descargas: " + libro.getDescargas());

                if (libro.getAutor() != null) {
                    System.out.println("Autor: " + libro.getAutor().getNombre() +
                            " (" + (libro.getAutor().getNacimiento() != null ? libro.getAutor().getNacimiento() : "?") +
                            "-" + (libro.getAutor().getFallecimiento() != null ? libro.getAutor().getFallecimiento() : "?") + ")");
                } else {
                    System.out.println("Autor: Desconocido");
                }
                System.out.println("--------------------");
            });
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println("\n=== AUTORES REGISTRADOS ===");
        List<Autor> autores = autorService.obtenerTodosLosAutores();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados aún.");
        } else {
            autores.forEach(autor -> {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Años: " +
                        (autor.getNacimiento() != null ? autor.getNacimiento() : "?") +
                        " - " +
                        (autor.getFallecimiento() != null ? autor.getFallecimiento() : "?"));

                System.out.println("Libros: " +
                        (autor.getLibros() != null ? autor.getLibros().size() : 0));
                System.out.println("--------------------");
            });

            // Opción para buscar autores vivos en un año específico
            System.out.print("\n¿Desea buscar autores vivos en un año específico? (s/n): ");
            String opcion = scanner.nextLine().toLowerCase();

            if (opcion.equals("s")) {
                buscarAutoresVivosEnAnio();
            }
        }
    }

    private void buscarAutoresVivosEnAnio() {
        System.out.print("\nIngrese el año: ");
        try {
            Integer anio = Integer.parseInt(scanner.nextLine());
            System.out.println("Buscando autores vivos en el año: " + anio);

            List<Autor> autoresVivos = autorService.buscarAutoresVivosEnAnio(anio);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
            } else {
                System.out.println("\n=== AUTORES VIVOS EN " + anio + " ===");
                autoresVivos.forEach(autor -> {
                    System.out.println("Autor: " + autor.getNombre());
                    System.out.println("Período: " +
                            (autor.getNacimiento() != null ? autor.getNacimiento() : "?") +
                            " - " +
                            (autor.getFallecimiento() != null ? autor.getFallecimiento() : "?"));
                    System.out.println("--------------------");
                });
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor ingrese un año válido.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("\n=== OPCIONES DE IDIOMA ===");
        System.out.println("1. Buscar libros por idioma específico");
        System.out.println("2. Ver estadísticas de todos los idiomas");
        System.out.print("Elija una opción: ");

        int subOpcion;
        try {
            subOpcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida. Volviendo al menú principal.");
            return;
        }

        switch (subOpcion) {
            case 1:
                buscarLibrosPorIdiomaEspecifico();
                break;
            case 2:
                mostrarEstadisticasIdiomas();
                break;
            default:
                System.out.println("Opción inválida. Volviendo al menú principal.");
        }
    }

    private void mostrarEstadisticasIdiomas() {
        List<Libro> todosLibros = libroService.obtenerTodosLosLibros();

        if (todosLibros.isEmpty()) {
            System.out.println("No hay libros registrados aún.");
            return;
        }

        // Usando Streams para obtener estadísticas de idiomas
        Map<String, Long> estadisticas = todosLibros.stream()
                .collect(Collectors.groupingBy(
                        Libro::getIdioma,
                        Collectors.counting()
                ));

        System.out.println("\n=== ESTADÍSTICAS DE IDIOMAS ===");
        System.out.println("Total de libros registrados: " + todosLibros.size());
        System.out.println("\nDistribución por idioma:");

        estadisticas.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " libros");
                });

        // Mostrar porcentajes
        System.out.println("\nPorcentajes:");
        estadisticas.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    double porcentaje = (entry.getValue() * 100.0) / todosLibros.size();
                    System.out.printf("- %s: %.1f%% (%d libros)%n",
                            entry.getKey(), porcentaje, entry.getValue());
                });
    }

    private void buscarLibrosPorIdiomaEspecifico() {
        System.out.print("\nIngrese el código de idioma (ej: es, en, fr): ");
        String idioma = scanner.nextLine().toLowerCase();

        List<Libro> libros = libroService.buscarLibrosPorIdioma(idioma);
        long conteo = libros.size();

        System.out.println("\n=== RESULTADOS PARA IDIOMA '" + idioma + "' ===");
        System.out.println("Total de libros: " + conteo);

        if (conteo > 0) {
            System.out.println("\n=== LIBROS ENCONTRADOS ===");
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Descargas: " + libro.getDescargas());
                System.out.println("Autor: " + libro.getNombreAutor());
                System.out.println("--------------------");
            });
        }
    }

    private void mostrarTop10Libros() {
        System.out.println("\n=== TOP 10 LIBROS MÁS DESCARGADOS ===");
        List<Libro> topLibros = libroService.obtenerTop10Libros();

        if (topLibros.isEmpty()) {
            System.out.println("No hay libros registrados aún.");
        } else {
            for (int i = 0; i < topLibros.size(); i++) {
                Libro libro = topLibros.get(i);
                System.out.println((i+1) + ". " + libro.getTitulo() +
                        " - " + libro.getDescargas() + " descargas");
            }
        }
    }

    private void mostrarEstadisticasDescargas() {
        DoubleSummaryStatistics stats = libroService.obtenerEstadisticasDescargas();

        System.out.println("\n=== ESTADÍSTICAS DE DESCARGAS ===");
        System.out.println("Total de libros: " + stats.getCount());
        System.out.println("Mínimo de descargas: " + stats.getMin());
        System.out.println("Máximo de descargas: " + stats.getMax());
        System.out.printf("Promedio de descargas: %.2f%n", stats.getAverage());
        System.out.println("Suma total de descargas: " + stats.getSum());

        // Distribución por rangos de descargas
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        Map<String, Long> distribucion = libros.stream()
                .collect(Collectors.groupingBy(
                        libro -> {
                            long descargas = libro.getDescargas();
                            if (descargas < 100) return "0-99";
                            if (descargas < 500) return "100-499";
                            if (descargas < 1000) return "500-999";
                            return "1000+";
                        },
                        Collectors.counting()
                ));

        System.out.println("\nDistribución por rangos de descargas:");
        distribucion.forEach((rango, cantidad) ->
                System.out.printf("- %s: %d libros (%.1f%%)%n",
                        rango, cantidad, (cantidad * 100.0 / stats.getCount()))
        );
    }

    private void buscarAutoresPorRangoNacimiento() {
        try {
            System.out.print("\nIngrese año de inicio (deje vacío para no filtrar): ");
            String inicioStr = scanner.nextLine();
            Integer anioInicio = inicioStr.isEmpty() ? null : Integer.parseInt(inicioStr);

            System.out.print("Ingrese año de fin (deje vacío para no filtrar): ");
            String finStr = scanner.nextLine();
            Integer anioFin = finStr.isEmpty() ? null : Integer.parseInt(finStr);

            System.out.println("Buscando autores con nacimiento entre: " +
                    (anioInicio != null ? anioInicio : "cualquier año") + " y " +
                    (anioFin != null ? anioFin : "cualquier año"));

            List<Autor> autores = autorService.buscarAutoresPorRangoNacimiento(anioInicio, anioFin);

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores en ese rango.");
            } else {
                System.out.println("\n=== RESULTADOS DE BÚSQUEDA ===");
                autores.forEach(autor -> {
                    System.out.println("Autor: " + autor.getNombre());
                    System.out.println("Nacimiento: " +
                            (autor.getNacimiento() != null ? autor.getNacimiento() : "Desconocido"));
                    System.out.println("Fallecimiento: " +
                            (autor.getFallecimiento() != null ? autor.getFallecimiento() : "Desconocido"));
                    System.out.println("--------------------");
                });
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor ingrese años válidos o deje vacío.");
        }
    }
}
