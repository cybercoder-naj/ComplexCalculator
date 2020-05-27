# How to Use

After adding the dependencies, you must import the class.

```bash
import com.nishant.complexcalculator.ComplexCalulator;
```
See [ComplexCalculator.md](ComplexCalculator.md) to understand the basics of using the classes.

## Finding Derivative at a point

You need create an object of the class and pass in a string which holds the function.
The function should only use one variable x.
The function uses the first derivative principle.

$$
f(x) = \lim_{h\to0}{\frac{f(x+h)-f(x)}{h}}
$$

```bash
String function = "x^2 - 5*x + 6";
DifferentialCalculator calc = DifferentialCalculator.fromString(function);
double answer = calc.differentiateAt(2.0);
System.out.println(Math.round(answer * 1000) / 1000.0);
```
The output of the following will be **-1.0**.

## Finding the area under the curve in a range.

You need to create a object of the class and pass in a string which holds the function.
The function should only use one variable x.
The function uses the Trapezoid method of approximation.

$$
\int_a^bf(x) = \sum_{i=a}^{b-\Delta x}\frac{f(i)+f(i+\Delta x)}{2}\times\Delta x
$$

```bash
String function = "e^x + 1";
DifferentialCalculator calc = DifferentialCalculator.fromString(function);
double answer = calc.integrate(-10, 10);
System.out.println(Math.round(answer * 1000) / 1000.0);
```
The output of the following will be **22037.657**