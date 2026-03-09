package exception;

/**
 * Exception thrown when a user exceeds the maximum loan limit (3 books).
 */
public class LimitePrestamosExcedidoException extends Exception {
    private String usuarioId;
    private int prestamosActuales;

    public LimitePrestamosExcedidoException(String usuarioId, int prestamosActuales) {
        super("El usuario con ID " + usuarioId + " ha excedido el límite de préstamos (3 libros). " +
                "Actualmente tiene " + prestamosActuales + " préstamos activos.");
        this.usuarioId = usuarioId;
        this.prestamosActuales = prestamosActuales;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public int getPrestamosActuales() {
        return prestamosActuales;
    }
}
