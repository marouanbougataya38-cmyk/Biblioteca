package exception;

/**
 * Exception thrown when a user is not found in the library system.
 */
public class UsuarioNoEncontradoException extends Exception {
    private String usuarioId;

    public UsuarioNoEncontradoException(String usuarioId) {
        super("No se encontró ningún usuario con el ID: " + usuarioId);
        this.usuarioId = usuarioId;
    }

    public UsuarioNoEncontradoException(String usuarioId, String mensaje) {
        super(mensaje);
        this.usuarioId = usuarioId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }
}
