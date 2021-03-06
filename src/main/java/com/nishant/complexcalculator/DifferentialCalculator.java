package com.nishant.complexcalculator;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * This class can compute the derivative of a function and the area under a function.
 *
 * @author Nishant
 * @version 2.0.0
 * @since 22-05-2020
 */
public class DifferentialCalculator {
    private String function;

    private DifferentialCalculator(String function) {
        this.function = function;
    }

    /**
     * This function will instantiate the {@code DifferentialCalculator} class for you.
     *
     * @param function the string of arithmetic expression.
     * @return an object of {@code DifferentialCalculator}
     */
    public static DifferentialCalculator fromString(@NotNull String function) {
        return new DifferentialCalculator(function);
    }

    /**
     * his function will duplicate a {@code DifferentialCalculator} object taking the current function of the passed object.
     *
     * @param other the object required to duplicate.
     * @return the duplicated object.
     */
    public static DifferentialCalculator fromComplex(@NotNull DifferentialCalculator other) {
        return new DifferentialCalculator(other.getFunction());
    }

    /**
     * This function will instantiate a {@code DifferentialCalculator} object with the function currently used in an existing
     * {@code ComplexCalculator} object.
     *
     * @param complex the {@code ComplexCalculator} object.
     * @return the {@code DifferentialCalculator} object.
     * @see ComplexCalculator
     */
    public static DifferentialCalculator fromDifferential(@NotNull ComplexCalculator complex) {
        return new DifferentialCalculator(complex.getFunction());
    }

    /**
     * @return the expression
     */
    public String getFunction() {
        return function;
    }

    /**
     * Modify the expression to replace the old one.
     *
     * @param function new expression.
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * Sets the new expression, which is of another <i>DifferentialCalculator</i> instance.
     *
     * @param other instance to set the new expression
     */
    public void setFunction(DifferentialCalculator other) {
        setFunction(other.getFunction());
    }

    /**
     * This method will calculate the derivative of the function at a given point <i>x</i>.
     * The function uses the first principle of derivative as the basis of computation.
     * The function should only use one variable x. <br>
     *
     * @param x the value at which derivative is to be calculated.
     * @return the derivative of the function at point x.
     */
    public double differentiateAt(double x) {
        ComplexCalculator complex = ComplexCalculator.fromDifferential(this);
        final double h = 1E-10;
        Map<Character, Double> xVariable = new HashMap<>();

        xVariable.put('x', x + h);
        double fOfXPlusH = complex.compute(xVariable);

        xVariable.put('x', x);
        double fOfX = complex.compute(xVariable);

        return (fOfXPlusH - fOfX) / h;
    }

    /**
     * This method will calculate the area under the curve in a given range.
     * The function uses the Trapezoid Riemann sum method as the basis of computation.
     * The function should only use one variable x.<br>
     *
     * @param a the starting number of the range (inclusive)
     * @param b the staring number of the range (inclusive)
     * @return the area under the function curve on [a, b]
     */
    public double integrate(double a, double b) {
        ComplexCalculator complex = ComplexCalculator.fromDifferential(this);
        final double deltaX = (b - a) / 50000.0;
        Map<Character, Double> xVariable = new HashMap<>();
        double integral = 0;
        for (double i = a; i <= b - deltaX; i += deltaX) {
            xVariable.put('x', i);
            double left = complex.compute(xVariable);

            xVariable.put('x', i + deltaX);
            double right = complex.compute(xVariable);

            integral += 0.5 * (left + right) * deltaX;
        }
        return integral;
    }
}
