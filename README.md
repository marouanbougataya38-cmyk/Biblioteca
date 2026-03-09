# Sistema de Gestión de Biblioteca - MVC

## Descripción del Proyecto

Este proyecto es un sistema de gestión de biblioteca desarrollado en Java utilizando el patrón MVC (Modelo-Vista-Controlador). Permite gestionar libros, usuarios, préstamos y devoluciones de manera estructurada y eficiente.

### Características Principales

- **Gestión de Libros**: Agregar, buscar (por título, ISBN, género) y listar libros.
- **Gestión de Usuarios**: Registro de usuarios y seguimiento de sus préstamos.
- **Gestión de Préstamos**: Prestar, devolver y reservar libros.
- **Validaciones**: 
  - Máximo 3 libros prestados por usuario.
  - Préstamo máximo de 30 días.
  - Bloqueo de 7 días tras devolución si el préstamo excedió 30 días.
- **Excepciones Personalizadas**: Manejo de errores específico para cada situación.
- **Interfaz de Consola**: Menú interactivo para el usuario.

## Estructura del Proyecto

```
src/
├── model/                  # Modelo (datos y lógica de negocio)
│   ├── Genero.java         # Enum: géneros de libros
│   ├── EstadoLibro.java    # Enum: estados del libro
│   ├── Libro.java          # Entidad: libro
│   ├── Usuario.java        # Entidad: usuario
│   └── Prestamo.java       # Entidad: préstamo
├── exception/              # Excepciones personalizadas
│   ├── LibroNoDisponibleException.java
│   ├── LimitePrestamosExcedidoException.java
│   ├── UsuarioBloqueadoException.java
│   ├── LibroNoEncontradoException.java
│   └── UsuarioNoEncontradoException.java
├── controller/             # Controladores (lógica del sistema)
│   ├── ControladorLibro.java
│   ├── ControladorUsuario.java
│   ├── ControladorPrestamo.java
│   └── GestorBiblioteca.java
├── view/                   # Vista (interfaz de usuario)
│   └── Consola.java
└── App.java                # Punto de entrada
```

## Requisitos del Sistema

- Java JDK 8 o superior
- Sistema operativo Windows/Linux/Mac

## Cómo Ejecutar el Programa

### Opción 1: Compilar y ejecutar desde línea de comandos

1. Abrir una terminal en la raíz del proyecto.
2. Compilar los archivos fuente:
   ```bash
   javac -d bin src/**/*.java src/*.java
   ```
3. Ejecutar el programa:
   ```bash
   java -cp bin App
   ```

### Opción 2: Usando un IDE (VS Code, IntelliJ, Eclipse)

1. Importar el proyecto como proyecto Java.
2. Ejecutar la clase `App.java`.

## Uso del Sistema

Al iniciar el programa, se crean datos de demostración automáticamente. El menú principal ofrece las siguientes opciones:

1. **Gestión de Libros**: Agregar y listar libros.
2. **Gestión de Usuarios**: Registrar y listar usuarios.
3. **Gestión de Préstamos**: Prestar, devolver, reservar libros.
4. **Búsquedas**: Buscar por título, ISBN o género.
5. **Resumen de Biblioteca**: Ver estadísticas generales.

### Datos de Demostración

El sistema incluye los siguientes datos de prueba:

**Libros:**
- El Quijote (NOVELA)
- 1984 (CIENCIA_FICCION)
- Harry Potter (FANTASIA)
- El Código Da Vinci (MISTERIO)
- La Historia (HISTORIA)

**Usuarios:**
- U001: Juan Pérez
- U002: María García
- U003: Carlos López

## Reparto de Tareas

| Tarea | Desarrollador |
|-------|---------------|
| Análisis y diseño inicial | Ambos |
| Modelo (Libro, Usuario, Prestamo) | Ambos |
| Enums (Genero, EstadoLibro) | Ambos |
| Excepciones personalizadas | Ambos |
| ControladorLibro | Ambos |
| ControladorUsuario | Ambos |
| ControladorPrestamo | Ambos |
| GestorBiblioteca | Ambos |
| Vista (Consola) | Ambos |
| README.md | Ambos |

## Excepciones Implementadas

- **LibroNoDisponibleException**: Cuando un libro no está disponible para préstamo.
- **LimitePrestamosExcedidoException**: Cuando un usuario intenta tener más de 3 préstamos.
- **UsuarioBloqueadoException**: Cuando un usuario está bloqueado para pedir un libro (7 días tras préstamo de 30 días).
- **LibroNoEncontradoException**: Cuando no se encuentra un libro.
- **UsuarioNoEncontradoException**: Cuando no se encuentra un usuario.

## Validaciones Implementadas

1. ✅ No permitir más de 3 libros prestados por usuario.
2. ✅ No permitir préstamo de libro ya prestado o reservado.
3. ✅ No permitir devolución de libro que no está prestado.
4. ✅ Controlar préstamo máximo de 30 días.
5. ✅ Bloqueo de 7 días tras devolución si el préstamo excedió 30 días.

## Notas

- El sistema utiliza `java.time.LocalDate` para el manejo de fechas.
- Los préstamos vencidos se marcan automáticamente al verificar.
- El sistema incluye datos de demostración para pruebas rápidas.

