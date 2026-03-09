package controller;

import model.*;
import exception.*;

import java.util.List;

/**
 * Main coordinator for the library system.
 * Coordinates interaction between controllers.
 */
public class GestorBiblioteca {
    private ControladorLibro controladorLibro;
    private ControladorUsuario controladorUsuario;
    private ControladorPrestamo controladorPrestamo;

    /**
     * Constructor for GestorBiblioteca.
     */
    public GestorBiblioteca() {
        this.controladorLibro = new ControladorLibro();
        this.controladorUsuario = new ControladorUsuario();
        this.controladorPrestamo = new ControladorPrestamo(controladorLibro, controladorUsuario);
    }

    //  BOOK METHODS 

    /**
     * Adds a new book to the library.
     */
    public void agregarLibro(Libro libro) {
        controladorLibro.agregarLibro(libro);
    }

    /**
     * Searches for a book by ISBN.
     */
    public Libro buscarLibroPorIsbn(String isbn) throws LibroNoEncontradoException {
        return controladorLibro.buscarPorIsbn(isbn);
    }

    /**
     * Searches for books by title.
     */
    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        return controladorLibro.buscarPorTitulo(titulo);
    }

    /**
     * Searches for books by genre.
     */
    public List<Libro> buscarLibrosPorGenero(Genero genero) {
        return controladorLibro.buscarPorGenero(genero);
    }

    /**
     * Gets all books.
     */
    public List<Libro> getTodosLosLibros() {
        return controladorLibro.getTodosLosLibros();
    }

    /**
     * Gets available books.
     */
    public List<Libro> getLibrosDisponibles() {
        return controladorLibro.getLibrosDisponibles();
    }

    // USER METHODS

    /**
     * Adds a new user.
     */
    public void agregarUsuario(Usuario usuario) {
        controladorUsuario.agregarUsuario(usuario);
    }

    /**
     * Searches for a user by ID.
     */
    public Usuario buscarUsuarioPorId(String id) throws UsuarioNoEncontradoException {
        return controladorUsuario.buscarPorId(id);
    }

    /**
     * Gets all users.
     */
    public List<Usuario> getTodosLosUsuarios() {
        return controladorUsuario.getTodosLosUsuarios();
    }

    // LOAN METHODS 

    /**
     * Loans a book to a user.
     */
    public Prestamo prestarLibro(String isbn, String usuarioId)
            throws LibroNoEncontradoException, UsuarioNoEncontradoException,
            LibroNoDisponibleException, LimitePrestamosExcedidoException,
            UsuarioBloqueadoException {
        return controladorPrestamo.prestarLibro(isbn, isbn);
    }

    /**
     * Returns a book.
     */
    public Prestamo devolverLibro(String isbn, String usuarioId)
            throws LibroNoEncontradoException, UsuarioNoEncontradoException {
        return controladorPrestamo.devolverLibro(isbn, usuarioId);
    }

    /**
     * Reserves a book.
     */
    public void reservarLibro(String isbn, String usuarioId)
            throws LibroNoEncontradoException, UsuarioNoEncontradoException {
        controladorPrestamo.reservarLibro(isbn, isbn);
    }

    /**
     * Cancels a reservation.
     */
    public void cancelarReserva(String isbn) throws LibroNoEncontradoException {
        controladorPrestamo.cancelarReserva(isbn);
    }

    /**
     * Gets which user has a book.
     */
    public Usuario getUsuarioConLibro(String isbn) throws LibroNoEncontradoException {
        return controladorPrestamo.getUsuarioConLibro(isbn);
    }

    /**
     * Gets all active loans.
     */
    public List<Prestamo> getPrestamosActivos() {
        return controladorPrestamo.getPrestamosActivos();
    }

    /**
     * Gets all loans.
     */
    public List<Prestamo> getTodosLosPrestamos() {
        return controladorPrestamo.getTodosLosPrestamos();
    }

    /**
     * Gets overdue loans.
     */
    public List<Prestamo> getPrestamosVencidos() {
        return controladorPrestamo.getPrestamosVencidos();
    }

    // SUMMARY METHODS

    /**
     * Prints a summary of the library.
     */
    public void mostrarResumenBiblioteca() {
        System.out.println("\n========================================");
        System.out.println("        RESUMEN DE LA BIBLIOTECA        ");
        System.out.println("========================================");

        // Book summary
        List<Libro> todosLibros = getTodosLosLibros();
        System.out.println("\n📚 RESUMEN DE LIBROS:");
        System.out.println("   Total de libros: " + todosLibros.size());
        System.out.println("   Disponibles: " + getLibrosDisponibles().size());

        int prestados = 0;
        int reservados = 0;
        for (Libro l : todosLibros) {
            if (l.getEstado() == EstadoLibro.PRESTADO)
                prestados++;
            if (l.getEstado() == EstadoLibro.RESERVADO)
                reservados++;
        }
        System.out.println("   Prestados: " + prestados);
        System.out.println("   Reservados: " + reservados);

        // User summary
        List<Usuario> todosUsuarios = getTodosLosUsuarios();
        System.out.println("\n👤 RESUMEN DE USUARIOS:");
        System.out.println("   Total de usuarios: " + todosUsuarios.size());

        int usuariosConPrestamos = 0;
        for (Usuario u : todosUsuarios) {
            if (u.getNumeroPrestamosActivos() > 0) {
                usuariosConPrestamos++;
            }
        }
        System.out.println("   Usuarios con préstamos: " + usuariosConPrestamos);

        // Active loans
        List<Prestamo> prestamosActivos = getPrestamosActivos();
        System.out.println("\n📋 PRÉSTAMOS ACTIVOS: " + prestamosActivos.size());

        System.out.println("========================================\n");
    }

    /**
     * Lists all books with their current status.
     */
    public void listarTodosLosLibros() {
        List<Libro> libros = getTodosLosLibros();

        System.out.println("\n========================================");
        System.out.println("         LISTADO DE LIBROS              ");
        System.out.println("========================================");

        if (libros.isEmpty()) {
            System.out.println("No hay libros en la biblioteca.");
        } else {
            for (Libro l : libros) {
                System.out.println(l);
            }
        }
        System.out.println("========================================\n");
    }

    /**
     * Lists all users with their active loans.
     */
    public void listarTodosLosUsuarios() {
        List<Usuario> usuarios = getTodosLosUsuarios();

        System.out.println("\n========================================");
        System.out.println("         LISTADO DE USUARIOS            ");
        System.out.println("========================================");

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario u : usuarios) {
                System.out.println(u);
                if (!u.getPrestamosActivos().isEmpty()) {
                    System.out.println("   Libros prestados:");
                    for (Prestamo p : u.getPrestamosActivos()) {
                        System.out.println("   - " + p.getLibro().getTitulo() +
                                " (Vence: " + p.getFechaVencimiento() + ")");
                    }
                }
                System.out.println();
            }
        }
        System.out.println("========================================\n");
    }

    // GETTERS

    public ControladorLibro getControladorLibro() {
        return controladorLibro;
    }

    public ControladorUsuario getControladorUsuario() {
        return controladorUsuario;
    }

    public ControladorPrestamo getControladorPrestamo() {
        return controladorPrestamo;
    }
}
