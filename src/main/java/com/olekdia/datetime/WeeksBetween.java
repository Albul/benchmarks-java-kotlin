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

import org.joda.time.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark                            Mode  Cnt        Score        Error   Units
 * WeeksBetween.emptyMethod            thrpt    5  2621265.494 ± 773265.388  ops/ms
 * WeeksBetween.threeTenLocalDate      thrpt    5    57127.813 ±   2786.482  ops/ms
 * WeeksBetween.javaLocalDate          thrpt    5    54954.037 ±   4188.778  ops/ms
 * WeeksBetween.jodaLocalDate          thrpt    5    44717.870 ±   7424.832  ops/ms
 * WeeksBetween.threeTenLocalDateTime  thrpt    5    42301.040 ±    962.235  ops/ms
 * WeeksBetween.javaLocalDateTime      thrpt    5    39808.724 ±   6698.393  ops/ms
 * WeeksBetween.threeTenZoneDateTime   thrpt    5    39619.506 ±   1760.923  ops/ms
 * WeeksBetween.javaZoneDateTime       thrpt    5    33933.782 ±  24226.083  ops/ms
 * WeeksBetween.jodaDateTime           thrpt    5    27955.494 ±   8549.899  ops/ms
 * WeeksBetween.jodaLocalDateTime      thrpt    5     1906.468 ±    460.859  ops/ms
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput})
public class WeeksBetween {

    private DateTime mJodaDateTimeStart;
    private DateTime mJodaDateTimeEnd;
    private LocalDateTime mJodaLocalDateTimeStart;
    private LocalDateTime mJodaLocalDateTimeEnd;
    private LocalDate mJodaLocalDateStart;
    private LocalDate mJodaLocalDateEnd;
    private org.threeten.bp.LocalDateTime mThreeTenLocalDateTimeStart;
    private org.threeten.bp.LocalDateTime mThreeTenLocalDateTimeEnd;
    private org.threeten.bp.ZonedDateTime mThreeTenZoneDateTimeStart;
    private org.threeten.bp.ZonedDateTime mThreeTenZoneDateTimeEnd;
    private org.threeten.bp.LocalDate mThreeTenLocalDateStart;
    private org.threeten.bp.LocalDate mThreeTenLocalDateEnd;
    private java.time.LocalDateTime mJavaLocalDateTimeStart;
    private java.time.LocalDateTime mJavaLocalDateTimeEnd;
    private java.time.ZonedDateTime mJavaZoneDateTimeStart;
    private java.time.ZonedDateTime mJavaZoneDateTimeEnd;
    private java.time.LocalDate mJavaLocalDateStart;
    private java.time.LocalDate mJavaLocalDateEnd;

    private final static int MONTHS_BETWEEN = 60;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(WeeksBetween.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup(Blackhole blackhole) {
        mJodaDateTimeStart = DateTime.now().withTimeAtStartOfDay();
        mJodaDateTimeEnd = mJodaDateTimeStart.plusMonths(MONTHS_BETWEEN);
        mJodaLocalDateStart = LocalDate.now();
        mJodaLocalDateEnd = mJodaLocalDateStart.plusMonths(MONTHS_BETWEEN);
        mJodaLocalDateTimeStart = mJodaLocalDateStart.toDateTimeAtStartOfDay().toLocalDateTime();
        mJodaLocalDateTimeEnd = mJodaLocalDateTimeStart.plusMonths(MONTHS_BETWEEN);

        mThreeTenLocalDateStart = org.threeten.bp.LocalDate.now();
        mThreeTenLocalDateEnd = mThreeTenLocalDateStart.plusMonths(MONTHS_BETWEEN);
        mThreeTenLocalDateTimeStart = mThreeTenLocalDateStart.atStartOfDay();
        mThreeTenLocalDateTimeEnd = mThreeTenLocalDateTimeStart.plusMonths(MONTHS_BETWEEN);

        mThreeTenZoneDateTimeStart = org.threeten.bp.ZonedDateTime.now()
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        mThreeTenZoneDateTimeEnd = mThreeTenZoneDateTimeStart.plusMonths(MONTHS_BETWEEN);

        mJavaLocalDateStart = java.time.LocalDate.now();
        mJavaLocalDateEnd = mJavaLocalDateStart.plusMonths(MONTHS_BETWEEN);
        mJavaLocalDateTimeStart = mJavaLocalDateStart.atStartOfDay();
        mJavaLocalDateTimeEnd = mJavaLocalDateTimeStart.plusMonths(MONTHS_BETWEEN);
        mJavaZoneDateTimeStart = java.time.ZonedDateTime.now()
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        mJavaZoneDateTimeEnd = mJavaZoneDateTimeStart.plusMonths(MONTHS_BETWEEN);
    }

    @Benchmark
    public void emptyMethod() {
    }

    @Benchmark
    public int jodaDateTime() {
        return Weeks.weeksBetween(mJodaDateTimeStart, mJodaDateTimeEnd).getWeeks();
    }

    @Benchmark
    public int jodaLocalDateTime() {
        return Weeks.weeksBetween(mJodaLocalDateTimeStart, mJodaLocalDateTimeEnd).getWeeks();
    }

    @Benchmark
    public int jodaLocalDate() {
        return Weeks.weeksBetween(mJodaLocalDateStart, mJodaLocalDateEnd).getWeeks();
    }

    @Benchmark
    public long threeTenLocalDateTime() {
        return org.threeten.bp.temporal.
                ChronoUnit.WEEKS.between(mThreeTenLocalDateTimeStart, mThreeTenLocalDateTimeEnd);
    }
    @Benchmark
    public long threeTenZoneDateTime() {
        return org.threeten.bp.temporal
                .ChronoUnit.WEEKS.between(mThreeTenZoneDateTimeStart, mThreeTenZoneDateTimeEnd);
    }

    @Benchmark
    public long threeTenLocalDate() {
        return org.threeten.bp.temporal
                .ChronoUnit.WEEKS.between(mThreeTenLocalDateStart, mThreeTenLocalDateEnd);
    }

    @Benchmark
    public long javaLocalDateTime() {
        return java.time.temporal
                .ChronoUnit.WEEKS.between(mJavaLocalDateTimeStart, mJavaLocalDateTimeEnd);
    }
    @Benchmark
    public long javaZoneDateTime() {
        return java.time.temporal
                .ChronoUnit.WEEKS.between(mJavaZoneDateTimeStart, mJavaZoneDateTimeEnd);
    }
    @Benchmark
    public long javaLocalDate() {
        return java.time.temporal
                .ChronoUnit.WEEKS.between(mJavaLocalDateStart, mJavaLocalDateEnd);
    }
}