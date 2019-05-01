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

import org.joda.time.DateTimeConstants;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark                         Mode  Cnt        Score         Error   Units
 * PlusWeeks.emptyMethod            thrpt    3  2664153.398 ± 3138890.206  ops/ms
 * PlusWeeks.jodaLocalDateTime      thrpt    3    69038.774 ±   31584.985  ops/ms
 * PlusWeeks.jodaLocalDate          thrpt    3    14185.858 ±     679.366  ops/ms
 * PlusWeeks.javaLocalDate          thrpt    3    13494.870 ±    6652.029  ops/ms
 * PlusWeeks.threeTenLocalDate      thrpt    3    11766.714 ±    2779.003  ops/ms
 * PlusWeeks.javaLocalDateTime      thrpt    3    10702.507 ±    5528.723  ops/ms
 * PlusWeeks.threeTenLocalDateTime  thrpt    3     9557.055 ±    1210.009  ops/ms
 * PlusWeeks.javaZoneDateTime       thrpt    3     3555.931 ±    1686.215  ops/ms
 * PlusWeeks.threeTenZoneDateTime   thrpt    3     3167.651 ±    1112.153  ops/ms
 * PlusWeeks.jodaDateTime           thrpt    3     2670.613 ±    2325.607  ops/ms
 * PlusWeeks.javaCalendar           thrpt    3     1839.816 ±    2514.172  ops/ms
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 3)
@Warmup(iterations = 2)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
public class PlusWeeks {

    private org.joda.time.DateTime mJodaDateTime;
    private org.joda.time.LocalDateTime mJodaLocalDateTime;
    private org.joda.time.LocalDate mJodaLocalDate;
    private org.threeten.bp.LocalDateTime mThreeTenLocalDateTime;
    private org.threeten.bp.ZonedDateTime mThreeTenZoneDateTime;
    private org.threeten.bp.LocalDate mThreeTenLocalDate;
    private java.time.LocalDateTime mJavaLocalDateTime;
    private java.time.ZonedDateTime mJavaZoneDateTime;
    private java.time.LocalDate mJavaLocalDate;
    private Calendar mJavaCalendar;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PlusWeeks.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup(Blackhole blackhole) {
        mJodaDateTime = org.joda.time.DateTime.now().withTimeAtStartOfDay();
        mJodaLocalDate = org.joda.time.LocalDate.now();
        mJodaLocalDateTime = mJodaLocalDate.toDateTimeAtStartOfDay().toLocalDateTime();

        mThreeTenLocalDate = org.threeten.bp.LocalDate.now();
        mThreeTenLocalDateTime = mThreeTenLocalDate.atStartOfDay();
        mThreeTenZoneDateTime = org.threeten.bp.ZonedDateTime.now()
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        mJavaLocalDate = java.time.LocalDate.now();
        mJavaLocalDateTime = mJavaLocalDate.atStartOfDay();
        mJavaZoneDateTime = java.time.ZonedDateTime.now()
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        mJavaCalendar = Calendar.getInstance();
        mJavaCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mJavaCalendar.set(Calendar.MINUTE, 0);
        mJavaCalendar.set(Calendar.SECOND, 0);
        mJavaCalendar.set(Calendar.MILLISECOND, 0);
        blackhole.consume(mJavaCalendar.getTimeInMillis());
    }

    @Benchmark
    public void emptyMethod() {
    }

    @Benchmark
    public int jodaDateTime() {
        return mJodaDateTime.withDayOfWeek(DateTimeConstants.MONDAY).plusWeeks(3).getDayOfWeek();
    }

    @Benchmark
    public int jodaLocalDateTime() {
        return mJodaLocalDateTime.withDayOfWeek(DateTimeConstants.MONDAY).plusWeeks(3).getDayOfWeek();
    }

    @Benchmark
    public int jodaLocalDate() {
        return mJodaLocalDate.withDayOfWeek(DateTimeConstants.MONDAY).plusWeeks(3).getDayOfWeek();
    }

    @Benchmark
    public int threeTenLocalDateTime() {
        return mThreeTenLocalDateTime.with(org.threeten.bp.temporal.TemporalAdjusters.previous(org.threeten.bp.DayOfWeek.MONDAY))
                .plusWeeks(3).getDayOfWeek().getValue();
    }
    @Benchmark
    public int threeTenZoneDateTime() {
        return mThreeTenZoneDateTime.with(org.threeten.bp.temporal.TemporalAdjusters.previous(org.threeten.bp.DayOfWeek.MONDAY))
                .plusWeeks(3).getDayOfWeek().getValue();
    }

    @Benchmark
    public int threeTenLocalDate() {
        return mThreeTenLocalDate.with(org.threeten.bp.temporal.TemporalAdjusters.previous(org.threeten.bp.DayOfWeek.MONDAY))
                .plusWeeks(3).getDayOfWeek().getValue();
    }

    @Benchmark
    public int javaLocalDateTime() {
        return mJavaLocalDateTime.with(java.time.temporal.TemporalAdjusters.previous(java.time.DayOfWeek.MONDAY))
                .plusWeeks(3).getDayOfWeek().getValue();
    }
    @Benchmark
    public int javaZoneDateTime() {
        return mJavaZoneDateTime.with(java.time.temporal.TemporalAdjusters.previous(java.time.DayOfWeek.MONDAY))
                .plusWeeks(3).getDayOfWeek().getValue();

    }
    @Benchmark
    public int javaLocalDate() {
        return mJavaLocalDate.with(java.time.temporal.TemporalAdjusters.previous(java.time.DayOfWeek.MONDAY))
                .plusWeeks(3).getDayOfWeek().getValue();
    }

    @Benchmark
    public int javaCalendar() {
        mJavaCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        mJavaCalendar.add(Calendar.WEEK_OF_YEAR, 3);
        return mJavaCalendar.get(Calendar.WEEK_OF_YEAR);
    }
}