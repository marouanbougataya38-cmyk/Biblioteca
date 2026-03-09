package exception;

/**
 * Exception thrown when a book is not found in the library.
 */
public class LibroNoEncontradoException extends Exception {
    private String criterio;

    public LibroNoEncontradoException(String criterio) {
        super("No se encontró ningún libro con el criterio: " + criterio);
        this.criterio = criterio;
    }

    public LibroNoEncontradoException(String criterio, String mensaje) {
        super(mensaje);
        this.criterio = criterio;
    }

    public String getCriterio() {
        return criterio;
    }
}
