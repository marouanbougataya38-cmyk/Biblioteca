package model;

/**
 * Represents a book in the library system.
 */
public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String editorial;
    private Genero genero;
    private int cantidadTotal;
    private int cantidadDisponible;
    private EstadoLibro estado;

    /**
     * Constructor for Libro.
     */
    public Libro(String isbn, String titulo, String autor, int anioPublicacion,
            String editorial, Genero genero, int cantidadTotal) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.editorial = editorial;
        this.genero = genero;
        this.cantidadTotal = cantidadTotal;
        this.cantidadDisponible = cantidadTotal;
        this.estado = EstadoLibro.DISPONIBLE;
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    // Setters
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }

    /**
     * Decrements available copies when a book is loaned.
     */
    public void decrementarDisponibles() {
        if (cantidadDisponible > 0) {
            cantidadDisponible--;
        }
        actualizarEstado();
    }

    /**
     * Increments available copies when a book is returned.
     */
    public void incrementarDisponibles() {
        if (cantidadDisponible < cantidadTotal) {
            cantidadDisponible++;
        }
        actualizarEstado();
    }

    /**
     * Updates the book status based on available copies.
     */
    private void actualizarEstado() {
        if (cantidadDisponible == 0) {
            if (estado != EstadoLibro.RESERVADO) {
                estado = EstadoLibro.PRESTADO;
            }
        } else {
            if (estado == EstadoLibro.PRESTADO) {
                estado = EstadoLibro.DISPONIBLE;
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                "ISBN: %s | Título: %s | Autor: %s | Año: %d | Editorial: %s | Género: %s | Disponibles: %d/%d | Estado: %s",
                isbn, titulo, autor, anioPublicacion, editorial, genero, cantidadDisponible, cantidadTotal, estado);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Libro libro = (Libro) obj;
        return isbn != null && isbn.equals(libro.isbn);
    }

    @Override
    public int hashCode() {
        return isbn != null ? isbn.hashCode() : 0;
    }
}
