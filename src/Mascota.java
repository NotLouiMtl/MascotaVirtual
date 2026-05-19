import java.util.Random;

public class Mascota {
    private String nombre;
    private int nivel;
    private int energia;
    private int felicidad;
    private int experiencia;
    private int maxExperiencia;
    private String tipo;
    private String estadoAnimo;

    public Mascota(String nombre) {
        this.nombre = nombre;
        this.nivel = 1;
        this.energia = 100;
        this.felicidad = 100;
        this.experiencia = 0;
        this.maxExperiencia = 100;
        this.tipo = "gato";
        this.estadoAnimo = "feliz";
    }

    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public int getEnergia() { return energia; }
    public int getFelicidad() { return felicidad; }
    public int getExperiencia() { return experiencia; }
    public String getTipo() { return tipo; }
    public String getEstadoAnimo() { return estadoAnimo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public void cambiarTipo() {
        String[] tipos = {"gato", "perro", "buh", "conejo", "panda"};
        Random rnd = new Random();
        String nuevo;
        do {
            nuevo = tipos[rnd.nextInt(tipos.length)];
        } while (nuevo.equals(this.tipo));
        this.tipo = nuevo;
        System.out.println(nombre + " se ha transformado en un " + nombreTipo() + "!");
    }

    private String nombreTipo() {
        switch (tipo) {
            case "gato": return "gato";
            case "perro": return "perro";
            case "buh": return "búho";
            case "conejo": return "conejo";
            case "panda": return "panda";
            default: return "mascota";
        }
    }

    public void ganarExperiencia(int puntos) {
        experiencia += puntos;
        System.out.println(nombre + " ganó " + puntos + " puntos de experiencia!");
        while (experiencia >= maxExperiencia) {
            subirNivel();
        }
    }

    private void subirNivel() {
        nivel++;
        experiencia -= maxExperiencia;
        maxExperiencia = (int)(maxExperiencia * 1.5);
        energia = Math.min(100, energia + 20);
        felicidad = Math.min(100, felicidad + 10);
        System.out.println("*** " + nombre + " subió al nivel " + nivel + "! ***");
        actualizarEstadoAnimo();
    }

    public void alimentar() {
        if (energia < 100) {
            energia = Math.min(100, energia + 15);
            felicidad = Math.min(100, felicidad + 5);
            System.out.println(nombre + " comió y recuperó energía!");
        } else {
            System.out.println(nombre + " ya está lleno...");
        }
        actualizarEstadoAnimo();
    }

    public void jugar() {
        if (energia >= 10) {
            energia -= 10;
            felicidad = Math.min(100, felicidad + 10);
            ganarExperiencia(5);
            System.out.println("Jugaste con " + nombre + "! Se divirtió mucho!");
        } else {
            System.out.println(nombre + " está muy cansado para jugar. Aliméntalo primero.");
        }
        actualizarEstadoAnimo();
    }

    public void actualizarEstadoAnimo() {
        double promedio = (energia + felicidad) / 2.0;
        if (promedio >= 80) estadoAnimo = "feliz";
        else if (promedio >= 50) estadoAnimo = "normal";
        else if (promedio >= 25) estadoAnimo = "triste";
        else estadoAnimo = "enojado";
    }

    public void mostrarEstadisticas() {
        System.out.println("=== " + nombre + " ===");
        System.out.println("Tipo: " + nombreTipo());
        System.out.println("Nivel: " + nivel);
        System.out.println("Energía: " + energia + "/100");
        System.out.println("Felicidad: " + felicidad + "/100");
        System.out.println("Experiencia: " + experiencia + "/" + maxExperiencia);
        System.out.println("Estado: " + estadoAnimo);
    }

    public String obtenerArteASCII() {
        String arte;
        switch (tipo) {
            case "gato":
                arte = "  /\\_/\\\n ( " + ojo() + "." + ojo() + " )\n  > ^ <";
                break;
            case "perro":
                arte = "   / \\__\n  ( " + ojo() + "." + ojo() + " )\\\n   / \\_/";
                break;
            case "buh":
                arte = "  ,___,\n  (" + ojo() + " " + ojo() + ")\n  /)  (\\";
                break;
            case "conejo":
                arte = "  (\\_/)\n  (" + ojo() + "." + ojo() + ")\n  ( >< )";
                break;
            case "panda":
                arte = "  .---.\n  (" + ojo() + "_" + ojo() + ")\n  / . . \\";
                break;
            default:
                arte = "  /\\_/\\\n ( " + ojo() + "." + ojo() + " )\n  > ^ <";
        }
        return arte + "\n  ~ " + nombre + " ~\n  Ánimo: " + estadoAnimo;
    }

    private String ojo() {
        switch (estadoAnimo) {
            case "feliz": return "o";
            case "normal": return ".";
            case "triste": return "u";
            case "enojado": return "@";
            default: return ".";
        }
    }
}
