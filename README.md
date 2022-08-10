# jmud
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

Expression distance = Measures.take(7, mile);
Expression time = Measures.take("16.09344", Units.MINUTE);

Measure<DoubleField> speed = distance.divide(time)  
    .using(DoubleField.factory())  
    .as(kilometerPerHour);

System.out.println(speed.toString());  //prints "42.0 kph"
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
| create dimension | `Dimensions.newFundamentalDimension()`<br>`Dimensions.newDimension()` |
| create unit | `Units.newUnit()` |
| create measurement | `Measures.take(...)` |
| manipulate measurement | `Measure<?> m;`<br>`m.negate()`<br>`m.reciprocal()`<br>`m.add(...)`<br>`m.subtract(...)`<br>`m.multiply(...)`<br>`m.divide(...)`|
| convert unit | `m.as(unit)` |

## Advanced Features

- Make your code precision agnostic using the `Scalar` and `Expression` classes.
- Represent measurements in alternative scales (e.g. °F, dB)  using `Scales`.
- Implement your own number type by implementing `Field`, and use it in concrete calculations of measurements.