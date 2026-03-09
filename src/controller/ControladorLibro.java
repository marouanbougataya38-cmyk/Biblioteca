package controller;

import model.Genero;
import model.Libro;
import model.EstadoLibro;
import exception.LibroNoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing books in the library.
 */
public class ControladorLibro {
    private List<Libro> libros;

    /**
     * Constructor for ControladorLibro.
     */
    public ControladorLibro() {
        this.libros = new ArrayList<>();
    }

    /**
     * Adds a new book to the library.
     */
    public void agregarLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser null");
        }
        libros.add(libro);
    }

    /**
     * Searches for a book by ISBN.
     */
    public Libro buscarPorIsbn(String isbn) throws LibroNoEncontradoException {
        return libros.stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new LibroNoEncontradoException(isbn,
                        "No se encontró ningún libro con ISBN: " + isbn));
    }

    /**
     * Searches for books by title (partial match, case-insensitive).
     */
    public List<Libro> buscarPorTitulo(String titulo) {
        return libros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Searches for books by genre.
     */
    public List<Libro> buscarPorGenero(Genero genero) {
        return libros.stream()
                .filter(l -> l.getGenero() == genero)
                .collect(Collectors.toList());
    }

    /**
     * Returns all books in the library.
     */
    public List<Libro> getTodosLosLibros() {
        return new ArrayList<>(libros);
    }

    /**
     * Returns all available books.
     */
    public List<Libro> getLibrosDisponibles() {
        return libros.stream()
                .filter(l -> l.getEstado() == EstadoLibro.DISPONIBLE)
                .collect(Collectors.toList());
    }

    /**
     * Returns all loaned books.
     */
    public List<Libro> getLibrosPrestados() {
        return libros.stream()
                .filter(l -> l.getEstado() == EstadoLibro.PRESTADO)
                .collect(Collectors.toList());
    }

    /**
     * Returns all reserved books.
     */
    public List<Libro> getLibrosReservados() {
        return libros.stream()
                .filter(l -> l.getEstado() == EstadoLibro.RESERVADO)
                .collect(Collectors.toList());
    }

    /**
     * Checks if a book exists by ISBN.
     */
    public boolean existeLibro(String isbn) {
        return libros.stream().anyMatch(l -> l.getIsbn().equals(isbn));
    }

    /**
     * Gets the total number of books.
     */
    public int getTotalLibros() {
        return libros.size();
    }

    /**
     * Gets the number of available books.
     */
    public int getTotalDisponibles() {
        return (int) libros.stream()
                .filter(l -> l.getEstado() == EstadoLibro.DISPONIBLE)
                .count();
    }

    /**
     * Updates book information.
     */
    public void actualizarLibro(Libro libro) throws LibroNoEncontradoException {
        int index = -1;
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIsbn().equals(libro.getIsbn())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new LibroNoEncontradoException(libro.getIsbn());
        }
        libros.set(index, libro);
    }

    /**
     * Removes a book from the library by ISBN.
     */
    public boolean eliminarLibro(String isbn) {
        return libros.removeIf(l -> l.getIsbn().equals(isbn));
    }
}
