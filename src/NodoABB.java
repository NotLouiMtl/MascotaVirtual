public class NodoABB {
    private int puntaje;
    private String jugador;
    private NodoABB izquierdo;
    private NodoABB derecho;

    public NodoABB(int puntaje, String jugador) {
        this.puntaje = puntaje;
        this.jugador = jugador;
        this.izquierdo = null;
        this.derecho = null;
    }

    public int getPuntaje() { return puntaje; }
    public String getJugador() { return jugador; }
    public NodoABB getIzquierdo() { return izquierdo; }
    public NodoABB getDerecho() { return derecho; }
    public void setIzquierdo(NodoABB izquierdo) { this.izquierdo = izquierdo; }
    public void setDerecho(NodoABB derecho) { this.derecho = derecho; }
}
