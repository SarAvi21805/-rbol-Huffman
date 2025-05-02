/* Actividad: Árbol de Huffman
 * Descripción: Comprimir, codificar, decodificar y descomprimir un archivo mediante un árbol de Huffman
 * Fecha de inicio: 09 de abril del 2025
 * Fecha de última modificación: 01 de mayo del 2025
 * Creadora: Alejandra Avilés - 24722
 * Descripción de la clase: Permite la implementación de la codificación y decodificación usando el algoritmo de Huffman.
 */

import java.io.*;
import java.util.*;

public class Huffman {
    /**
     * Mapa que almacena los códigos binarios para cada caracter.
     */
    private static Map<Character, String> codigos = new HashMap<>();

    /**
     * Cola de prioridad para construir el árbol de Huffman.
     */
    private static PriorityQueue<Nodo> colaPrioridad;

    /**
     * Genera los códigos de Huffman para un texto dado.
     * @param texto El texto para el cual se generarán los códigos.
     * @return Un mapa con los caracteres y sus códigos binarios.
     * @throws IOException Si ocurre un error al guardar la tabla de frecuencias.
     */
    public static Map<Character, String> generarCodigos(String texto) throws IOException {
        Map<Character, Integer> frecuencias = contarFrecuencias(texto);
        int totalCaracteres = texto.length();
        guardarFrecuencias(frecuencias, totalCaracteres, "tabla_frecuencias.txt");

        Nodo raiz = construirArbol(frecuencias);
        generarCodigos(raiz, "");

        return codigos;
    }

    /**
     * Codifica un texto usando los códigos de Huffman generados.
     * @param texto El texto a codificar.
     * @return El texto codificado en forma de cadena binaria.
     */
    public static String codificar(String texto) {
        StringBuilder codigoComprimido = new StringBuilder();
        for (char c : texto.toCharArray()) {
            codigoComprimido.append(codigos.get(c));
        }
        return codigoComprimido.toString();
    }

    /**
     * Decodifica un código binario usando el árbol de Huffman.
     * @param codigo El código binario a decodificar.
     * @param raiz La raíz del árbol de Huffman.
     * @return El texto decodificado.
     */
    public static String decodificar(String codigo, Nodo raiz) {
        StringBuilder resultado = new StringBuilder();
        Nodo nodo = raiz;
        for (char bit : codigo.toCharArray()) {
            nodo = (bit == '0') ? nodo.izquierda : nodo.derecha;
            if (nodo.izquierda == null && nodo.derecha == null) {
                resultado.append(nodo.caracter);
                nodo = raiz;
            }
        }
        return resultado.toString();
    }

    /**
     * Cuenta la frecuencia de cada caracter en un texto.
     * @param texto El texto a analizar.
     * @return Un mapa con los caracteres y sus frecuencias.
     */
    public static Map<Character, Integer> contarFrecuencias(String texto) {
        Map<Character, Integer> frecuencias = new HashMap<>();
        for (char c : texto.toCharArray()) {
            frecuencias.put(c, frecuencias.getOrDefault(c, 0) + 1);
        }
        return frecuencias;
    }

    /**
     * Guarda la tabla de frecuencias en un archivo.
     * @param frecuencias Mapa con los caracteres y sus frecuencias.
     * @param total Total de caracteres en el texto.
     * @param nombreArchivo Nombre del archivo donde se guardará la tabla.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public static void guardarFrecuencias(Map<Character, Integer> frecuencias, int total, String nombreArchivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write("Caracter\tFrecuencia\tPorcentaje\n");
            for (Map.Entry<Character, Integer> entry : frecuencias.entrySet()) {
                char caracter = entry.getKey();
                int frecuencia = entry.getValue();
                double porcentaje = (double) frecuencia / total;
                bw.write(String.format("%c\t%d\t%.4f\n", caracter, frecuencia, porcentaje));
            }
        }
    }

    /**
     * Construye el árbol de Huffman a partir de las frecuencias de los caracteres.
     * @param frecuencias Mapa con los caracteres y sus frecuencias.
     * @return La raíz del árbol de Huffman.
     */
    public static Nodo construirArbol(Map<Character, Integer> frecuencias) {
        colaPrioridad = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frecuencias.entrySet()) {
            colaPrioridad.add(new Nodo(entry.getKey(), entry.getValue()));
        }

        while (colaPrioridad.size() > 1) {
            Nodo izquierda = colaPrioridad.poll();
            Nodo derecha = colaPrioridad.poll();
            Nodo nuevoNodo = new Nodo('\0', izquierda.frecuencia + derecha.frecuencia);
            nuevoNodo.izquierda = izquierda;
            nuevoNodo.derecha = derecha;
            colaPrioridad.add(nuevoNodo);
        }

        return colaPrioridad.poll();
    }

    /**
     * Genera los códigos binarios para cada caracter recorriendo el árbol de Huffman.
     * @param nodo Nodo actual en el recorrido.
     * @param codigo Código binario acumulado hasta el nodo actual.
     */
    private static void generarCodigos(Nodo nodo, String codigo) {
        if (nodo.izquierda == null && nodo.derecha == null) {
            codigos.put(nodo.caracter, codigo);
            return;
        }
        if (nodo.izquierda != null) {
            generarCodigos(nodo.izquierda, codigo + "0");
        }
        if (nodo.derecha != null) {
            generarCodigos(nodo.derecha, codigo + "1");
        }
    }
}