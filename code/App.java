/*
 * App.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-02
 * Versión: 1.0
 * Descripción: Punto de entrada del programa PSP para integrar la
 *              distribución t utilizando la regla de Simpson.
 */

/*
 * Listing Contents:
 *  - Reuse instructions
 *  - Compilation instructions
 *  - Clase App con método main()
 */

/*
 * Reuse Instructions:
 *  - Ejecutar con:  java App
 *  - El programa solicita por consola:
 *        x   : límite superior de integración
 *        dof : grados de libertad de la distribución t
 *  - Usa una tolerancia fija E = 0.00001 y num_seg inicial = 10.
 *  - Escribe los resultados en el archivo "resultado_psp5.txt".
 */

public class App {

    /**
     * Método principal del programa.
     * @param args Argumentos de línea de comando (no usados).
     */
    public static void main(String[] args) {
        Logic logic = new Logic();
        logic.logic1a();
    }
}
