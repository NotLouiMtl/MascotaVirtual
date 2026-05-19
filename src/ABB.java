public class ABB {
    private NodoABB raiz;

    public ABB() {
        raiz = null;
    }

    public void insertar(int puntaje, String jugador) {
        raiz = insertarRec(raiz, puntaje, jugador);
    }

    private NodoABB insertarRec(NodoABB nodo, int puntaje, String jugador) {
        if (nodo == null) return new NodoABB(puntaje, jugador);
        if (puntaje < nodo.getPuntaje())
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), puntaje, jugador));
        else if (puntaje > nodo.getPuntaje())
            nodo.setDerecho(insertarRec(nodo.getDerecho(), puntaje, jugador));
        else {
            nodo = new NodoABB(puntaje, jugador);
        }
        return nodo;
    }

    public String buscar(int puntaje) {
        return buscarRec(raiz, puntaje);
    }

    private String buscarRec(NodoABB nodo, int puntaje) {
        if (nodo == null) return null;
        if (puntaje == nodo.getPuntaje()) return nodo.getJugador();
        if (puntaje < nodo.getPuntaje())
            return buscarRec(nodo.getIzquierdo(), puntaje);
        else
            return buscarRec(nodo.getDerecho(), puntaje);
    }

    public String inOrden() {
        StringBuilder sb = new StringBuilder();
        inOrdenRec(raiz, sb);
        return sb.toString();
    }

    private void inOrdenRec(NodoABB nodo, StringBuilder sb) {
        if (nodo != null) {
            inOrdenRec(nodo.getIzquierdo(), sb);
            sb.append(nodo.getPuntaje()).append(" - ").append(nodo.getJugador()).append("\n");
            inOrdenRec(nodo.getDerecho(), sb);
        }
    }

    public String preOrden() {
        StringBuilder sb = new StringBuilder();
        preOrdenRec(raiz, sb);
        return sb.toString();
    }

    private void preOrdenRec(NodoABB nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.getPuntaje()).append(" - ").append(nodo.getJugador()).append("\n");
            preOrdenRec(nodo.getIzquierdo(), sb);
            preOrdenRec(nodo.getDerecho(), sb);
        }
    }

    public String postOrden() {
        StringBuilder sb = new StringBuilder();
        postOrdenRec(raiz, sb);
        return sb.toString();
    }

    private void postOrdenRec(NodoABB nodo, StringBuilder sb) {
        if (nodo != null) {
            postOrdenRec(nodo.getIzquierdo(), sb);
            postOrdenRec(nodo.getDerecho(), sb);
            sb.append(nodo.getPuntaje()).append(" - ").append(nodo.getJugador()).append("\n");
        }
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}
