package com.nishant.complexcalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgebraCalculator {
    String function;

    private AlgebraCalculator(String function) {
        this.function = function;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void simplify() {

    }

    private void simplifyTerms() {
        String function = getFunction();
        Matcher matcher = Pattern.compile("[(](-?\\d+(\\.\\d+(E-?\\d+)?)?[\\^/*+\\-])+-?\\d+(\\.\\d+(E-?\\d+)?)?[)]").matcher(function);
    }
}
