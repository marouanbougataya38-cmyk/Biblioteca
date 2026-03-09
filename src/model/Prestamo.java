package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a loan in the library system.
 */
public class Prestamo {
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDevolucion;

    // Constants
    public static final int DIAS_MAXIMO_PRESTAMO = 30;
    public static final int DIAS_BLOQUEO_DESPUES_DEVOLUCION = 7;

    /**
     * Constructor for Prestamo.
     */
    public Prestamo(Libro libro, Usuario usuario) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaVencimiento = fechaPrestamo.plusDays(DIAS_MAXIMO_PRESTAMO);
        this.fechaDevolucion = null;
    }

    // Getters
    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    // Setters
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * Checks if the loan is overdue.
     */
    public boolean isVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    /**
     * Checks if the loan has exceeded 30 days (for blocking purposes).
     */
    public boolean excedio30Dias() {
        long dias = ChronoUnit.DAYS.between(fechaPrestamo, LocalDate.now());
        return dias >= DIAS_MAXIMO_PRESTAMO;
    }

    /**
     * Returns the number of days until the loan is due (negative if overdue).
     */
    public long getDiasRestantes() {
        return ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
    }

    /**
     * Returns the total days the book has been on loan.
     */
    public long getDiasTranscurridos() {
        LocalDate fechaFin = fechaDevolucion != null ? fechaDevolucion : LocalDate.now();
        return ChronoUnit.DAYS.between(fechaPrestamo, fechaFin);
    }

    /**
     * Marks the loan as returned.
     */
    public void marcarDevuelto() {
        this.fechaDevolucion = LocalDate.now();
    }

    /**
     * Checks if the loan has been returned.
     */
    public boolean isDevuelto() {
        return fechaDevolucion != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Libro: ").append(libro.getTitulo());
        sb.append(" | Usuario: ").append(usuario.getNombre());
        sb.append(" | Fecha Préstamo: ").append(fechaPrestamo);
        sb.append(" | Fecha Vencimiento: ").append(fechaVencimiento);

        if (fechaDevolucion != null) {
            sb.append(" | Fecha Devolución: ").append(fechaDevolucion);
            sb.append(" | Días: ").append(getDiasTranscurridos());
        } else {
            sb.append(" | Estado: ").append(isVencido() ? "VENCIDO" : "Activo");
            sb.append(" | Días restantes: ").append(getDiasRestantes());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Prestamo prestamo = (Prestamo) obj;
        return libro.equals(prestamo.libro) && usuario.equals(prestamo.usuario)
                && fechaPrestamo.equals(prestamo.fechaPrestamo);
    }

    @Override
    public int hashCode() {
        int result = libro.hashCode();
        result = 31 * result + usuario.hashCode();
        result = 31 * result + fechaPrestamo.hashCode();
        return result;
    }
}
