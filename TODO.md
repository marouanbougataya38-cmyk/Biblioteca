# TODO - Library Management System (MVC)

## Phase 1: Enums - ✅ COMPLETED
- [x] Genero.java - Book genres enum
- [x] EstadoLibro.java - Book status enum

## Phase 2: Model Classes - ✅ COMPLETED
- [x] Libro.java - Book entity
- [x] Usuario.java - User entity
- [x] Prestamo.java - Loan entity

## Phase 3: Exceptions - ✅ COMPLETED
- [x] LibroNoDisponibleException.java
- [x] LimitePrestamosExcedidoException.java
- [x] UsuarioBloqueadoException.java
- [x] LibroNoEncontradoException.java
- [x] UsuarioNoEncontradoException.java

## Phase 4: Controllers - ✅ COMPLETED
- [x] ControladorLibro.java
- [x] ControladorUsuario.java
- [x] ControladorPrestamo.java
- [x] GestorBiblioteca.java

## Phase 5: View - ✅ COMPLETED
- [x] Consola.java

## Phase 6: App - ✅ COMPLETED
- [x] App.java - Main entry point

## Phase 7: Documentation - ✅ COMPLETED
- [x] README.md - Project documentation

## Status: ALL COMPLETED ✅

The Library Management System has been fully implemented with:
- MVC Pattern (Model-View-Controller)
- Enums for Genero and EstadoLibro
- Custom exceptions
- Validations for:
  - Maximum 3 books per user
  - 30-day loan limit
  - 7-day blocking after 30-day loan
- Console-based user interface
- Demo data for testing

To run the project:
```bash
java -cp bin App
```

