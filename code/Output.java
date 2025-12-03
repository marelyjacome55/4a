/*
 * Output.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-02
 * Versión: 1.0
 * Descripción: Clase responsable de escribir los resultados del programa
 *              PSP en un archivo de salida de texto.
 */

/*
 * Listing Contents:
 *  - Reuse instructions
 *  - Método writeData()
 */

/*
 * Reuse Instructions:
 *  - Crear una instancia de Output.
 *  - Llamar a writeData(nombreArchivo, texto).
 *  - El método sobrescribe el archivo si ya existe.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Output {

    /**
     * Escribe el texto de salida en el archivo indicado.
     *
     * @param outFile nombre del archivo de salida.
     * @param outText contenido a escribir.
     */
    public void writeData(String outFile, String outText) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(outFile));
            writer.print(outText);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo de salida: "
                    + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}

