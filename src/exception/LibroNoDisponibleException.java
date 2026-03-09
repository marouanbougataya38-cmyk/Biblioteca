package exception;

/**
 * Exception thrown when a book is not available for loan.
 */
public class LibroNoDisponibleException extends Exception {
    private String isbn;

    public LibroNoDisponibleException(String isbn) {
        super("El libro con ISBN " + isbn + " no está disponible para préstamo.");
        this.isbn = isbn;
    }

    public LibroNoDisponibleException(String isbn, String mensaje) {
        super(mensaje);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
