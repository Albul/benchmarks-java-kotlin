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
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
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
 PlusWeeks.emptyMethod            thrpt    4  2848690.339 ± 530332.321  ops/ms
 PlusWeeks.jodaLocalDateTime      thrpt    4    71962.070 ±   4952.688  ops/ms
 PlusWeeks.time4JPlainTimestamp   thrpt    4    44332.292 ±   3179.340  ops/ms
 PlusWeeks.jodaDateTime0UTC       thrpt    4    23387.710 ±   1078.957  ops/ms
 PlusWeeks.jodaDateTimeUTC        thrpt    4    23341.561 ±    843.104  ops/ms
 PlusWeeks.jodaLocalDate          thrpt    4    14558.057 ±    873.697  ops/ms
 PlusWeeks.javaLocalDate          thrpt    4    12780.260 ±   3023.632  ops/ms
 PlusWeeks.threeTenLocalDate      thrpt    4    12312.915 ±    787.278  ops/ms
 PlusWeeks.javaLocalDateTime      thrpt    4    10924.023 ±    752.543  ops/ms
 PlusWeeks.threeTenLocalDateTime  thrpt    4     9919.074 ±    712.080  ops/ms
 PlusWeeks.javaZoneDateTime       thrpt    4     3821.568 ±    392.846  ops/ms
 PlusWeeks.threeTenZoneDateTime   thrpt    4     3460.660 ±     48.649  ops/ms
 PlusWeeks.jodaDateTime           thrpt    4     2870.329 ±    143.604  ops/ms
 PlusWeeks.javaCalendar           thrpt    4     1983.252 ±    387.999  ops/ms
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 4, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput})
public class PlusWeeks {

    private org.joda.time.DateTime mJodaDateTimeUTC;
    private org.joda.time.DateTime mJodaDateTime0UTC;
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
    private net.time4j.PlainTimestamp m4JPlainTimestamp;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PlusWeeks.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup(Blackhole blackhole) {
        mJodaDateTime0UTC = org.joda.time.DateTime.now(DateTimeZone.UTC).withTimeAtStartOfDay();
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

        mJavaCalendar = Calendar.getInstance();
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
    public int jodaDateTime0UTC() {
        return mJodaDateTime0UTC.withDayOfWeek(DateTimeConstants.MONDAY).plusWeeks(3).getDayOfWeek();
    }

    @Benchmark
    public int jodaDateTimeUTC() {
        return mJodaDateTimeUTC.withDayOfWeek(DateTimeConstants.MONDAY).plusWeeks(3).getDayOfWeek();
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
        return mJavaCalendar.get(Calendar.DAY_OF_WEEK);
    }

    @Benchmark
    public long time4JPlainTimestamp() {
        return m4JPlainTimestamp.plus(3, CalendarUnit.WEEKS).getCalendarDate().getDayOfWeek().getValue();
    }
}