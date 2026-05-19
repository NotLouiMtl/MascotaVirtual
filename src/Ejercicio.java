import java.util.Random;

public class Ejercicio {
    private String pregunta;
    private double respuestaCorrecta;
    private String tipo;

    public Ejercicio(String pregunta, double respuestaCorrecta, String tipo) {
        this.pregunta = pregunta;
        this.respuestaCorrecta = respuestaCorrecta;
        this.tipo = tipo;
    }

    public String getPregunta() { return pregunta; }
    public double getRespuestaCorrecta() { return respuestaCorrecta; }
    public String getTipo() { return tipo; }

    public boolean verificarRespuesta(double respuesta) {
        return Math.abs(respuestaCorrecta - respuesta) < 0.001;
    }

    public static Ejercicio generarEjercicio(String dificultad) {
        Random rnd = new Random();
        int a, b;
        String pregunta;
        double respuesta;
        String tipo;

        int opcion = rnd.nextInt(5);
        int rango = dificultad.equals("facil") ? 10 : dificultad.equals("medio") ? 50 : 100;

        switch (opcion) {
            case 0:
                a = rnd.nextInt(rango) + 1;
                b = rnd.nextInt(rango) + 1;
                pregunta = a + " + " + b;
                respuesta = a + b;
                tipo = "suma";
                break;
            case 1:
                a = rnd.nextInt(rango) + 1;
                b = rnd.nextInt(rango) + 1;
                if (a < b) { int t = a; a = b; b = t; }
                pregunta = a + " - " + b;
                respuesta = a - b;
                tipo = "resta";
                break;
            case 2:
                a = rnd.nextInt(dificultad.equals("facil") ? 10 : 12) + 1;
                b = rnd.nextInt(dificultad.equals("facil") ? 10 : 12) + 1;
                pregunta = a + " x " + b;
                respuesta = a * b;
                tipo = "multiplicacion";
                break;
            case 3:
                b = rnd.nextInt(dificultad.equals("facil") ? 10 : 12) + 1;
                int multiplicador = rnd.nextInt(dificultad.equals("facil") ? 10 : 12) + 1;
                a = b * multiplicador;
                pregunta = a + " ÷ " + b;
                respuesta = (double) a / b;
                tipo = "division";
                break;
            default:
                a = rnd.nextInt(10) + 1;
                b = rnd.nextInt(10) + 1;
                pregunta = "Tabla del " + a + ": " + a + " x " + b;
                respuesta = a * b;
                tipo = "tabla";
                break;
        }
        return new Ejercicio(pregunta, respuesta, tipo);
    }

    @Override
    public String toString() {
        return pregunta + " = " + (respuestaCorrecta == (int)respuestaCorrecta ? String.valueOf((int)respuestaCorrecta) : String.format("%.2f", respuestaCorrecta));
    }
}
