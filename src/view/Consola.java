package view;

import controller.GestorBiblioteca;
import model.*;
import exception.*;

import java.util.List;
import java.util.Scanner;

/**
 * Console interface for the library system.
 */
public class Consola {
    private GestorBiblioteca gestor;
    private Scanner scanner;

    /**
     * Constructor for Consola.
     */
    public Consola() {
        this.gestor = new GestorBiblioteca();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the main menu loop.
     */
    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    menuLibros();
                    break;
                case 2:
                    menuUsuarios();
                    break;
                case 3:
                    menuPrestamos();
                    break;
                case 4:
                    menuBusquedas();
                    break;
                case 5:
                    gestor.mostrarResumenBiblioteca();
                    break;
                case 0:
                    System.out.println("¡Gracias por usar el sistema de biblioteca!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    /**
     * Shows the main menu.
     */
    private void mostrarMenuPrincipal() {
        System.out.println("\n========================================");
        System.out.println("     SISTEMA DE GESTIÓN DE BIBLIOTECA    ");
        System.out.println("========================================");
        System.out.println("1. Gestión de Libros");
        System.out.println("2. Gestión de Usuarios");
        System.out.println("3. Gestión de Préstamos");
        System.out.println("4. Búsquedas");
        System.out.println("5. Resumen de Biblioteca");
        System.out.println("0. Salir");
        System.out.println("========================================");
    }

    /**
     * Book management menu.
     */
    private void menuLibros() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE LIBROS ---");
            System.out.println("1. Agregar libro");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar libros disponibles");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarLibro();
                    break;
                case 2:
                    gestor.listarTodosLosLibros();
                    break;
                case 3:
                    listarLibrosDisponibles();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    /**
     * User management menu.
     */
    private void menuUsuarios() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Listar todos los usuarios");
            System.out.println("3. Ver préstamos de un usuario");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarUsuario();
                    break;
                case 2:
                    gestor.listarTodosLosUsuarios();
                    break;
                case 3:
                    verPrestamosUsuario();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    /**
     * Loan management menu.
     */
    private void menuPrestamos() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE PRÉSTAMOS ---");
            System.out.println("1. Prestar libro");
            System.out.println("2. Devolver libro");
            System.out.println("3. Reservar libro");
            System.out.println("4. Cancelar reserva");
            System.out.println("5. Listar préstamos activos");
            System.out.println("6. Ver quién tiene un libro");
            System.out.println("7. Préstamos vencidos");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    prestarLibro();
                    break;
                case 2:
                    devolverLibro();
                    break;
                case 3:
                    reservarLibro();
                    break;
                case 4:
                    cancelarReserva();
                    break;
                case 5:
                    listarPrestamosActivos();
                    break;
                case 6:
                    buscarUsuarioConLibro();
                    break;
                case 7:
                    listarPrestamosVencidos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    /**
     * Search menu.
     */
    private void menuBusquedas() {
        int opcion;
        do {
            System.out.println("\n--- BÚSQUEDAS ---");
            System.out.println("1. Buscar por título");
            System.out.println("2. Buscar por ISBN");
            System.out.println("3. Buscar por género");
            System.out.println("0. Volver");

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    buscarPorTitulo();
                    break;
                case 2:
                    buscarPorIsbn();
                    break;
                case 3:
                    buscarPorGenero();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // BOOK METHODS

    /**
     * Adds a new book to the library.
     */
    private void agregarLibro() {
        System.out.println("\n--- AGREGAR LIBRO ---");

        String isbn = leerTexto("ISBN: ");
        String titulo = leerTexto("Título: ");
        String autor = leerTexto("Autor: ");
        int anio = leerEntero("Año de publicación: ");
        String editorial = leerTexto("Editorial: ");

        System.out.println("Géneros disponibles:");
        Genero[] generos = Genero.values();
        for (int i = 0; i < generos.length; i++) {
            System.out.println((i + 1) + ". " + generos[i]);
        }
        int genOpcion = leerEntero("Seleccione género: ") - 1;

        int cantidad = leerEntero("Cantidad de copias: ");

        try {
            Genero genero = generos[genOpcion];
            Libro libro = new Libro(isbn, titulo, autor, anio, editorial, genero, cantidad);
            gestor.agregarLibro(libro);
            System.out.println("✅ Libro agregado exitosamente!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("❌ Género inválido.");
        }
    }

    /**
     * Lists available books.
     */
    private void listarLibrosDisponibles() {
        List<Libro> libros = gestor.getLibrosDisponibles();

        System.out.println("\n--- LIBROS DISPONIBLES ---");
        if (libros.isEmpty()) {
            System.out.println("No hay libros disponibles.");
        } else {
            for (Libro l : libros) {
                System.out.println(l);
            }
        }
    }

    //  USER METHODS

    /**
     * Adds a new user.
     */
    private void agregarUsuario() {
        System.out.println("\n--- AGREGAR USUARIO ---");

        String id = leerTexto("ID de usuario: ");
        String nombre = leerTexto("Nombre: ");

        try {
            Usuario usuario = new Usuario(id, nombre);
            gestor.agregarUsuario(usuario);
            System.out.println("✅ Usuario agregado exitosamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Views loans for a specific user.
     */
    private void verPrestamosUsuario() {
        System.out.println("\n--- PRÉSTAMOS DE USUARIO ---");
        String id = leerTexto("ID de usuario: ");

        try {
            Usuario usuario = gestor.buscarUsuarioPorId(id);
            System.out.println("\nUsuario: " + usuario.getNombre());
            System.out.println("Préstamos activos: " + usuario.getNumeroPrestamosActivos() + "/3");

            if (!usuario.getPrestamosActivos().isEmpty()) {
                System.out.println("\nLibros prestados:");
                for (Prestamo p : usuario.getPrestamosActivos()) {
                    System.out.println("  - " + p.getLibro().getTitulo());
                    System.out.println("    Vence: " + p.getFechaVencimiento());
                    System.out.println("    Estado: " + (p.isVencido() ? "VENCIDO" : "Activo"));
                }
            }

            if (!usuario.getHistorialPrestamos().isEmpty()) {
                System.out.println("\nHistorial de préstamos: " + usuario.getHistorialPrestamos().size() + " libros");
            }
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    //  LOAN METHODS

    /**
     * Loans a book to a user.
     */
    private void prestarLibro() {
        System.out.println("\n--- PRESTAR LIBRO ---");

        String isbn = leerTexto("ISBN del libro: ");
        String usuarioId = leerTexto("ID del usuario: ");

        try {
            Prestamo prestamo = gestor.prestarLibro(isbn, usuarioId);
            System.out.println("✅ Préstamo realizado exitosamente!");
            System.out.println("Fecha de vencimiento: " + prestamo.getFechaVencimiento());
        } catch (LibroNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (LibroNoDisponibleException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (LimitePrestamosExcedidoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (UsuarioBloqueadoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Returns a loaned book.
     */
    private void devolverLibro() {
        System.out.println("\n--- DEVOLVER LIBRO ---");

        String isbn = leerTexto("ISBN del libro: ");
        String usuarioId = leerTexto("ID del usuario: ");

        try {
            Prestamo prestamo = gestor.devolverLibro(isbn, usuarioId);
            System.out.println("✅ Libro devuelto exitosamente!");
            System.out.println("Días prestado: " + prestamo.getDiasTranscurridos());
        } catch (LibroNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Reserves a book.
     */
    private void reservarLibro() {
        System.out.println("\n--- RESERVAR LIBRO ---");

        String isbn = leerTexto("ISBN del libro: ");
        String usuarioId = leerTexto("ID del usuario: ");

        try {
            gestor.reservarLibro(isbn, usuarioId);
            System.out.println("✅ Reserva realizada exitosamente!");
        } catch (LibroNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Cancels a reservation.
     */
    private void cancelarReserva() {
        System.out.println("\n--- CANCELAR RESERVA ---");

        String isbn = leerTexto("ISBN del libro: ");

        try {
            gestor.cancelarReserva(isbn);
            System.out.println("✅ Reserva cancelada exitosamente!");
        } catch (LibroNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Lists all active loans.
     */
    private void listarPrestamosActivos() {
        List<Prestamo> prestamos = gestor.getPrestamosActivos();

        System.out.println("\n--- PRÉSTAMOS ACTIVOS ---");
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos activos.");
        } else {
            for (Prestamo p : prestamos) {
                System.out.println(p);
            }
        }
    }

    /**
     * Finds which user has a specific book.
     */
    private void buscarUsuarioConLibro() {
        System.out.println("\n--- BUSCAR USUARIO CON LIBRO ---");

        String isbn = leerTexto("ISBN del libro: ");

        try {
            Usuario usuario = gestor.getUsuarioConLibro(isbn);
            if (usuario != null) {
                System.out
                        .println("El libro está prestado a: " + usuario.getNombre() + " (ID: " + usuario.getId() + ")");
            } else {
                System.out.println("El libro no está prestado actualmente.");
            }
        } catch (LibroNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Lists overdue loans.
     */
    private void listarPrestamosVencidos() {
        List<Prestamo> prestamos = gestor.getPrestamosVencidos();

        System.out.println("\n--- PRÉSTAMOS VENCIDOS ---");
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos vencidos.");
        } else {
            for (Prestamo p : prestamos) {
                System.out.println(p);
            }
        }
    }

    // SEARCH METHODS

    /**
     * Searches books by title.
     */
    private void buscarPorTitulo() {
        String titulo = leerTexto("Título a buscar: ");

        List<Libro> libros = gestor.buscarLibrosPorTitulo(titulo);

        System.out.println("\n--- RESULTADOS DE BÚSQUEDA ---");
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros.");
        } else {
            for (Libro l : libros) {
                System.out.println(l);
            }
        }
    }

    /**
     * Searches a book by ISBN.
     */
    private void buscarPorIsbn() {
        String isbn = leerTexto("ISBN a buscar: ");

        try {
            Libro libro = gestor.buscarLibroPorIsbn(isbn);
            System.out.println("\n--- RESULTADO ---");
            System.out.println(libro);
        } catch (LibroNoEncontradoException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Searches books by genre.
     */
    private void buscarPorGenero() {
        System.out.println("Géneros disponibles:");
        Genero[] generos = Genero.values();
        for (int i = 0; i < generos.length; i++) {
            System.out.println((i + 1) + ". " + generos[i]);
        }

        int opcion = leerEntero("Seleccione género: ") - 1;

        try {
            Genero genero = generos[opcion];
            List<Libro> libros = gestor.buscarLibrosPorGenero(genero);

            System.out.println("\n--- RESULTADOS ---");
            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros.");
            } else {
                for (Libro l : libros) {
                    System.out.println(l);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("❌ Género inválido.");
        }
    }

    // HELPER METHODS 

    /**
     * Reads an integer from console.
     */
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Reads a string from console.
     */
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Creates sample data for testing.
     */
    public void crearDatosDemo() {
        // Create books
        gestor.agregarLibro(new Libro("978-84-663-1234-5", "El Quijote", "Miguel de Cervantes", 1605, "Santillana",
                Genero.NOVELA, 3));
        gestor.agregarLibro(
                new Libro("978-84-450-5678-9", "1984", "George Orwell", 1949, "Planeta", Genero.CIENCIA_FICCION, 2));
        gestor.agregarLibro(
                new Libro("978-84-344-9012-3", "Harry Potter", "J.K. Rowling", 1997, "Salamandra", Genero.FANTASIA, 5));
        gestor.agregarLibro(
                new Libro("978-84-9187-234-1", "El Código Da Vinci", "Dan Brown", 2003, "Umbriel", Genero.MISTERIO, 3));
        gestor.agregarLibro(new Libro("978-84-9999-456-7", "La Historia", "Varios", 2020, "National Geographic",
                Genero.HISTORIA, 2));

        // Create users
        gestor.agregarUsuario(new Usuario("U001", "Juan Pérez"));
        gestor.agregarUsuario(new Usuario("U002", "María García"));
        gestor.agregarUsuario(new Usuario("U003", "Carlos López"));

        System.out.println("✅ Datos de demostración creados.");
    }
}
