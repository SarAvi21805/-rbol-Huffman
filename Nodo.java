/* Actividad: Árbol de Huffman
 * Descripción: Comprimir, codificar, decodificar y descomprimir un archivo mediante un árbol de Huffman
 * Fecha de inicio: 09 de abril del 2025
 * Fecha de última modificación: 01 de mayo del 2025
 * Creadora: Alejandra Avilés - 24722
 * Descripción de la clase: Representación de un nodo en el árbol de Huffman. Implementación de Comparable para ordenar nodos según su frecuencia.
 */

class Nodo implements Comparable<Nodo> {
    /**
     * Caracter almacenado en el nodo.
     */
    char caracter;

    /**
     * Frecuencia del caracter.
     */
    int frecuencia;

    /**
     * Nodo hijo izquierdo.
     */
    Nodo izquierda;

    /**
     * Nodo hijo derecho.
     */
    Nodo derecha;

    /**
     * Constructor para crear un nodo con un caracter y su frecuencia.
     * @param caracter El caracter almacenado en el nodo.
     * @param frecuencia La frecuencia del caracter.
     */
    public Nodo(char caracter, int frecuencia) {
        this.caracter = caracter;
        this.frecuencia = frecuencia;
        this.izquierda = null;
        this.derecha = null;
    }

    /**
     * Compara este nodo con otro nodo basado en la frecuencia.
     * @param otro El otro nodo a comparar.
     * @return Un valor negativo, cero o positivo si la frecuencia de este nodo es menor, igual o mayor que la del otro nodo.
     */
    @Override
    public int compareTo(Nodo otro) {
        return Integer.compare(this.frecuencia, otro.frecuencia);
    }
}