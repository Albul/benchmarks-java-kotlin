A set of JMH benchmarks for various JVM constructions, written in Java and Kotlin.

Build:
```
mvn clean package
```
Tests includes:
* Loops through lists and arrays
* Comparing different DataTime API available for JVM, like joda.time, time4j, java.util.Calendar, java.time, threeten.bp. Comparing typical operations plusDays, plusWeeks, daysBetween, etc.