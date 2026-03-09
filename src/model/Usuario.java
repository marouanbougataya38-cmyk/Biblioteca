package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the library system.
 */
public class Usuario {
    private String id;
    private String nombre;
    private List<Prestamo> prestamosActivos;
    private List<Prestamo> historialPrestamos;

    /**
     * Constructor for Usuario.
     */
    public Usuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.prestamosActivos = new ArrayList<>();
        this.historialPrestamos = new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Prestamo> getPrestamosActivos() {
        return prestamosActivos;
    }

    public List<Prestamo> getHistorialPrestamos() {
        return historialPrestamos;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Adds an active loan to the user.
     */
    public void agregarPrestamoActivo(Prestamo prestamo) {
        prestamosActivos.add(prestamo);
    }

    /**
     * Removes an active loan and adds it to history.
     */
    public void moverPrestamoAHistorial(Prestamo prestamo) {
        prestamosActivos.remove(prestamo);
        historialPrestamos.add(prestamo);
    }

    /**
     * Returns the number of active loans.
     */
    public int getNumeroPrestamosActivos() {
        return prestamosActivos.size();
    }

    /**
     * Checks if the user has reached the maximum loan limit (3 books).
     */
    public boolean tieneLimitePrestamosAlcanzado() {
        return prestamosActivos.size() >= 3;
    }

    /**
     * Checks if the user currently has a specific book on loan.
     */
    public boolean tieneLibroPrestado(Libro libro) {
        return prestamosActivos.stream()
                .anyMatch(p -> p.getLibro().equals(libro));
    }

    /**
     * Gets the active loan for a specific book.
     */
    public Prestamo getPrestamoActivo(Libro libro) {
        return prestamosActivos.stream()
                .filter(p -> p.getLibro().equals(libro))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if the user is blocked from borrowing a specific book
     * (if they had it for 30 days, they can't borrow it again for 7 days).
     */
    public boolean estaBloqueadoPara(Libro libro) {
        // Find the last loan of this book in history
        for (int i = historialPrestamos.size() - 1; i >= 0; i--) {
            Prestamo p = historialPrestamos.get(i);
            if (p.getLibro().equals(libro)) {
                LocalDate fechaDevolucion = p.getFechaDevolucion();
                if (fechaDevolucion != null) {
                    LocalDate fechaDesbloqueo = fechaDevolucion.plusDays(7);
                    if (LocalDate.now().isBefore(fechaDesbloqueo)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Gets the count of books currently loaned to the user.
     */
    public int getLibrosPrestadosCount() {
        return prestamosActivos.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(" | Nombre: ").append(nombre);
        sb.append(" | Libros Prestados: ").append(prestamosActivos.size()).append("/3");
        if (!prestamosActivos.isEmpty()) {
            sb.append(" | Libros: ");
            prestamosActivos.forEach(p -> sb.append(p.getLibro().getTitulo()).append(", "));
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Usuario usuario = (Usuario) obj;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
