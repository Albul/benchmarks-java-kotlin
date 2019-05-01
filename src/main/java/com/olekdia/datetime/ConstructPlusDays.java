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

import net.time4j.CalendarUnit;
import net.time4j.SystemClock;
import org.joda.time.DateTimeZone;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark                                 Mode  Cnt        Score        Error   Units
 * ConstructPlusDays.emptyMethod            thrpt    5  2899820.953 ± 123332.328  ops/ms
 * ConstructPlusDays.jodaDateTimeUTC        thrpt    5    17139.065 ±   1402.255  ops/ms
 * ConstructPlusDays.jodaLocalDateTime      thrpt    5    17040.003 ±   3789.682  ops/ms
 * ConstructPlusDays.jodaLocalDate          thrpt    5    10371.670 ±    662.855  ops/ms
 * ConstructPlusDays.time4JPlainDate        thrpt    5    10040.191 ±    388.830  ops/ms
 * ConstructPlusDays.time4JPlainTimestamp   thrpt    5     7842.440 ±    519.761  ops/ms
 * ConstructPlusDays.javaLocalDate          thrpt    5     5022.998 ±    166.381  ops/ms
 * ConstructPlusDays.javaLocalDateTime      thrpt    5     4515.313 ±    714.933  ops/ms
 * ConstructPlusDays.javaCalendar           thrpt    5     3315.312 ±    295.936  ops/ms
 * ConstructPlusDays.javaZoneDateTime       thrpt    5     3133.798 ±    641.575  ops/ms
 * ConstructPlusDays.jodaDateTime           thrpt    5     2132.737 ±    139.183  ops/ms
 * ConstructPlusDays.threeTenLocalDate      thrpt    5     1858.705 ±    313.515  ops/ms
 * ConstructPlusDays.threeTenLocalDateTime  thrpt    5     1660.723 ±    808.662  ops/ms
 * ConstructPlusDays.threeTenZoneDateTime   thrpt    5     1399.538 ±    418.596  ops/ms
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput})
public class ConstructPlusDays {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ConstructPlusDays.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public int jodaDateTimeUTC() {
        return org.joda.time.DateTime.now(DateTimeZone.UTC).plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int jodaDateTime() {
        return org.joda.time.DateTime.now().plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int jodaLocalDateTime() {
        return org.joda.time.LocalDateTime.now().plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int jodaLocalDate() {
        return org.joda.time.LocalDate.now().plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int threeTenLocalDateTime() {
        return org.threeten.bp.LocalDateTime.now().plusDays(1).getDayOfYear();
    }
    @Benchmark
    public int threeTenZoneDateTime() {
        return org.threeten.bp.ZonedDateTime.now().plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int threeTenLocalDate() {
        return org.threeten.bp.LocalDate.now().plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int javaLocalDateTime() {
        return java.time.LocalDateTime.now().plusDays(1).getDayOfYear();
    }
    @Benchmark
    public int javaZoneDateTime() {
        return java.time.ZonedDateTime.now().plusDays(1).getDayOfYear();
    }
    @Benchmark
    public int javaLocalDate() {
        return java.time.LocalDate.now().plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int javaCalendar() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return c.get(Calendar.DAY_OF_YEAR);
    }

    @Benchmark
    public int time4JPlainDate() {
        return SystemClock.inLocalView().today().plus(1, CalendarUnit.DAYS).getDayOfYear();
    }

    @Benchmark
    public int time4JPlainTimestamp() {
        return SystemClock.inLocalView().now().plus(1, CalendarUnit.DAYS).getCalendarDate().getDayOfYear();
    }
}