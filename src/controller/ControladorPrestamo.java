 package controller;

import model.*;
import exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing loans in the library.
 */
public class ControladorPrestamo {
    private List<Prestamo> prestamos;
    private ControladorLibro controladorLibro;
    private ControladorUsuario controladorUsuario;

    /**
     * Constructor for ControladorPrestamo.
     */
    public ControladorPrestamo(ControladorLibro controladorLibro, ControladorUsuario controladorUsuario) {
        this.prestamos = new ArrayList<>();
        this.controladorLibro = controladorLibro;
        this.controladorUsuario = controladorUsuario;
    }

    /**
     * Loans a book to a user with all validations.
     * 
     * @throws IllegalArgumentException         if the book is already loaned or
     *                                          reserved
     * @throws LimitePrestamosExcedidoException if user has 3 active loans
     * @throws UsuarioBloqueadoException        if user had the book for 30 days and
     *                                          can't borrow for 7 more
     * @throws LibroNoEncontradoException       if book doesn't exist
     * @throws UsuarioNoEncontradoException     if user doesn't exist
     */
    public Prestamo prestarLibro(String isbn, String usuarioId)
            throws LibroNoEncontradoException, UsuarioNoEncontradoException,
            LibroNoDisponibleException, LimitePrestamosExcedidoException,
            UsuarioBloqueadoException {

        // Validate book exists
        Libro libro = controladorLibro.buscarPorIsbn(isbn);

        // Validate user exists
        Usuario usuario = controladorUsuario.buscarPorId(usuarioId);

        // Validate book is available (not loaned or reserved)
        if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
            throw new IllegalArgumentException(
                    "El libro '" + libro.getTitulo() + "' ya está " + libro.getEstado().toString().toLowerCase());
        }

        // Validate user hasn't reached loan limit
        if (usuario.tieneLimitePrestamosAlcanzado()) {
            throw new LimitePrestamosExcedidoException(usuarioId, usuario.getNumeroPrestamosActivos());
        }

        // Validate user is not blocked for this book
        if (usuario.estaBloqueadoPara(libro)) {
            // Find the last loan to get the unlock date
            LocalDate fechaDesbloqueo = null;
            for (int i = usuario.getHistorialPrestamos().size() - 1; i >= 0; i--) {
                Prestamo p = usuario.getHistorialPrestamos().get(i);
                if (p.getLibro().equals(libro) && p.getFechaDevolucion() != null) {
                    fechaDesbloqueo = p.getFechaDevolucion().plusDays(Prestamo.DIAS_BLOQUEO_DESPUES_DEVOLUCION);
                    break;
                }
            }
            throw new UsuarioBloqueadoException(usuarioId, isbn, fechaDesbloqueo);
        }

        // Check if user already has this specific book
        if (usuario.tieneLibroPrestado(libro)) {
            throw new IllegalArgumentException("El usuario ya tiene prestado este libro");
        }

        // Create the loan
        Prestamo prestamo = new Prestamo(libro, usuario);

        // Update book status
        libro.decrementarDisponibles();

        // Add loan to user's active loans
        usuario.agregarPrestamoActivo(prestamo);

        // Add to global loan list
        prestamos.add(prestamo);

