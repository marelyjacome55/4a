/*
 * Logic.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-02
 * Versión: 1.0
 * Descripción: Clase de lógica principal. Obtiene los datos del usuario,
 *              coordina el cálculo de la integral con SimpsonIntegration
 *              y escribe los resultados mediante Output.
 */

/*
 * Listing Contents:
 *  - Reuse instructions
 *  - Dependencias (SimpsonIntegration, Output)
 *  - Atributos de control (intNumSeg, dblE, intDOF, dblX)
 *  - Método logic1a()
 */

/*
 * Reuse Instructions:
 *  - Crear una instancia de Logic y llamar a logic1a().
 *  - Esta clase se encarga de:
 *        * pedir x y dof por consola
 *        * fijar el error permitido E y numSeg inicial
 *        * invocar SimpsonIntegration para calcular p
 *        * delegar la escritura del resultado a Output
 */

import java.util.Scanner;

public class Logic {

    /** Número de segmentos inicial para la regla de Simpson. Debe ser par. */
    private int intNumSeg;

    /** Error máximo permitido entre dos aproximaciones sucesivas. */
    private double dblE;

    /** Grados de libertad de la distribución t. */
    private int intDOF;

    /** Límite superior de integración (0 a x). */
    private double dblX;

    /**
     * Método principal de lógica del programa PSP5.
     * Solicita los datos, llama al integrador y genera la salida.
     */
    public void logic1a() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== PSP - Programa 5: Integración numérica con Simpson ===");
        System.out.print("Ingrese el valor de x: ");
        this.dblX = scanner.nextDouble();

        System.out.print("Ingrese los grados de libertad (DOF): ");
        this.intDOF = scanner.nextInt();

        // Parámetros estándar del ejercicio PSP.
        this.dblE = 0.00001;
        this.intNumSeg = 10;  // siempre par

        SimpsonIntegration simpson =
                new SimpsonIntegration(this.dblE, this.dblX, this.intDOF);

        double dblP = simpson.computeFinalValue(this.intNumSeg);

        // Construimos el texto de salida para el archivo.
        StringBuilder outText = new StringBuilder();
        outText.append("RESULTADOS INTEGRACIÓN DISTRIBUCIÓN t (PSP5)\n");
        outText.append("x = ").append(this.dblX).append("\n");
        outText.append("dof = ").append(this.intDOF).append("\n");
        outText.append("Error permitido E = ").append(this.dblE).append("\n");
        outText.append("Num. segmentos final = ")
               .append(simpson.getIntNumSeg()).append("\n");
        outText.append("Valor de la integral p = ").append(dblP).append("\n");

        Output output = new Output();
        output.writeData("resultado_psp5.txt", outText.toString());

        System.out.println();
        System.out.println("Cálculo completado.");
        System.out.println("p = " + dblP);
        System.out.println("Resultados escritos en resultado_psp5.txt");

        scanner.close();
    }
}
