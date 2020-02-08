# Complex Calculator

[View Releases and Changelogs](https://github.com/cybercoder-naj/ComplexCalculator/releases)

[![Build Status](https://travis-ci.com/cybercoder-naj/ComplexCalculator.svg?branch=master)](https://travis-ci.com/cybercoder-naj/ComplexCalculator)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Complex Calculator is a java-based project helping to evaluate arithmetical operations

## Getting Started

**Version 1.1.3**

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You need to have a maven or gradle project. Preferably in an IDE such as IntelliJ, Eclipse or NetBeans.

### Installing

If you are using gradle, this is what you must do,

```bash
repositories {
    maven {
       url 'https://jitpack.io' 
    }
}

dependencies {
    implementation 'com.github.cybercoder-naj:ComplexCalculator:1.1.3'
}
```

If you are using maven, this is what you must do,

```bash
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
    
<dependencies>
    <dependency>
        <groupId>com.github.cybercoder-naj</groupId>
        <artifactId>ComplexCalculator</artifactId>
        <version>1.1.3</version>
    </dependency>
</dependencies>
```

## How to Use

After adding the dependencies, you must import the class.

```bash
import com.nishant.complexcalculator.ComplexCalulator;
```

### Using Constant Functions

You need create an object of the class and pass in a string which holds the constant function.
```bash
String function = "3+4^2/8";
ComplexCalculator calc = new ComplexCalculator(function);
```
Calling the compute function will return the value of the given expression.
```bash
System.out.println(calc.compute());
```
The output of the following will be **5.0**.

### Using Dependent Functions

You need create an object of the class and pass in a string which holds the dependent function. Create a
Map<Character, Double> Collection and put the variables as _key_ and the value it holds as _value_. 
```bash
String function = "(x^2 - 4)/(x - 2)";
ComplexCalculator calc = new ComplexCalculator(function);
Map<Character, Double> variableMap = new HashMap<>();
variableMap.put('x', 4.0);
```
Calling the compute function will return the value of the given expression.
```bash
System.out.println(calc.compute(variableMap));
```
The output of the following will be **6.0**.

### Using Exponents

You can use exponents in the given string expression following the rules of using exponents in _double_ data type.
The rules for using exponents are:-
*  The digits after E must not be fractional.
*  The digits before E must have a integral and a fractional part.
```bash
String function = "6.626E-34/(m*v)";
ComplexCalculator calc = new ComplexCalculator(function);
Map<Character, Double> variableMap = new HashMap<>();
variableMap.put('m', 9.1E-31);
variableMap.put('v', 2.1E6);
System.out.println(calc.compute(variableMap));
```
The output of the following will be **3.467294610151753E-10**.

### Using Pi(π)

You can use _pi_ in the middle of the expression to use its value.
```bash
String function = "pi*r^2";
ComplexCalculator calc = new ComplexCalculator(function);
Map<Character, Double> variableMap = new HashMap<>();
variableMap.put('r', 7.0);
System.out.println(calc.compute(variableMap));
```

The output of the following will be **153.93804002589985**.

## Project Documentation

Don't forget to download the documentation. Visit [Documentation](https://javadoc.jitpack.io/com/github/cybercoder-naj/ComplexCalculator/1.1.3/javadoc) to view the whole documentation.

## License

This project is licensed under Apache License Version 2.0 - see [License](LICENSE)
