import view.Consola;

/**
 * Main entry point for the Library Management System.
 */
public class Main {
    public static void main(String[] args) {
        Consola consola = new Consola();

        // Create demo data
        consola.crearDatosDemo();

        // Start the console interface
        consola.iniciar();
    }
}
