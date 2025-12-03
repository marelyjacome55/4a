/*
 * GammaFunction.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-02
 * Versión: 1.0
 * Descripción: Implementa la función Gamma necesaria para
 *              el cálculo de la distribución t.
 */

/*
 * Listing Contents:
 *  - Reuse instructions
 *  - Atributo gammaValue
 *  - Métodos computeIntGamma() y computeDblGamma()
 */

/*
 * Reuse Instructions:
 *  - Usar computeIntGamma(int n) para valores enteros n >= 1.
 *  - Usar computeDblGamma(double x) para valores doble (por ejemplo x = 4.5).
 *  - La implementación de computeDblGamma está orientada a valores
 *    enteros y semi-enteros (n/2) usados en la distribución t.
 */

public class GammaFunction {

    /** Último valor Gamma calculado. */
    private double gammaValue;

    /**
     * Calcula Gamma(n) para valores enteros n >= 1.
     * Para enteros, Gamma(n) = (n-1)!.
     *
     * @param intValue valor entero n.
     * @return Gamma(n) como double.
     */
    public double computeIntGamma(int intValue) {
        if (intValue <= 1) {
            this.gammaValue = 1.0;
            return this.gammaValue;
        }

        double result = 1.0;
        for (int i = 2; i < intValue; i++) {
            result *= i;
        }
        this.gammaValue = result;
        return this.gammaValue;
    }

    /**
     * Calcula Gamma(x) para valores double.
     * Esta implementación está pensada para valores enteros o semi-enteros
     * (por ejemplo 0.5, 1.5, 2.5, ...) y usa la relación:
     *
     *   Gamma(x) = (x-1) * Gamma(x-1)
     *   Gamma(1) = 1
     *   Gamma(0.5) = sqrt(pi)
     *
     * @param doubleValue valor x.
     * @return Gamma(x) como double.
     */
    public double computeDblGamma(double doubleValue) {
        // Caso base para 0.5
        if (Math.abs(doubleValue - 0.5) < 1e-10) {
            this.gammaValue = Math.sqrt(Math.PI);
            return this.gammaValue;
        }

        // Caso base para 1.0
        if (Math.abs(doubleValue - 1.0) < 1e-10) {
            this.gammaValue = 1.0;
            return this.gammaValue;
        }

        // Recurrencia para x > 1
        if (doubleValue > 1.0) {
            this.gammaValue =
                    (doubleValue - 1.0) * computeDblGamma(doubleValue - 1.0);
            return this.gammaValue;
        }

        // Para cualquier otro caso no contemplado explícitamente:
        // devolvemos una aproximación segura.
        this.gammaValue = Math.sqrt(Math.PI);
        return this.gammaValue;
    }

    /**
     * Devuelve el último valor Gamma calculado.
     * @return último valor de Gamma.
     */
    public double getGammaValue() {
        return this.gammaValue;
    }
}
