package controller;

import model.Usuario;
import model.Prestamo;
import exception.UsuarioNoEncontradoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing users in the library.
 */
public class ControladorUsuario {
    private List<Usuario> usuarios;

    /**
     * Constructor for ControladorUsuario.
     */
    public ControladorUsuario() {
        this.usuarios = new ArrayList<>();
    }

    /**
     * Adds a new user to the library system.
     */
    public void agregarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        if (existeUsuario(usuario.getId())) {
            throw new IllegalArgumentException("Ya existe un usuario con el ID: " + usuario.getId());
        }
        usuarios.add(usuario);
    }

    /**
     * Searches for a user by ID.
     */
    public Usuario buscarPorId(String id) throws UsuarioNoEncontradoException {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UsuarioNoEncontradoException(id,
                        "No se encontró ningún usuario con ID: " + id));
    }

    /**
     * Searches for users by name (partial match, case-insensitive).
     */
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarios.stream()
                .filter(u -> u.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Returns all users in the system.
     */
    public List<Usuario> getTodosLosUsuarios() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Returns all users with active loans.
     */
    public List<Usuario> getUsuariosConPrestamos() {
        return usuarios.stream()
                .filter(u -> u.getNumeroPrestamosActivos() > 0)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Checks if a user exists by ID.
     */
    public boolean existeUsuario(String id) {
        return usuarios.stream().anyMatch(u -> u.getId().equals(id));
    }

    /**
     * Gets the total number of users.
     */
    public int getTotalUsuarios() {
        return usuarios.size();
    }

    /**
     * Gets all active loans for a user.
     */
    public List<Prestamo> getPrestamosActivos(String usuarioId) throws UsuarioNoEncontradoException {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getPrestamosActivos();
    }

    /**
     * Gets loan history for a user.
     */
    public List<Prestamo> getHistorialPrestamos(String usuarioId) throws UsuarioNoEncontradoException {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getHistorialPrestamos();
    }

    /**
     * Gets the number of active loans for a user.
     */
    public int getNumeroPrestamosActivos(String usuarioId) throws UsuarioNoEncontradoException {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getNumeroPrestamosActivos();
    }

    /**
     * Checks if a user has reached the loan limit.
     */
    public boolean tieneLimitePrestamosAlcanzado(String usuarioId) throws UsuarioNoEncontradoException {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.tieneLimitePrestamosAlcanzado();
    }

    /**
     * Checks if a user is blocked from borrowing a specific book.
     */
    public boolean estaBloqueadoPara(String usuarioId, model.Libro libro) throws UsuarioNoEncontradoException {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.estaBloqueadoPara(libro);
    }

    /**
     * Updates user information.
     */
    public void actualizarUsuario(Usuario usuario) throws UsuarioNoEncontradoException {
        int index = -1;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new UsuarioNoEncontradoException(usuario.getId());
        }
        usuarios.set(index, usuario);
    }

    /**
     * Removes a user from the system by ID.
     */
    public boolean eliminarUsuario(String id) {
        return usuarios.removeIf(u -> u.getId().equals(id));
    }
}
