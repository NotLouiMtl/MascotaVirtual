import java.util.*;

public class MascotaVirtual {
    private static Mascota mascota;
    private static Queue<Ejercicio> colaEjercicios = new LinkedList<>();
    private static Stack<String> historialRespuestas = new Stack<>();
    private static ArrayList<String> recompensas = new ArrayList<>();
    private static ABB arbolPuntajes = new ABB();
    private static Scanner sc = new Scanner(System.in);
    private static String dificultad = "facil";
    private static int rachaCorrectas = 0;
    private static int totalCorrectas = 0;
    private static int totalIncorrectas = 0;

    public static void main(String[] args) {
        System.out.println("=== MASCOTA VIRTUAL ACADÉMICA ===");
        System.out.print("Ingresa tu nombre: ");
        String nombre = sc.nextLine();

        System.out.println("Elige tu mascota:");
        System.out.println("1. Gato  2. Perro  3. Búho  4. Conejo  5. Panda");
        System.out.print("Opcion: ");
        int tipoOp = leerEntero(1, 5);
        String[] tipos = {"gato", "perro", "buh", "conejo", "panda"};

        mascota = new Mascota(nombre);
        mascota.setTipo(tipos[tipoOp - 1]);

        while (true) {
            try {
                mostrarMenu();
                int opcion = leerEntero(1, 9);
                switch (opcion) {
                    case 1 -> resolverEjercicio();
                    case 2 -> verEjerciciosPendientes();
                    case 3 -> verHistorial();
                    case 4 -> menuRecompensas();
                    case 5 -> menuPuntajes();
                    case 6 -> { mascota.alimentar(); }
                    case 7 -> { mascota.jugar(); }
                    case 8 -> cambiarMascota();
                    case 9 -> {
                        System.out.println("Gracias por jugar!");
                        mostrarEstadisticasFinales();
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenu() {
        limpiarPantalla();
        System.out.println(mascota.obtenerArteASCII());
        System.out.println();
        linea();
        System.out.println("  MASCOTA VIRTUAL ACADEMICA");
        System.out.println("  Dificultad: " + dificultad.toUpperCase());
        System.out.println("  Racha: " + rachaCorrectas + " aciertos");
        linea();
        System.out.println("1. Resolver ejercicio");
        System.out.println("2. Ver ejercicios pendientes");
        System.out.println("3. Ver historial de respuestas");
        System.out.println("4. Recompensas");
        System.out.println("5. Puntajes (ABB)");
        System.out.println("6. Alimentar mascota");
        System.out.println("7. Jugar con mascota");
        System.out.println("8. Cambiar tipo de mascota");
        System.out.println("9. Salir");
        linea();
        System.out.print("Elige una opcion: ");
    }

    private static void linea() {
        System.out.println("=================================");
    }

    private static void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                System.out.print("\033[H\033[2J");
        } catch (Exception e) {
            for (int i = 0; i < 30; i++) System.out.println();
        }
    }

    private static void resolverEjercicio() {
        int cantidad = 5;
        System.out.println("Generando " + cantidad + " ejercicios...");
        for (int i = 0; i < cantidad; i++) {
            colaEjercicios.add(Ejercicio.generarEjercicio(dificultad));
        }

        int correctas = 0;
        while (!colaEjercicios.isEmpty()) {
            Ejercicio ej = colaEjercicios.poll();
            System.out.println("\nEjercicio: " + ej.getPregunta() + " = ?");
            System.out.print("Tu respuesta: ");
            double respuesta = leerDouble();

            boolean correcto = ej.verificarRespuesta(respuesta);
            String tipo = ej.getTipo();

            if (correcto) {
                System.out.println("Correcto!");
                historialRespuestas.push("CORRECTO: " + ej.getPregunta() + " = " + (respuesta == (int)respuesta ? String.valueOf((int)respuesta) : String.format("%.2f", respuesta)));
                correctas++;
                totalCorrectas++;
                rachaCorrectas++;
                mascota.ganarExperiencia(20);

                if (rachaCorrectas > 0 && rachaCorrectas % 3 == 0) {
                    String recompensa = obtenerRecompensaAleatoria();
                    recompensas.add(recompensa);
                    System.out.println("Racha de " + rachaCorrectas + "! Has ganado: " + recompensa);
                }
            } else {
                String respStr = (respuesta == (int)respuesta ? String.valueOf((int)respuesta) : String.format("%.2f", respuesta));
                String respCorr = (ej.getRespuestaCorrecta() == (int)ej.getRespuestaCorrecta() ? String.valueOf((int)ej.getRespuestaCorrecta()) : String.format("%.2f", ej.getRespuestaCorrecta()));
                System.out.println("Incorrecto! La respuesta era: " + respCorr);
                historialRespuestas.push("INCORRECTO: " + ej.getPregunta() + " = " + respStr + " (correcta: " + respCorr + ")");
                totalIncorrectas++;
                rachaCorrectas = 0;
                mascota.ganarExperiencia(5);
            }

            if (correctas >= 3 && colaEjercicios.isEmpty()) {
                System.out.println("\nExcelente! Subiendo dificultad...");
                cambiarDificultad();
            }
        }

        System.out.println("\nResumen: " + correctas + "/" + cantidad + " correctas");
        arbolPuntajes.insertar((correctas * 100) / cantidad, mascota.getNombre());
        System.out.println("Puntaje guardado en el arbol!");
    }

    private static String obtenerRecompensaAleatoria() {
        String[] posibles = {
            "Estrella matematica",
            "Manzana energetica",
            "Insignia dorada",
            "Libro de sabiduria",
            "Galleta cosmica",
            "Medalla de honor",
            "Pocima de concentracion",
            "Gema del conocimiento",
            "Corona del saber",
            "Amuleto de la suerte"
        };
        return posibles[new Random().nextInt(posibles.length)];
    }

    private static void cambiarDificultad() {
        if (dificultad.equals("facil")) {
            dificultad = "medio";
            System.out.println("Dificultad ahora: MEDIO");
        } else if (dificultad.equals("medio")) {
            dificultad = "dificil";
            System.out.println("Dificultad ahora: DIFICIL");
        }
    }

    private static void verEjerciciosPendientes() {
        if (colaEjercicios.isEmpty()) {
            System.out.println("No hay ejercicios pendientes.");
            System.out.println("Resuelve algunos para generar nuevos!");
            pausa();
            return;
        }
        System.out.println("Ejercicios pendientes (" + colaEjercicios.size() + "):");
        int i = 1;
        for (Ejercicio e : colaEjercicios) {
            System.out.println(i++ + ". " + e.getPregunta() + " = ?");
        }
        System.out.println("\nSiguiente: " + colaEjercicios.peek().getPregunta() + " = ?");
        pausa();
    }

    private static void verHistorial() {
        if (historialRespuestas.isEmpty()) {
            System.out.println("Aun no hay respuestas registradas.");
            pausa();
            return;
        }
        System.out.println("Ultima respuesta:");
        System.out.println(historialRespuestas.peek());
        System.out.println("\nHistorial completo (mas reciente primero):");
        int i = 1;
        for (String r : historialRespuestas) {
            System.out.println(i++ + ". " + r);
        }
        pausa();
    }

    private static void menuRecompensas() {
        while (true) {
            limpiarPantalla();
            System.out.println("=== RECOMPENSAS ===");
            System.out.println("1. Mostrar recompensas");
            System.out.println("2. Agregar recompensa");
            System.out.println("3. Eliminar recompensa");
            System.out.println("4. Buscar recompensa");
            System.out.println("5. Regresar");
            linea();
            System.out.print("Opcion: ");
            int op = leerEntero(1, 5);

            switch (op) {
                case 1 -> mostrarRecompensas();
                case 2 -> agregarRecompensa();
                case 3 -> eliminarRecompensa();
                case 4 -> buscarRecompensa();
                case 5 -> { return; }
            }
        }
    }

    private static void mostrarRecompensas() {
        if (recompensas.isEmpty()) {
            System.out.println("No tienes recompensas aun.");
        } else {
            System.out.println("Recompensas (" + recompensas.size() + "):");
            int i = 1;
            for (String r : recompensas)
                System.out.println(i++ + ". " + r);
        }
        pausa();
    }

    private static void agregarRecompensa() {
        System.out.print("Nombre de la recompensa: ");
        String r = sc.nextLine();
        recompensas.add(r);
        System.out.println("Recompensa agregada!");
        pausa();
    }

    private static void eliminarRecompensa() {
        if (recompensas.isEmpty()) {
            System.out.println("No hay recompensas.");
            pausa();
            return;
        }
        System.out.println("Que recompensa deseas eliminar?");
        System.out.print("Indice (1-" + recompensas.size() + "): ");
        int idx = leerEntero(1, recompensas.size());
        String eliminada = recompensas.remove(idx - 1);
        System.out.println("Recompensa eliminada: " + eliminada);
        pausa();
    }

    private static void buscarRecompensa() {
        System.out.print("Recompensa a buscar: ");
        String buscada = sc.nextLine();
        boolean encontrada = false;
        for (int i = 0; i < recompensas.size(); i++) {
            if (recompensas.get(i).toLowerCase().contains(buscada.toLowerCase())) {
                System.out.println("Encontrada en posicion " + (i + 1) + ": " + recompensas.get(i));
                encontrada = true;
            }
        }
        if (!encontrada)
            System.out.println("No se encontro \"" + buscada + "\".");
        pausa();
    }

    private static void menuPuntajes() {
        if (arbolPuntajes.estaVacio()) {
            System.out.println("Aun no hay puntajes registrados.");
            pausa();
            return;
        }
        while (true) {
            limpiarPantalla();
            System.out.println("=== PUNTAJES (ABB) ===");
            System.out.println("1. Recorrido InOrden");
            System.out.println("2. Recorrido PreOrden");
            System.out.println("3. Recorrido PostOrden");
            System.out.println("4. Buscar puntaje");
            System.out.println("5. Insertar puntaje manual");
            System.out.println("6. Regresar");
            linea();
            System.out.print("Opcion: ");
            int op = leerEntero(1, 6);

            switch (op) {
                case 1 -> {
                    System.out.println("InOrden (orden ascendente):");
                    System.out.println(arbolPuntajes.inOrden());
                }
                case 2 -> {
                    System.out.println("PreOrden (raiz, izquierdo, derecho):");
                    System.out.println(arbolPuntajes.preOrden());
                }
                case 3 -> {
                    System.out.println("PostOrden (izquierdo, derecho, raiz):");
                    System.out.println(arbolPuntajes.postOrden());
                }
                case 4 -> {
                    System.out.print("Puntaje a buscar: ");
                    int p = leerEntero(0, 99999);
                    String jug = arbolPuntajes.buscar(p);
                    if (jug != null)
                        System.out.println("Puntaje " + p + " pertenece a: " + jug);
                    else
                        System.out.println("Puntaje " + p + " no encontrado.");
                }
                case 5 -> {
                    System.out.print("Nombre del jugador: ");
                    String nom = sc.nextLine();
                    System.out.print("Puntaje: ");
                    int pts = leerEntero(0, 99999);
                    arbolPuntajes.insertar(pts, nom);
                    System.out.println("Puntaje insertado!");
                }
                case 6 -> { return; }
            }
            if (op >= 1 && op <= 5) pausa();
        }
    }

    private static void cambiarMascota() {
        mascota.cambiarTipo();
        pausa();
    }

    private static void mostrarEstadisticasFinales() {
        System.out.println("\n=== ESTADISTICAS FINALES ===");
        mascota.mostrarEstadisticas();
        System.out.println("Total correctas: " + totalCorrectas);
        System.out.println("Total incorrectas: " + totalIncorrectas);
        System.out.println("Mejor racha: " + rachaCorrectas);
        System.out.println("Recompensas obtenidas: " + recompensas.size());
        System.out.println("\nTabla de puntajes final (ABB InOrden):");
        if (!arbolPuntajes.estaVacio())
            System.out.println(arbolPuntajes.inOrden());
    }

    private static int leerEntero(int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(sc.nextLine());
                if (v >= min && v <= max) return v;
                System.out.print("Ingresa un valor entre " + min + " y " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Ingresa un numero valido: ");
            }
        }
    }

    private static double leerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Ingresa un numero valido: ");
            }
        }
    }

    private static void pausa() {
        System.out.println("\nPresiona Enter para continuar...");
        sc.nextLine();
    }
}
