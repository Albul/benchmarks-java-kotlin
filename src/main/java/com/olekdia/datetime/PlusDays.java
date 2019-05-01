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
import org.joda.time.Instant;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 Benchmark                        Mode  Cnt        Score         Error   Units
 PlusDays.emptyMethod            thrpt    3  2734596.462 ± 1214842.135  ops/ms
 PlusDays.jodaLocalDateTime      thrpt    3    88385.623 ±   31972.719  ops/ms
 PlusDays.time4JPlainTimestamp   thrpt    3    42208.842 ±    8242.766  ops/ms
 PlusDays.jodaDateTimeUTC        thrpt    3    41026.604 ±    2329.884  ops/ms
 PlusDays.javaLocalDate          thrpt    3    34184.331 ±     757.917  ops/ms
 PlusDays.threeTenLocalDate      thrpt    3    32113.875 ±   19075.244  ops/ms
 PlusDays.javaLocalDateTime      thrpt    3    30582.113 ±    1092.704  ops/ms
 PlusDays.threeTenLocalDateTime  thrpt    3    28733.767 ±   11007.222  ops/ms
 PlusDays.jodaLocalDate          thrpt    3    25305.501 ±   12448.679  ops/ms
 PlusDays.javaZoneDateTime       thrpt    3     9262.515 ±    2136.671  ops/ms
 PlusDays.threeTenZoneDateTime   thrpt    3     7748.664 ±    2291.185  ops/ms
 PlusDays.javaCalendar           thrpt    3     6316.605 ±    2998.024  ops/ms
 PlusDays.jodaDateTime           thrpt    3     3043.285 ±     245.133  ops/ms
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 3)
@Warmup(iterations = 2)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
public class PlusDays {

    private org.joda.time.DateTime mJodaDateTimeUTC;
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
    private net.time4j.PlainTimestamp m4JPlainTimestamp;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PlusDays.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup(Blackhole blackhole) {
        mJodaDateTimeUTC = org.joda.time.DateTime.now(DateTimeZone.UTC);
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

        m4JPlainTimestamp = SystemClock.inLocalView().now();
    }

    @Benchmark
    public void emptyMethod() {
    }

    @Benchmark
    public int jodaDateTimeUTC() {
        return mJodaDateTimeUTC.plusDays(1).getDayOfYear();
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

    @Benchmark
    public long time4JPlainTimestamp() {
        return m4JPlainTimestamp.plus(1, CalendarUnit.DAYS).getCalendarDate().getDayOfYear();
    }
}