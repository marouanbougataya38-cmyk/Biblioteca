package exception;

import java.time.LocalDate;

/**
 * Exception thrown when a user is blocked from borrowing a specific book.
 * This happens when a user has had a book for 30 days and cannot borrow it
 * again
 * until 7 days have passed since the return.
 */
public class UsuarioBloqueadoException extends Exception {
    private String usuarioId;
    private String isbn;
    private LocalDate fechaDesbloqueo;

    public UsuarioBloqueadoException(String usuarioId, String isbn, LocalDate fechaDesbloqueo) {
        super("El usuario con ID " + usuarioId + " está bloqueado para solicitar el libro con ISBN " +
                isbn + ". Podrá solicitarlo nuevamente a partir del " + fechaDesbloqueo);
        this.usuarioId = usuarioId;
        this.isbn = isbn;
        this.fechaDesbloqueo = fechaDesbloqueo;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getFechaDesbloqueo() {
        return fechaDesbloqueo;
    }
}
