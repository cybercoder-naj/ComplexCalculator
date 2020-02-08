package com.nishant.complexcalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;

/**
 * This class can compute mathematical operations based on an expression.
 * The expression is stored in {@code String function}.
 *
 * @author Nishant
 * @version 1.1.3
 * @since 18-09-2019
 */
public class ComplexCalculator {
    private String function;

    /**
     * Instantiate the object with an expression.
     * <p>
     * The function can perform the following operations:-
     * <ul>
     * <li>Exponent (^)</li>
     * <li>Division (/)</li>
     * <li>Multiplication (*)</li>
     * <li>Addition (+)</li>
     * <li>Subtraction (-)</li>
     * </ul>
     * If you like to involve variables in the function, you are most welcome to do so.
     * If there are any variables, you must map them to double values in a {@code Map<Character, Double>} collection.
     * The method will replace the variables with your value and compute the given task.
     * <br><br>You can use <i>pi</i> in the middle of the function to use the mathematical value of {@code Math.PI}.<br>
     * You can also you <i>E</i> to denote exponent. <code>For example, 3.0E10 = 3.0 * 10<sup>10</sup></code>.
     * The rules of using <i>E</i> will coincide with that of {@code Double}:-
     * <ul>
     * <li>The digits after E must not be fractional.</li>
     * <li>The digits before E must have a integral and a fractional part.</li>
     * </ul>
     *
     * @param function given expression.
     */
    public ComplexCalculator(String function) {
        this.function = function;
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
    public void setFunctionOf(ComplexCalculator other) {
        setFunction(other.function);
    }

    /**
     * Returns the answer of the given expression.
     * <br>The map collection has a character key and a double value. Each Character in the key will be replaced by its
     * respective double value before computer the expression.
     * <br><br>
     * The following with result in Exceptions:-
     * <ul>
     * <li>replacing E as a variable.</li>
     * <li>using unexpected letters.</li>
     * <li>missing round brackets.</li>
     * <li>using brackets other than round brackets</li>
     * <li>use of curly or square brackets.</li>
     * <li>{@code variableMap} is null.</li>
     * <li>{@code function} is null or empty.</li>
     * <li>If other unknown computing error occurs.</li>
     * <li>If there is any error in the code.</li>
     * </ul>
     *
     * @param variableMap characters in key will be replaced by its respective double value.
     * @return the solution to the given expression with value of variables.
     * @see Double
     * @see Map
     */
    public double compute(Map<Character, Double> variableMap) {
        try {
            if (this.getFunction().length() == 0) throw new IllegalStateException("Function is empty");
            
            variableMap = Objects.requireNonNull(variableMap);
            String function = "(" + Objects.requireNonNull(this.getFunction) + ")";
            function = function.replaceAll(" ", "");
            function = function.replaceAll("pi", String.valueOf(Math.PI));
            for (Entry<Character, Double> entry : variableMap.entrySet()) {
                if (entry.getKey() == 'E') throw new IllegalArgumentException("Cannot replace E as a variable.");
                function = function.replaceAll(entry.getKey().toString(), entry.getValue().toString());
            }
            Matcher matcher = Pattern.compile("[a-zA-DF-Z]").matcher(function);
            if (matcher.find()) throw new IllegalArgumentException("Unexpected letter: " + matcher.group());
            if (count(function, '(') > count(function, ')'))
                throw new IllegalArgumentException("Missing '('");
            if (count(function, '(') < count(function, ')'))
                throw new IllegalArgumentException("Missing ')'");

            Matcher matcher1 = Pattern.compile("[(](-?\\d+(\\.\\d+(E-?\\d+)?)?[\\^/*+\\-])+-?\\d+(\\.\\d+(E-?\\d+)?)?[)]").matcher(function);
            Matcher matcher2 = Pattern.compile("[(]-?\\d+(\\.\\d+(E-?\\d+)?)?[)]").matcher(function);
            Matcher matcher3 = Pattern.compile("(--|\\+\\+)").matcher(function);
            Matcher matcher4 = Pattern.compile("(\\+-|-\\+)").matcher(function);
            boolean match1 = matcher1.find();
            boolean match2 = matcher2.find();
            boolean match3 = matcher3.find();
            boolean match4 = matcher4.find();
            while (match1 || match2 || match3 || match4) {
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
                matcher2.reset(function);
                matcher3.reset(function);
                matcher4.reset(function);
                match1 = matcher1.find();
                match2 = matcher2.find();
                match3 = matcher3.find();
                match4 = matcher4.find();
            }
            try {
                return Double.parseDouble(function);
            } catch (NumberFormatException ex) {
                if (function.contains("NaN"))
                    throw new ArithmeticException("Division by zero");
                else
                    throw new ArithmeticException("Unable to compute the given string.");
            }
        } catch (ArithmeticException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
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
            for (i = group.indexOf(operator) - 1; group.charAt(i) == 'E' || Character.isDigit(group.charAt(i)) || group.charAt(i) == '.' || group.charAt(i) == '-' && (group.charAt(i - 1) == '(' || group.charAt(i - 1) == 'E'); i--) {
                num1.insert(0, group.charAt(i));
            }

            for (i = group.indexOf(operator) + 1; group.charAt(i) == 'E' || Character.isDigit(group.charAt(i)) || group.charAt(i) == '.' || group.charAt(i) == '-' && (i == group.indexOf(operator) + 1 || group.charAt(i - 1) == 'E'); i++) {
                num2.append(group.charAt(i));
            }
        } else {
            for (i = group.lastIndexOf(operator) - 1; group.charAt(i) == 'E' || Character.isDigit(group.charAt(i)) || group.charAt(i) == '.' || group.charAt(i) == '-' && (group.charAt(i - 1) == '(' || group.charAt(i - 1) == 'E'); i--) {
                num1.insert(0, group.charAt(i));
            }

            for (i = group.lastIndexOf(operator) + 1; group.charAt(i) == 'E' || Character.isDigit(group.charAt(i)) || group.charAt(i) == '.' || group.charAt(i) == '-' && (i == group.lastIndexOf(operator) + 1 || group.charAt(i - 1) == 'E'); i++) {
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
                throw new IllegalStateException("Unexpected value: " + operator);
        }
    }
}
