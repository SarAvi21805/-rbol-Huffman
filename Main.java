/* Actividad: Árbol de Huffman
 * Descripción: Comprimir, codificar, decodificar y descomprimir un archivo mediante un árbol de Huffman
 * Fecha de inicio: 09 de abril del 2025
 * Fecha de última modificación: 01 de mayo del 2025
 * Creadora: Alejandra Avilés - 24722
 * Descripción de la clase: Clase principal que ejecuta la compresión y descompresión usando Huffman.
 */

import java.io.*;
import java.util.*;

public class Main {
    /**
     * Método principal que ejecuta el flujo completo de compresión y descompresión.
     * @param args Argumentos de línea de comandos (no usados).
     * @throws IOException Si ocurre un error al leer o escribir archivos.
     */
    public static void main(String[] args) throws IOException {
        String nombreArchivoEntrada = "archivo_original.txt";
        String nombreArchivoSalida = "archivo_comprimido.txt";
        String nombreArchivoDescomprimido = "archivo_descomprimido.txt";

        // Leer el archivo original
        String texto = leerArchivo(nombreArchivoEntrada);

        // Generar códigos de Huffman
        Huffman huffman = new Huffman();
        Map<Character, String> codigos = huffman.generarCodigos(texto);

        // Codificar el texto
        String codigoComprimido = huffman.codificar(texto);

        // Guardar el archivo comprimido
        guardarArchivo(codigoComprimido, nombreArchivoSalida);

        // Descompresión
        String textoDescomprimido = huffman.decodificar(codigoComprimido, huffman.construirArbol(huffman.contarFrecuencias(texto)));

        // Guardar el archivo descomprimido
        guardarArchivo(textoDescomprimido, nombreArchivoDescomprimido);

        // Mostrar resultados
        System.out.println("Texto original:");
        System.out.println(texto);
        System.out.println("\nCódigo comprimido:");
        System.out.println(codigoComprimido);
        System.out.println("\nTexto descomprimido:");
        System.out.println(textoDescomprimido);
    }

    /**
     * Lee el contenido de un archivo de texto.
     * @param nombreArchivo Nombre del archivo a leer.
     * @return El contenido del archivo como una cadena.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private static String leerArchivo(String nombreArchivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        return contenido.toString().trim();
    }

    /**
     * Guarda contenido en un archivo de texto.
     * @param contenido Contenido a guardar.
     * @param nombreArchivo Nombre del archivo donde se guardará el contenido.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    private static void guardarArchivo(String contenido, String nombreArchivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write(contenido);
        }
    }
}