        return prestamo;
    }

    /**
     * Returns a loaned book.
     * 
     * @throws IllegalArgumentException     if the book is not currently loaned
     * @throws LibroNoEncontradoException   if book doesn't exist
     * @throws UsuarioNoEncontradoException if user doesn't exist
     */
    public Prestamo devolverLibro(String isbn, String usuarioId)
            throws LibroNoEncontradoException, UsuarioNoEncontradoException {

        // Validate book exists
        Libro libro = controladorLibro.buscarPorIsbn(isbn);

        // Validate user exists
        Usuario usuario = controladorUsuario.buscarPorId(usuarioId);

        // Find the active loan
        Prestamo prestamo = usuario.getPrestamoActivo(libro);
        if (prestamo == null) {
            throw new IllegalArgumentException(
                    "El libro '" + libro.getTitulo() + "' no está prestado al usuario '" + usuario.getNombre() + "'");
        }

        // Mark as returned
        prestamo.marcarDevuelto();

        // Update book status
        libro.incrementarDisponibles();

        // Move from active to history
        usuario.moverPrestamoAHistorial(prestamo);

        return prestamo;
    }

    /**
     * Reserves a book for a user.
     */
    public void reservarLibro(String isbn, String usuarioId)
            throws LibroNoEncontradoException, UsuarioNoEncontradoException, IllegalArgumentException {

        // Validate book exists
        Libro libro = controladorLibro.buscarPorIsbn(isbn);

        // Validate user exists
        controladorUsuario.buscarPorId(usuarioId);

        // Validate book is available
        if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
            throw new IllegalArgumentException(
                    "El libro '" + libro.getTitulo() + "' no está disponible para reservar");
        }

        // Reserve the book
        libro.setEstado(EstadoLibro.RESERVADO);
    }

    /**
     * Cancels a reservation.
     */
    public void cancelarReserva(String isbn)
            throws LibroNoEncontradoException, IllegalArgumentException {

        // Validate book exists
        Libro libro = controladorLibro.buscarPorIsbn(isbn);

        // Validate book is reserved
        if (libro.getEstado() != EstadoLibro.RESERVADO) {
            throw new IllegalArgumentException(
                    "El libro '" + libro.getTitulo() + "' no está reservado");
        }

        // Cancel reservation and update status
        libro.setEstado(EstadoLibro.DISPONIBLE);
    }

    /**
     * Gets all active loans.
     */
    public List<Prestamo> getPrestamosActivos() {
        return prestamos.stream()
                .filter(p -> !p.isDevuelto())
                .collect(Collectors.toList());
    }

    /**
     * Gets all loans (active and history).
     */
    public List<Prestamo> getTodosLosPrestamos() {
        return new ArrayList<>(prestamos);
    }

    /**
     * Finds which user has a specific book currently loaned.
     */
    public Usuario getUsuarioConLibro(String isbn) throws LibroNoEncontradoException {
        Libro libro = controladorLibro.buscarPorIsbn(isbn);

        for (Prestamo p : prestamos) {
            if (p.getLibro().equals(libro) && !p.isDevuelto()) {
                return p.getUsuario();
            }
        }
        return null;
    }

    /**
     * Gets overdue loans.
     */
    public List<Prestamo> getPrestamosVencidos() {
        return prestamos.stream()
                .filter(p -> !p.isDevuelto() && p.isVencido())
                .collect(Collectors.toList());
    }

    /**
     * Gets loans for a specific user.
     */
    public List<Prestamo> getPrestamosUsuario(String usuarioId)
            throws UsuarioNoEncontradoException {
        Usuario usuario = controladorUsuario.buscarPorId(usuarioId);
        return prestamos.stream()
                .filter(p -> p.getUsuario().equals(usuario))
                .collect(Collectors.toList());
    }

    /**
     * Gets loans for a specific book.
     */
    public List<Prestamo> getPrestamosPorLibro(String isbn)
            throws LibroNoEncontradoException {
        Libro libro = controladorLibro.buscarPorIsbn(isbn);
        return prestamos.stream()
                .filter(p -> p.getLibro().equals(libro))
                .collect(Collectors.toList());
    }

    /**
     * Renews a loan (extends due date by 30 days).
     */
    public Prestamo renovarPrestamo(String isbn, String usuarioId)
            throws LibroNoEncontradoException, UsuarioNoEncontradoException, IllegalArgumentException {

        // Validate book exists
        Libro libro = controladorLibro.buscarPorIsbn(isbn);

        // Validate user exists
        Usuario usuario = controladorUsuario.buscarPorId(usuarioId);

        // Find the active loan
        Prestamo prestamo = usuario.getPrestamoActivo(libro);
        if (prestamo == null) {
            throw new IllegalArgumentException(
                    "El libro no está prestado al usuario");
        }

        // Extend due date
        prestamo.setFechaVencimiento(LocalDate.now().plusDays(Prestamo.DIAS_MAXIMO_PRESTAMO));

        return prestamo;
    }
}
