# <img src="https://repository-images.githubusercontent.com/325668576/94ce6095-c753-485e-a563-c98b0b434341" alt="jmud" width="320">
Java library for creating and manipulating dimensional units of measurement.

## Usage

```java
Unit kilometer = Units.kilo(Units.METER);
Unit kilometerPerHour = Units.newUnit()  
    .ofDimension(Dimensions.VELOCITY)  
    .as(kilometer).divide(Units.HOUR)  
    .withSymbol("kph").create();
Unit mile = Units.newUnit()  
    .ofDimension(Dimensions.LENGTH)  
    .asExactly(5280).ofA(Units.FOOT)  
    .create();

Expression distance = Expressions.take(7, mile);
Expression time = Expressions.take("16.09344", Units.MINUTE);
Expression speed = distance.divide(time);

Measure<DoubleField> m = speed.using(DoubleField.factory());

System.out.println(m.as(kilometerPerHour).toString());  //prints "42.0 kph"
```

## Features
- Easily create and localiz/se your own units.
- Dimensional commensurability checking at runtime.
- Basic arithmetic (add, subtract, multiply, and divide) on measurements.
- Use your own numerical implementation -- calculations are performed exactly to the precision of your choice.
- All classes are immutable and thread-safe.

## Getting Started

| how to: | code: |
---|---
| create dimension | `Dimensions.newDimension()` |
| create unit | `Units.newUnit()` |
| create measurement/expression | `Expressions.take(...)` |
| manipulate measurement | `Measure<?> m;`<br>`m.add(...)`<br>`m.subtract(...)`<br>`m.multiply(...)`<br>`m.divide(...)`|
| convert unit | `m.as(unit)` |

## Advanced Features

- Make your code numerical implementation agnostic using the `Scalar` and `Expression` classes.
- Represent measurements in alternative scales (e.g. °F, dB) using `Scales`.
- Implement your own number type by implementing `Field`, and use it in concrete calculations of measurements.
