// java
package org.pr2.hibernate;

import org.pr2.hibernate.DAO.*;
import org.pr2.hibernate.Entidades.*;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Date;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static GameDAO gameDAO;
    private static TeamDAO teamDAO;
    private static GenreDAO genreDAO;
    private static ReviewDAO reviewDAO;

    public static void main(String[] args) {
        inicializarDAOs();
        mostrarMenuPrincipal();
    }

    private static void inicializarDAOs() {
        gameDAO = new GameDAO();
        teamDAO = new TeamDAO();
        genreDAO = new GenreDAO();
        reviewDAO = new ReviewDAO();
        System.out.println("Sistema de Gestión de Juegos iniciado correctamente.");
    }

    private static void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestionar Juegos");
            System.out.println("2. Gestionar Equipos");
            System.out.println("3. Gestionar Géneros");
            System.out.println("4. Gestionar Reseñas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    menuGestionJuegos();
                    break;
                case 2:
                    menuGestionEquipos();
                    break;
                case 3:
                    menuGestionGeneros();
                    break;
                case 4:
                    menuGestionResenas();
                    break;
                case 5:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
        scanner.close();
    }

    // Menú para Juegos
    private static void menuGestionJuegos() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE JUEGOS ===");
            System.out.println("1. Crear Juego");
            System.out.println("2. Listar Todos los Juegos");
            System.out.println("3. Obtener Juego por ID");
            System.out.println("4. Actualizar Juego");
            System.out.println("5. Borrar Juego");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    crearJuego();
                    break;
                case 2:
                    listarJuegos();
                    break;
                case 3:
                    obtenerJuegoPorId();
                    break;
                case 4:
                    actualizarJuego();
                    break;
                case 5:
                    borrarJuego();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }

    private static void crearJuego() {
        Game game = new Game();
        System.out.print("Título del juego: ");
        game.setTitle(scanner.nextLine());
        System.out.print("Fecha de lanzamiento (dd/MM/yyyy): ");
        // Nota: Asumiendo un simple Date parsing; usa una librería como SimpleDateFormat en producción
        String fechaStr = scanner.nextLine();
        try {
            Date releaseDate = new Date(); // Placeholder; implementa parsing real
            game.setReleaseDate(releaseDate);
        } catch (Exception e) {
            System.out.println("Fecha inválida, se usará fecha actual.");
        }
        System.out.print("Rating (0-10): ");
        game.setRating(scanner.nextFloat());
        scanner.nextLine();
        System.out.print("Resumen: ");
        game.setSummary(scanner.nextLine());
        // Otros campos opcionales...

        gameDAO.guardar(game);
        System.out.println("Juego creado con ID: " + game.getIdGame());
    }

    private static void listarJuegos() {
        List<Game> juegos = gameDAO.obtenerTodos();
        if (juegos.isEmpty()) {
            System.out.println("No hay juegos guardados.");
            return;
        }
        for (Game game : juegos) {
            System.out.println("ID: " + game.getIdGame() + ", Título: " + game.getTitle() + ", Rating: " + game.getRating());
            if (game.getTeams() != null) {
                System.out.print("Equipos: ");
                for (Team t : game.getTeams()) {
                    System.out.print(t.getTeamName() + " ");
                }
                System.out.println();
            }
            if (game.getGenres() != null) {
                System.out.print("Géneros: ");
                for (Genre g : game.getGenres()) {
                    System.out.print(g.getGenreName() + " ");
                }
                System.out.println();
            }
        }
    }

    private static void obtenerJuegoPorId() {
        System.out.print("ID del juego: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Game game = gameDAO.obtenerPorId(id);
        if (game != null) {
            System.out.println("Juego encontrado: " + game.getTitle() + ", Rating: " + game.getRating());
            // Mostrar relaciones si existen
            if (game.getReviews() != null) {
                System.out.println("Número de reseñas: " + game.getReviews().size());
            }
        } else {
            System.out.println("Juego no encontrado.");
        }
    }

    private static void actualizarJuego() {
        System.out.print("ID del juego a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Game game = gameDAO.obtenerPorId(id);
        if (game == null) {
            System.out.println("Juego no encontrado.");
            return;
        }
        System.out.print("Nuevo título (actual: " + game.getTitle() + "): ");
        game.setTitle(scanner.nextLine());
        System.out.print("Nuevo rating (actual: " + game.getRating() + "): ");
        game.setRating(scanner.nextFloat());
        scanner.nextLine();
        System.out.print("Nuevo resumen (actual: " + game.getSummary() + "): ");
        game.setSummary(scanner.nextLine());

        gameDAO.actualizar(game);
        System.out.println("Juego actualizado correctamente.");
    }

    private static void borrarJuego() {
        System.out.print("ID del juego a borrar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Game game = gameDAO.obtenerPorId(id);
        if (game != null) {
            gameDAO.eliminar(game);
            System.out.println("Juego borrado correctamente.");
        } else {
            System.out.println("Juego no encontrado.");
        }
    }

    // Menú para Equipos (similar estructura)
    private static void menuGestionEquipos() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE EQUIPOS ===");
            System.out.println("1. Crear Equipo");
            System.out.println("2. Listar Todos los Equipos");
            System.out.println("3. Obtener Equipo por ID");
            System.out.println("4. Actualizar Equipo");
            System.out.println("5. Borrar Equipo");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearEquipo();
                    break;
                case 2:
                    listarEquipos();
                    break;
                case 3:
                    obtenerEquipoPorId();
                    break;
                case 4:
                    actualizarEquipo();
                    break;
                case 5:
                    borrarEquipo();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }

    private static void crearEquipo() {
        Team team = new Team();
        System.out.print("Nombre del equipo: ");
        team.setTeamName(scanner.nextLine());
        teamDAO.guardar(team);
        System.out.println("Equipo creado con ID: " + team.getId());
    }

    private static void listarEquipos() {
        List<Team> equipos = teamDAO.obtenerTodos();
        if (equipos.isEmpty()) {
            System.out.println("No hay equipos guardados.");
            return;
        }
        for (Team team : equipos) {
            System.out.println("ID: " + team.getId() + ", Nombre: " + team.getTeamName());
            if (team.getGames() != null) {
                System.out.print("Juegos asociados: ");
                for (Game g : team.getGames()) {
                    System.out.print(g.getTitle() + " ");
                }
                System.out.println();
            }
        }
    }

    private static void obtenerEquipoPorId() {
        System.out.print("ID del equipo: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Team team = teamDAO.obtenerPorId(id);
        if (team != null) {
            System.out.println("Equipo encontrado: " + team.getTeamName());
        } else {
            System.out.println("Equipo no encontrado.");
        }
    }

    private static void actualizarEquipo() {
        System.out.print("ID del equipo a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Team team = teamDAO.obtenerPorId(id);
        if (team == null) {
            System.out.println("Equipo no encontrado.");
            return;
        }
        System.out.print("Nuevo nombre (actual: " + team.getTeamName() + "): ");
        team.setTeamName(scanner.nextLine());
        teamDAO.actualizar(team);
        System.out.println("Equipo actualizado correctamente.");
    }

    private static void borrarEquipo() {
        System.out.print("ID del equipo a borrar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Team team = teamDAO.obtenerPorId(id);
        if (team != null) {
            teamDAO.eliminar(team);
            System.out.println("Equipo borrado correctamente.");
        } else {
            System.out.println("Equipo no encontrado.");
        }
    }

    // Menú para Géneros (similar a Equipos)
    private static void menuGestionGeneros() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE GÉNEROS ===");
            System.out.println("1. Crear Género");
            System.out.println("2. Listar Todos los Géneros");
            System.out.println("3. Obtener Género por ID");
            System.out.println("4. Actualizar Género");
            System.out.println("5. Borrar Género");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearGenero();
                    break;
                case 2:
                    listarGeneros();
                    break;
                case 3:
                    obtenerGeneroPorId();
                    break;
                case 4:
                    actualizarGenero();
                    break;
                case 5:
                    borrarGenero();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }

    private static void crearGenero() {
        Genre genre = new Genre();
        System.out.print("Nombre del género: ");
        genre.setGenreName(scanner.nextLine());
        genreDAO.guardar(genre);
        System.out.println("Género creado con ID: " + genre.getId());
    }

    private static void listarGeneros() {
        List<Genre> generos = genreDAO.obtenerTodos();
        if (generos.isEmpty()) {
            System.out.println("No hay géneros guardados.");
            return;
        }
        for (Genre genre : generos) {
            System.out.println("ID: " + genre.getId() + ", Nombre: " + genre.getGenreName());
            if (genre.getGames() != null) {
                System.out.print("Juegos asociados: ");
                for (Game g : genre.getGames()) {
                    System.out.print(g.getTitle() + " ");
                }
                System.out.println();
            }
        }
    }

    private static void obtenerGeneroPorId() {
        System.out.print("ID del género: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Genre genre = genreDAO.obtenerPorId(id);
        if (genre != null) {
            System.out.println("Género encontrado: " + genre.getGenreName());
        } else {
            System.out.println("Género no encontrado.");
        }
    }

    private static void actualizarGenero() {
        System.out.print("ID del género a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Genre genre = genreDAO.obtenerPorId(id);
        if (genre == null) {
            System.out.println("Género no encontrado.");
            return;
        }
        System.out.print("Nuevo nombre (actual: " + genre.getGenreName() + "): ");
        genre.setGenreName(scanner.nextLine());
        genreDAO.actualizar(genre);
        System.out.println("Género actualizado correctamente.");
    }

    private static void borrarGenero() {
        System.out.print("ID del género a borrar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Genre genre = genreDAO.obtenerPorId(id);
        if (genre != null) {
            genreDAO.eliminar(genre);
            System.out.println("Género borrado correctamente.");
        } else {
            System.out.println("Género no encontrado.");
        }
    }

    // Menú para Reseñas (similar a otros)
    private static void menuGestionResenas() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE RESEÑAS ===");
            System.out.println("1. Crear Reseña");
            System.out.println("2. Listar Todas las Reseñas");
            System.out.println("3. Obtener Reseña por ID");
            System.out.println("4. Actualizar Reseña");
            System.out.println("5. Borrar Reseña");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearResena();
                    break;
                case 2:
                    listarResenas();
                    break;
                case 3:
                    obtenerResenaPorId();
                    break;
                case 4:
                    actualizarResena();
                    break;
                case 5:
                    borrarResena();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }

    private static void crearResena() {
        Review review = new Review();
        System.out.print("Texto de la reseña: ");
        review.setReviewText(scanner.nextLine());
        System.out.print("ID del juego asociado: ");
        int idJuego = scanner.nextInt();
        scanner.nextLine();
        Game game = gameDAO.obtenerPorId(idJuego);
        if (game != null) {
            review.setGame(game);
            reviewDAO.guardar(review);
            System.out.println("Reseña creada con ID: " + review.getId());
        } else {
            System.out.println("Juego no encontrado. No se puede crear la reseña.");
        }
    }

    private static void listarResenas() {
        List<Review> resenas = reviewDAO.obtenerTodos();
        if (resenas.isEmpty()) {
            System.out.println("No hay reseñas guardadas.");
            return;
        }
        for (Review review : resenas) {
            System.out.println("ID: " + review.getId() + ", Texto: " + review.getReviewText());
            if (review.getGame() != null) {
                System.out.println("Juego: " + review.getGame().getTitle());
            }
        }
    }

    private static void obtenerResenaPorId() {
        System.out.print("ID de la reseña: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Review review = reviewDAO.obtenerPorId(id);
        if (review != null) {
            System.out.println("Reseña encontrada: " + review.getReviewText());
            if (review.getGame() != null) {
                System.out.println("Juego asociado: " + review.getGame().getTitle());
            }
        } else {
            System.out.println("Reseña no encontrada.");
        }
    }

    private static void actualizarResena() {
        System.out.print("ID de la reseña a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Review review = reviewDAO.obtenerPorId(id);
        if (review == null) {
            System.out.println("Reseña no encontrada.");
            return;
        }
        System.out.print("Nuevo texto (actual: " + review.getReviewText() + "): ");
        review.setReviewText(scanner.nextLine());
        reviewDAO.actualizar(review);
        System.out.println("Reseña actualizada correctamente.");
    }

    private static void borrarResena() {
        System.out.print("ID de la reseña a borrar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Review review = reviewDAO.obtenerPorId(id);
        if (review != null) {
            reviewDAO.eliminar(review);
            System.out.println("Reseña borrada correctamente.");
        } else {
            System.out.println("Reseña no encontrada.");
        }
    }
}
