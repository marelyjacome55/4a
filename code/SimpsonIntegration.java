/*
 * SimpsonIntegration.java
 * Autor: Marely Jacome
 * Fecha: 2025-12-02
 * Versión: 1.0
 * Descripción:
 *      Implementa la integración numérica usando la regla de Simpson
 *      para calcular la integral de la distribución t.
 *      Sigue el diagrama UML y el estándar PSP (R1).
 */

/*
 * Listing Contents:
 *  - Reuse Instructions
 *  - Atributos internos
 *  - Métodos:
 *      * computeW()
 *      * computeXi()
 *      * computeFirstBaseTerms()
 *      * computeExponent()
 *      * computeCoefficient()
 *      * computeFx()
 *      * computeFinalTerms()
 *      * computeFinalValue()
 *      * getters
 */

/*
 * Reuse Instructions:
 *  - Crear con: new SimpsonIntegration(E, X, DOF)
 *  - Ejecutar la integración con: computeFinalValue(numSegInicial)
 *  - No usar numSeg impar
 *  - DOF debe ser entero positivo
 *  - E debe ser un valor pequeño (0.00001 recomendado)
 */

public class SimpsonIntegration {

    /* ==============================
       ATRIBUTOS INTERNOS (PSP)
       ============================== */

    /** Número de segmentos actual. */
    private int intNumSeg;

    /** Ancho del segmento W. */
    private double dblW;

    /** Error permitido. */
    private double dblE;

    /** Grados de libertad. */
    private int intDOF;

    /** Límite superior x. */
    private double dblX;

    /** Arreglo de xi. */
    private double[] dblTotXi;

    /** Arreglo de términos base (1 + xi^2/dof). */
    private double[] dblFirstBaseTerms;

    /** Exponente negativo de la función t. */
    private double dblExponent;

    /** Coeficiente de la distribución t. */
    private double dblCoeff;

    /** Arreglo de valores f(xi). */
    private double[] dblFx;

    /** Arreglo de términos finales de Simpson. */
    private double[] dblFinalTerms;

    /** Valor final de la integral. */
    private double dblFinalValue;

    /** Dependencia: función Gamma. */
    private GammaFunction gammaFunction;

    /* ==============================
       CONSTRUCTOR
       ============================== */

    public SimpsonIntegration(double dblE, double dblX, int intDOF) {
        this.dblE = dblE;
        this.dblX = dblX;
        this.intDOF = intDOF;
        this.gammaFunction = new GammaFunction();
    }

    /* ==============================
       MÉTODO: computeW
       ============================== */

    public double computeW(int intNumSeg, double dblX) {
        this.intNumSeg = intNumSeg;
        this.dblX = dblX;
        this.dblW = this.dblX / this.intNumSeg;
        return this.dblW;
    }

    /* ==============================
       MÉTODO: computeXi
       ============================== */

    public void computeXi(int intNumSeg) {
        this.dblTotXi = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {
            this.dblTotXi[i] = i * this.dblW;
        }
    }

    /* ==============================
       MÉTODO: computeFirstBaseTerms
       ============================== */

    public void computeFirstBaseTerms(int intNumSeg, double[] dblTotXi,
                                      int intDOF) {

        this.dblFirstBaseTerms = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {
            double xi = dblTotXi[i];
            this.dblFirstBaseTerms[i] =
                    1.0 + ((xi * xi) / (double) intDOF);
        }
    }

    /* ==============================
       MÉTODO: computeExponent
       ============================== */

    public void computeExponent(int intDOF) {
        this.dblExponent = -((intDOF + 1.0) / 2.0);
    }

    /* ==============================
       MÉTODO: computeCoefficient
       ============================== */

    public void computeCoefficient(int intDOF) {

        double numeratorArg = (intDOF + 1.0) / 2.0;
        double denominatorArg = intDOF / 2.0;

        double gammaNum;
        double gammaDen;

        // Gamma(numerador)
        if (Math.abs(numeratorArg - Math.rint(numeratorArg)) < 1e-10) {
            gammaNum = this.gammaFunction.computeIntGamma(
                    (int) Math.rint(numeratorArg));
        } else {
            gammaNum = this.gammaFunction.computeDblGamma(numeratorArg);
        }

        // Gamma(denominador)
        if (Math.abs(denominatorArg - Math.rint(denominatorArg)) < 1e-10) {
            gammaDen = this.gammaFunction.computeIntGamma(
                    (int) Math.rint(denominatorArg));
        } else {
            gammaDen = this.gammaFunction.computeDblGamma(denominatorArg);
        }

        this.dblCoeff = gammaNum /
                (Math.sqrt(intDOF * Math.PI) * gammaDen);
    }

    /* ==============================
       MÉTODO: computeFx
       ============================== */

    public void computeFx(int intNumSeg) {

        this.dblFx = new double[intNumSeg + 1];

        for (int i = 0; i <= intNumSeg; i++) {

            double base = this.dblFirstBaseTerms[i];
            double pow = Math.pow(base, this.dblExponent);

            this.dblFx[i] = this.dblCoeff * pow;
        }
    }

    /* ==============================
       MÉTODO: computeFinalTerms
       ============================== */

    public void computeFinalTerms(int intNumSeg) {

        this.dblFinalTerms = new double[intNumSeg + 1];

        double factor = this.dblW / 3.0;

        for (int i = 0; i <= intNumSeg; i++) {

            int multiplier;

            if (i == 0 || i == intNumSeg) {
                multiplier = 1;
            } else if (i % 2 == 0) {
                multiplier = 2;
            } else {
                multiplier = 4;
            }

            this.dblFinalTerms[i] =
                    factor * multiplier * this.dblFx[i];
        }
    }

    /* ==============================
       MÉTODO: computeFinalValue
       ============================== */

    public double computeFinalValue(int intInitialNumSeg) {

        this.intNumSeg = intInitialNumSeg;

        double previousValue = 0.0;
        double currentValue = 0.0;
        boolean firstPass = true;

        while (true) {

            computeW(this.intNumSeg, this.dblX);
            computeXi(this.intNumSeg);
            computeFirstBaseTerms(this.intNumSeg, this.dblTotXi, this.intDOF);
            computeExponent(this.intDOF);
            computeCoefficient(this.intDOF);
            computeFx(this.intNumSeg);
            computeFinalTerms(this.intNumSeg);

            previousValue = currentValue;
            currentValue = 0.0;

            for (int i = 0; i <= this.intNumSeg; i++) {
                currentValue += this.dblFinalTerms[i];
            }

            if (!firstPass) {
                double diff = Math.abs(currentValue - previousValue);

                if (diff < this.dblE) {
                    break;
                }
            } else {
                firstPass = false;
            }

            this.intNumSeg *= 2;
        }

        this.dblFinalValue = currentValue;
        return this.dblFinalValue;
    }

    /* ==============================
       GETTERS PSP
       ============================== */

    public int getIntNumSeg() {
        return this.intNumSeg;
    }

    public double getDblFinalValue() {
        return this.dblFinalValue;
    }
}
 
