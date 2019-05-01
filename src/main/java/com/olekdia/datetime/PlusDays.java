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
 Benchmark                        Mode  Cnt        Score        Error   Units
 PlusDays.emptyMethod            thrpt    3  2831597.152 ± 1648131.285  ops/ms
 PlusDays.jodaLocalDateTime      thrpt    3    84447.391 ±   73131.181  ops/ms
 PlusDays.threeTenLocalDate      thrpt    3    33225.927 ±    6684.740  ops/ms
 PlusDays.javaLocalDate          thrpt    3    31878.518 ±   33477.932  ops/ms
 PlusDays.threeTenLocalDateTime  thrpt    3    30221.530 ±     761.391  ops/ms
 PlusDays.javaLocalDateTime      thrpt    3    29311.461 ±   16409.772  ops/ms
 PlusDays.jodaLocalDate          thrpt    3    24911.455 ±   30700.210  ops/ms
 PlusDays.threeTenZoneDateTime   thrpt    3     7703.428 ±     516.216  ops/ms
 PlusDays.javaZoneDateTime       thrpt    3     7539.305 ±    4669.747  ops/ms
 PlusDays.javaCalendar           thrpt    3     6440.795 ±    1063.632  ops/ms
 PlusDays.jodaDateTime           thrpt    3     2865.198 ±    1146.998  ops/ms
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 3)
@Warmup(iterations = 2)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
public class PlusDays {

    private org.joda.time.DateTime mJodaDateTime;
    private org.joda.time.LocalDateTime mJodaLocalDateTime;
    private org.joda.time.LocalDate mJodaLocalDate;
    private org.threeten.bp.LocalDateTime mThreeTenLocalDateTime;
    private org.threeten.bp.ZonedDateTime mThreeTenZoneDateTime;
    private org.threeten.bp.LocalDate mThreeTenLocalDate;
    private java.time.LocalDateTime mJavaLocalDateTime;
    private java.time.ZonedDateTime mJavaZoneDateTime;
    private java.time.LocalDate mJavaLocalDate;
    private java.util.Calendar mJavaCalendar;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PlusDays.class.getSimpleName())
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

        mJavaCalendar = java.util.Calendar.getInstance();
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
        return mJodaDateTime.plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int jodaLocalDateTime() {
        return mJodaLocalDateTime.plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int jodaLocalDate() {
        return mJodaLocalDate.plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int threeTenLocalDateTime() {
        return mThreeTenLocalDateTime.plusDays(1).getDayOfYear();
    }
    @Benchmark
    public int threeTenZoneDateTime() {
        return mThreeTenZoneDateTime.plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int threeTenLocalDate() {
        return mThreeTenLocalDate.plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int javaLocalDateTime() {
        return mJavaLocalDateTime.plusDays(1).getDayOfYear();
    }
    @Benchmark
    public int javaZoneDateTime() {
        return mJavaZoneDateTime.plusDays(1).getDayOfYear();
    }
    @Benchmark
    public int javaLocalDate() {
        return mJavaLocalDate.plusDays(1).getDayOfYear();
    }

    @Benchmark
    public int javaCalendar() {
        mJavaCalendar.add(Calendar.DATE, 1);
        return mJavaCalendar.get(Calendar.DAY_OF_YEAR);
    }
}