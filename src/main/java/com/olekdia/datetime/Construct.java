/**
 * Copyright 2019 Oleksandr Albul
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.olekdia.datetime;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 Benchmark                         Mode  Cnt        Score        Error   Units
 Construct.emptyMethod            thrpt    5  2845957.712 ± 286315.186  ops/ms
 Construct.jodaLocalDateTime      thrpt    5    22643.252 ±   1303.925  ops/ms
 Construct.jodaDateTime           thrpt    5    19267.265 ±    954.476  ops/ms
 Construct.jodaLocalDate          thrpt    5    16445.420 ±    865.370  ops/ms
 Construct.javaLocalDate          thrpt    5     5493.370 ±    358.704  ops/ms
 Construct.javaLocalDateTime      thrpt    5     5286.260 ±    556.990  ops/ms
 Construct.javaCalendar           thrpt    5     4547.192 ±    396.965  ops/ms
 Construct.threeTenLocalDate      thrpt    5     1965.252 ±    118.156  ops/ms
 Construct.threeTenLocalDateTime  thrpt    5     1847.321 ±    405.722  ops/ms
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput})
public class Construct {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Construct.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void emptyMethod() {
    }

    @Benchmark
    public void jodaDateTime(Blackhole blackhole) {
        blackhole.consume(org.joda.time.DateTime.now());
    }

    @Benchmark
    public void jodaLocalDateTime(Blackhole blackhole) {
        blackhole.consume(org.joda.time.LocalDateTime.now());
    }

    @Benchmark
    public void jodaLocalDate(Blackhole blackhole) {
        blackhole.consume(org.joda.time.LocalDate.now());
    }

    @Benchmark
    public void threeTenLocalDateTime(Blackhole blackhole) {
        blackhole.consume(org.threeten.bp.LocalDateTime.now());
    }

    @Benchmark
    public void threeTenLocalDate(Blackhole blackhole) {
        blackhole.consume(org.threeten.bp.LocalDate.now());
    }

    @Benchmark
    public void javaLocalDateTime(Blackhole blackhole) {
        blackhole.consume(java.time.LocalDateTime.now());
    }
    @Benchmark
    public void javaLocalDate(Blackhole blackhole) {
        blackhole.consume(java.time.LocalDate.now());
    }

    @Benchmark
    public void javaCalendar(Blackhole blackhole) {
        blackhole.consume(java.util.Calendar.getInstance());
    }
}