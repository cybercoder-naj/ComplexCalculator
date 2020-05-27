package com.nishant.complexcalculator;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class can compute mathematical operations based on an arithmetic expression.<br><br>
 *
 * @author Nishant
 * @version 3.0.0
 * @since 18-09-2019
 */
public class ComplexCalculator {
    private String function;

    private ComplexCalculator(String function) {
        this.function = function;
    }

    /**
     * This function will instantiate the {@code ComplexCalculator} class for you.
     *
     * @param function the string of arithmetic expression.
     * @return an object of {@code ComplexCalculator}
     */
    public static ComplexCalculator fromString(@NotNull String function) {
        return new ComplexCalculator(function);
    }

    /**
     * This function will duplicate a {@code ComplexCalculator} object taking the current function of the passed object.
     *
     * @param other the object required to duplicate.
     * @return the duplicated object.
     */
    public static ComplexCalculator fromComplex(@NotNull ComplexCalculator other) {
        return new ComplexCalculator(other.getFunction());
    }

    /**
     * This function will instantiate a {@code ComplexCalculator} object with the function currently used in an existing
     * {@code DifferentialCalculator} object.
     *
     * @param differential the {@code DifferentialCalculator} object.
     * @return the {@code ComplexCalculator} object.
     * @see DifferentialCalculator
     */
    public static ComplexCalculator fromDifferential(@NotNull DifferentialCalculator differential) {
        return new ComplexCalculator(differential.getFunction());
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
     * Sets the new expression, which is of another <i>ComplexCalculator</i> instance.
     *
     * @param other instance to set the new expression
     */
    public void setFunction(@NotNull ComplexCalculator other) {
        setFunction(other.function);
    }

    /**
     * <b>Returns the answer of the given expression.</b>
     * <br>The map collection has a character key and a double value. Each Character in the key will be replaced by its
     * respective double value before computer the expression.
     * <br><br>
     * <p>
     * It can perform the following operations:-
     * <ul>
     * <li>Exponent (^)</li>
     * <li>Division (/)</li>
     * <li>Multiplication (*)</li>
     * <li>Addition (+)</li>
     * <li>Subtraction (-)</li>
     * <li>Trigonometric functions:
     * <ul type='circle'>
     *      <li>sine (sin)</li>
     *      <li>cosine (cos)</li>
     *      <li>tangent (tan)</li>
     *      <li>cotangent (cot)</li>
     *      <li>secant (sec)</li>
     *      <li>cosecant (csc)</li>
     * </ul></li>
     * <li>Inverse Trigonometric functions:
     * <ul type='circle'>
     *      <li>sine inverse (arcsin)</li>
     *      <li>cosine inverse (arccos)</li>
     *      <li>tangent inverse (arctan)</li>
     *      <li>cotangent inverse (arccot)</li>
     *      <li>secant inverse(arcsec)</li>
     *      <li>cosecant inverse(arccsc)</li>
     * </ul></li>
     * <li>Natural Logarithm (ln)</li>
     * <li>Absolute value (|<i>x</i>|)</li>
     * </ul>
     * <b>Note: <i>The angles in trigonometric functions are measured in radians.</i></b><br><br>
     * If there are any variables, you must map them to double values in a {@code Map<Character, Double>} collection.
     * The method will replace the variables with your value and compute the given task.
     * <br><br>You can use <i>pi</i> and <i>e</i> in the middle of the function to use the mathematical value of {@code Math.PI}
     * and {@code Math.E} respectively.<br>
     * You can also you <i>E</i> to denote exponent. For example, <code>3.0E10 = 3.0 * 10<sup>10</sup></code>.
     * The rules of using <i>E</i> will coincide with that of {@code Double}:-
     * <ul>
     * <li>The digits after E must not be fractional.</li>
     * <li>The digits before E must have a integral and a fractional part.</li>
     * </ul>
     * <hr>
     * The following with result in Exceptions:-
     * <ul>
     * <li>replacing E as a variable.</li>
     * <li>missing round brackets.</li>
     * <li>using brackets other than round brackets</li>
     * <li>use of curly or square brackets.</li>
     * <li>{@code variableMap} is null.</li>
     * <li>{@code function} is null or empty.</li>
     * <li>If other unknown computing error occurs.</li>
     * <li>If there is any error in the code.</li>
     * </ul>
     *
     * @param _variableMap characters in key will be replaced by its respective double value.
     * @return the solution to the given expression with value of variables.
     * @see Double
     * @see Map
     */
    public double compute(Map<Character, Double> _variableMap) {
        if (this.getFunction().length() == 0) throw new IllegalStateException("Function is empty");

        Map<Character, Double> variableMap = Objects.requireNonNull(_variableMap);
        String function = "(" + Objects.requireNonNull(this.getFunction()) + ")";
        function = function.replaceAll(" ", "");
        function = function.replaceAll("(?<!\\w)pi(?!\\w)", String.valueOf(Math.PI));
        function = function.replaceAll("(?<!\\w)e(?!\\w)", String.valueOf(Math.E));
        for (Entry<Character, Double> entry : variableMap.entrySet()) {
            if (entry.getKey() == 'E') throw new IllegalArgumentException("Cannot replace E as a variable.");
            function = function.replaceAll(entry.getKey().toString(), entry.getValue().toString());
        }

        if (count(function, '(') > count(function, ')'))
            throw new IllegalArgumentException("Missing '('");
        if (count(function, '(') < count(function, ')'))
            throw new IllegalArgumentException("Missing ')'");

        Matcher matcher1 = Pattern.compile("[(](-?\\d+(\\.\\d+(E-?\\d+)?)?[\\^/*+\\-])+-?\\d+(\\.\\d+(E-?\\d+)?)?[)]").matcher(function);
        Matcher trigonometry = Pattern.compile("(arc)?(sin|cos|tan|cot|sec|csc)[(]-?\\d+(\\.\\d+(E-?\\d+)?)?[)]").matcher(function);
        Matcher logarithm = Pattern.compile("ln[(]-?\\d+(\\.\\d+(E-?\\d+)?)?[)]").matcher(function);
        Matcher absolute = Pattern.compile("\\|-?\\d+(\\.\\d+(E-?\\d+)?)?\\|").matcher(function);
        Matcher matcher2 = Pattern.compile("(?<!\\w)[(]-?\\d+(\\.\\d+(E-?\\d+)?)?[)]").matcher(function);
        Matcher matcher3 = Pattern.compile("(--|\\+\\+)").matcher(function);
        Matcher matcher4 = Pattern.compile("(\\+-|-\\+)").matcher(function);
        boolean match1 = matcher1.find();
        boolean trigoMatch = trigonometry.find();
        boolean logMatch = logarithm.find();
        boolean absMatch = absolute.find();
        boolean match2 = matcher2.find();
        boolean match3 = matcher3.find();
        boolean match4 = matcher4.find();
        while (match1 || match2 || match3 || match4 || trigoMatch || logMatch || absMatch) {
            function = function.replaceAll("[(]\\+", "(");
            String group;
            if (match1) {
                group = matcher1.group();
                if (group.indexOf('^') != -1) function = performOperation('^', group, function);
                else if (group.indexOf('/') != -1) function = performOperation('/', group, function);
                else if (group.indexOf('*') != -1) function = performOperation('*', group, function);
                else if (group.indexOf('+') != -1) function = performOperation('+', group, function);
                else if (group.indexOf('-') != -1 && group.charAt(group.indexOf('-') - 1) != '(')
                    function = performOperation('-', group, function);
                else if (group.lastIndexOf('-') != 1) function = performOperation('-', group, function);
            }

            trigonometry.reset(function);
            trigoMatch = trigonometry.find();
            if (trigoMatch) {
                group = trigonometry.group();
                if (group.startsWith("arc")) {
                    String trigoFunction = group.substring(0, 6);
                    double number = Double.parseDouble(group.substring(7, group.length() - 1));
                    switch (trigoFunction) {
                        case "arcsin":
                            function = function.replace(group, String.valueOf(Math.asin(number)));
                            break;

                        case "arccos":
                            function = function.replace(group, String.valueOf(Math.acos(number)));
                            break;

                        case "arctan":
                            function = function.replace(group, String.valueOf(Math.atan(number)));
                            break;

                        case "arccot":
                            function = function.replace(group, String.valueOf(1.0 / Math.atan(number)));
                            break;

                        case "arcsec":
                            function = function.replace(group, String.valueOf(1.0 / Math.acos(number)));
                            break;

                        case "arccsc":
                            function = function.replace(group, String.valueOf(1.0 / Math.asin(number)));
                            break;
                    }
                } else {
                    String trigoFunction = group.substring(0, 3);
                    double number = Double.parseDouble(group.substring(4, group.length() - 1));
                    switch (trigoFunction) {
                        case "sin":
                            function = function.replace(group, String.valueOf(Math.sin(number)));
                            break;

                        case "cos":
                            function = function.replace(group, String.valueOf(Math.cos(number)));
                            break;

                        case "tan":
                            function = function.replace(group, String.valueOf(Math.tan(number)));
                            break;

                        case "cot":
                            function = function.replace(group, String.valueOf(1.0 / Math.tan(number)));
                            break;

                        case "sec":
                            function = function.replace(group, String.valueOf(1.0 / Math.cos(number)));
                            break;

                        case "csc":
                            function = function.replace(group, String.valueOf(1.0 / Math.sin(number)));
                            break;
                    }
                }
            }

            logarithm.reset(function);
            logMatch = logarithm.find();
            if (logMatch) {
                group = logarithm.group();
                double number = Double.parseDouble(group.substring(3, group.length() - 1));
                function = function.replace(group, String.valueOf(Math.log(number)));
            }

            absolute.reset(function);
            absMatch = absolute.find();
            if (absMatch) {
                group = absolute.group();
                double number = Double.parseDouble(group.substring(1, group.length() - 1));
                function = function.replace(group, String.valueOf(Math.abs(number)));
            }

            matcher2.reset(function);
            match2 = matcher2.find();
            if (match2) {
                group = matcher2.group();
                function = function.replace(group, group.substring(1, group.length() - 1));
            }

            matcher3.reset(function);
            match3 = matcher3.find();
            if (match3) {
                group = matcher3.group();
                function = function.replace(group, "+");
            }

            matcher4.reset(function);
            match4 = matcher4.find();
            if (match4) {
                group = matcher4.group();
                function = function.replace(group, "-");
            }

            matcher1.reset(function);
            trigonometry.reset(function);
            logarithm.reset(function);
            absolute.reset(function);
            matcher2.reset(function);
            matcher3.reset(function);
            matcher4.reset(function);
            match1 = matcher1.find();
            trigoMatch = trigonometry.find();
            logMatch = logarithm.find();
            absMatch = absolute.find();
            match2 = matcher2.find();
            match3 = matcher3.find();
            match4 = matcher4.find();
        }
        try {
            double answer = Double.parseDouble(function);
            if (answer > 1E16)
                throw new ArithmeticException("The value tends to infinity.");
            else if (answer < -1E16)
                throw new ArithmeticException("The value tends to negative infinity");
            else
                return answer;
        } catch (NumberFormatException ex) {
            if (function.contains("Infinity"))
                throw new ArithmeticException("The computation contains infinity");
            else if(function.contains("-Infinity"))
                throw new ArithmeticException("The computation contains negative infinity");
            else if(function.contains("NaN"))
                throw new ArithmeticException("The value in the function was out of its domain");
            else
                throw new ArithmeticException("Unable to compute the given string.");
        }
    }

    /**
     * This method is to be called when there are no variables in the function expression.
     * Call {@code compute(Map<Character, Double>} to replace the value of variables used.
     *
     * @return the solution to the given expression
     */
    public double compute() {
        return compute(new HashMap<>());
    }

    private int count(String function, char c) {
        int f = 0;

        for (int i = 0; i < function.length(); i++) {
            if (function.charAt(i) == c) f++;
        }

        return f;
    }

    private String performOperation(char operator, String group, String function) {
        StringBuilder num1 = new StringBuilder();
        StringBuilder num2 = new StringBuilder();
        int i;
        if (operator != '-') {
            for (i = group.indexOf(operator) - 1; group.charAt(i) == 'E' || Character.isDigit(group.charAt(i)) ||
                    group.charAt(i) == '.' || group.charAt(i) == '-' && (group.charAt(i - 1) == '(' ||
                    group.charAt(i - 1) == 'E'); i--) {
                num1.insert(0, group.charAt(i));
            }

            for (i = group.indexOf(operator) + 1; group.charAt(i) == 'E' || Character.isDigit(group.charAt(i)) ||
                    group.charAt(i) == '.' || group.charAt(i) == '-' && (i == group.indexOf(operator) + 1 ||
                    group.charAt(i - 1) == 'E'); i++) {
                num2.append(group.charAt(i));
            }
        } else {
            for (i = group.lastIndexOf(operator) - 1; group.charAt(i) == 'E' || Character.isDigit(group.charAt(i)) ||
                    group.charAt(i) == '.' || group.charAt(i) == '-' && (group.charAt(i - 1) == '(' ||
                    group.charAt(i - 1) == 'E'); i--) {
                num1.insert(0, group.charAt(i));
            }

            for (i = group.lastIndexOf(operator) + 1; group.charAt(i) == 'E' || Character.isDigit(group.charAt(i)) ||
                    group.charAt(i) == '.' || group.charAt(i) == '-' && (i == group.lastIndexOf(operator) + 1 ||
                    group.charAt(i - 1) == 'E'); i++) {
                num2.append(group.charAt(i));
            }
        }

        double n1 = Double.parseDouble(num1.toString());
        double n2 = Double.parseDouble(num2.toString());
        String toReplace = num1.toString() + operator + num2.toString();
        switch (operator) {
            case '*':
                return function.replace(toReplace, String.valueOf(n1 * n2));
            case '+':
                return function.replace(toReplace, String.valueOf(n1 + n2));
            case '-':
                return function.replace(toReplace, String.valueOf(n1 - n2));
            case '/':
                return function.replace(toReplace, String.valueOf(n1 / n2));
            case '^':
                return function.replace(toReplace, String.valueOf(Math.pow(n1, n2)));
            default:
                throw new IllegalArgumentException("Unexpected value: " + operator);
        }
    }
}
