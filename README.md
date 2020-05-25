# Complex Calculator

[View Releases and Changelog](https://github.com/cybercoder-naj/ComplexCalculator/releases)

[![Build Status](https://travis-ci.com/cybercoder-naj/ComplexCalculator.svg?branch=master)](https://travis-ci.com/cybercoder-naj/ComplexCalculator)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/af26fdcf65604ec0a73d907ba2b58f92)](https://app.codacy.com/manual/cybercoder-naj/ComplexCalculator?utm_source=github.com&utm_medium=referral&utm_content=cybercoder-naj/ComplexCalculator&utm_campaign=Badge_Grade_Dashboard)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Release](https://jitpack.io/v/cybercoder-naj/ComplexCalculator.svg)](https://jitpack.io/#cybercoder-naj/ComplexCalculator)

Complex Calculator is a java-based project helping to evaluate arithmetical operations

## Getting Started

**Version 2.0.1**

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
    implementation 'com.github.cybercoder-naj:ComplexCalculator:2.0.1'
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
        <version>2.0.1</version>
    </dependency>
</dependencies>
```

## How to Use

After adding the dependencies, you must import the class.

```bash
import com.nishant.complexcalculator.ComplexCalulator;
```

 - Visit [ComplexCalculator.md](ComplexCalculator.md) to see how to use that class.
 - Visit [DifferentialCalculator.md](DifferentialCalculator.md) to see how to use that class.

### Using Constant Functions

You need create an object of the class and pass in a string which holds the constant function.
```bash
String function = "3+4^2/8";
ComplexCalculator calc = ComplexCalculator.fromString(function);
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
ComplexCalculator calc = ComplexCalculator.fromString(function);
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
* The digits after E must not be fractional.
* The digits before E must have a integral and a fractional part.
```bash
String function = "6.626E-34/(m*v)";
ComplexCalculator calc = ComplexCalculator.fromString(function);
Map<Character, Double> variableMap = new HashMap<>();
variableMap.put('m', 9.1E-31);
variableMap.put('v', 2.1E6);
System.out.println(calc.compute(variableMap));
```
The output of the following will be **3.467294610151753E-10**.

### Using Pi(π) and e

You can use _pi_ in the middle of the expression to use its value.
```bash
String function = "pi*r^2 + e^x";
ComplexCalculator calc = ComplexCalculator.fromString(function);
Map<Character, Double> variableMap = new HashMap<>();
variableMap.put('r', 7.0);
variableMap.put('x', 0.5);
System.out.println(calc.compute(variableMap));
```

The output of the following will be **155.58676129659997**.

### Finding Derivative at a point

You need create an object of the class and pass in a string which holds the function.
The function should only use one variable x. 

```bash
String function = "x^2 - 5*x + 6";
DifferentialCalculator calc = DifferentialCalculator.fromString(function);
double answer = calc.differentiateAt(2.0);
System.out.println(Math.round(answer * 1000) / 1000.0);
```
The output of the following will be **-1.0**.

### Finding the area under the curve in a range.

You need to create a object of the class and pass in a string which holds the function.
The function should only use one variable x.

```bash
String function = "e^x + 1";
DifferentialCalculator calc = DifferentialCalculator.fromString(function);
double answer = calc.integrate(-10, 10);
System.out.println(Math.round(answer * 1000) / 1000.0);
```
The output of the following will be **22037.657**

## Project Documentation

Don't forget to download the documentation. Visit [Documentation](https://javadoc.jitpack.io/com/github/cybercoder-naj/ComplexCalculator/2.0.1/javadoc) to view the whole documentation.

## License

This project is licensed under Apache License Version 2.0 - see [License](LICENSE)
