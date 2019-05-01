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

import net.time4j.PlainTimestamp;
import net.time4j.SystemClock;
import org.joda.time.DateTimeZone;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.threeten.bp.ZoneOffset;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark                             Mode  Cnt        Score        Error   Units
 * GetFields.emptyMethod                thrpt    5  2707960.398 ± 333365.424  ops/ms
 * GetFields.time4JPlainTimestamp       thrpt    5   199520.969 ±   6616.082  ops/ms
 * GetFields.javaCalendar               thrpt    5   157856.991 ±  29359.464  ops/ms
 * GetFields.threeTenLocalDateTime      thrpt    5   143348.120 ±   7436.794  ops/ms
 * GetFields.javaLocalDateTime          thrpt    5   139233.435 ±  36577.531  ops/ms
 * GetFields.threeTenZoneDateTime       thrpt    5   127376.485 ±   7084.874  ops/ms
 * GetFields.javaZoneDateTime           thrpt    5   124499.758 ±   7337.896  ops/ms
 * GetFields.threeTenOffsetDateTimeUtc  thrpt    5   123150.854 ±  14638.145  ops/ms
 * GetFields.jodaLocalDateTime          thrpt    5     9151.977 ±   1297.086  ops/ms
 * GetFields.jodaDateTimeUTC            thrpt    5     8705.570 ±    657.472  ops/ms
 * GetFields.jodaDateTime               thrpt    5     5775.587 ±    359.390  ops/ms
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput})
public class GetFields {

    private org.joda.time.DateTime mJodaDateTimeUTC;
    private org.joda.time.DateTime mJodaDateTime;
    private org.joda.time.LocalDateTime mJodaLocalDateTime;
    private org.threeten.bp.LocalDateTime mThreeTenLocalDateTime;
    private org.threeten.bp.ZonedDateTime mThreeTenZoneDateTime;
    private org.threeten.bp.OffsetDateTime mThreeTenOffsetDateTimeUTC;
    private java.time.LocalDateTime mJavaLocalDateTime;
    private java.time.ZonedDateTime mJavaZoneDateTime;
    private Calendar mJavaCalendar;
    private PlainTimestamp mTime4JPlainTimestamp;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(GetFields.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup(Blackhole blackhole) {
        mJodaDateTimeUTC = org.joda.time.DateTime.now(DateTimeZone.UTC).withTimeAtStartOfDay();
        mJodaDateTime = org.joda.time.DateTime.now().withTimeAtStartOfDay();
        mJodaLocalDateTime = mJodaDateTime.toLocalDateTime();

        mThreeTenZoneDateTime = org.threeten.bp.ZonedDateTime.now()
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        mThreeTenLocalDateTime = mThreeTenZoneDateTime.toLocalDateTime();

        mJavaZoneDateTime = java.time.ZonedDateTime.now()
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        mJavaLocalDateTime = mJavaZoneDateTime.toLocalDateTime();

        mThreeTenOffsetDateTimeUTC = org.threeten.bp.OffsetDateTime.now(ZoneOffset.UTC);

        mJavaCalendar = Calendar.getInstance();
        mJavaCalendar.set(Calendar.HOUR_OF_DAY, 0);
        mJavaCalendar.set(Calendar.MINUTE, 0);
        mJavaCalendar.set(Calendar.SECOND, 0);
        mJavaCalendar.set(Calendar.MILLISECOND, 0);

        mTime4JPlainTimestamp = SystemClock.inLocalView().now();
    }

    @Benchmark
    public void emptyMethod() {
    }

    @Benchmark
    public long jodaDateTime() {
        long total = 0;
        total += mJodaDateTime.getYear();
        total += mJodaDateTime.getMonthOfYear();
        total += mJodaDateTime.getDayOfMonth();
        total += mJodaDateTime.getHourOfDay();
        total += mJodaDateTime.getMinuteOfHour();
        total += mJodaDateTime.getSecondOfMinute();
        total += mJodaDateTime.getMillisOfSecond();

        return total;
    }

    @Benchmark
    public long jodaDateTimeUTC() {
        long total = 0;
        total += mJodaDateTimeUTC.getYear();
        total += mJodaDateTimeUTC.getMonthOfYear();
        total += mJodaDateTimeUTC.getDayOfMonth();
        total += mJodaDateTimeUTC.getHourOfDay();
        total += mJodaDateTimeUTC.getMinuteOfHour();
        total += mJodaDateTimeUTC.getSecondOfMinute();
        total += mJodaDateTimeUTC.getMillisOfSecond();

        return total;
    }

    @Benchmark
    public long jodaLocalDateTime() {
        long total = 0;
        total += mJodaLocalDateTime.getYear();
        total += mJodaLocalDateTime.getMonthOfYear();
        total += mJodaLocalDateTime.getDayOfMonth();
        total += mJodaLocalDateTime.getHourOfDay();
        total += mJodaLocalDateTime.getMinuteOfHour();
        total += mJodaLocalDateTime.getSecondOfMinute();
        total += mJodaLocalDateTime.getMillisOfSecond();

        return total;
    }

    @Benchmark
    public long threeTenLocalDateTime() {
        long total = 0;
        total += mThreeTenLocalDateTime.getYear();
        total += mThreeTenLocalDateTime.getMonth().getValue();
        total += mThreeTenLocalDateTime.getDayOfMonth();
        total += mThreeTenLocalDateTime.getHour();
        total += mThreeTenLocalDateTime.getMinute();
        total += mThreeTenLocalDateTime.getSecond();
        total += mThreeTenLocalDateTime.getNano();

        return total;
    }
    @Benchmark
    public long threeTenZoneDateTime() {
        long total = 0;
        total += mThreeTenZoneDateTime.getYear();
        total += mThreeTenZoneDateTime.getMonth().getValue();
        total += mThreeTenZoneDateTime.getDayOfMonth();
        total += mThreeTenZoneDateTime.getHour();
        total += mThreeTenZoneDateTime.getMinute();
        total += mThreeTenZoneDateTime.getSecond();
        total += mThreeTenZoneDateTime.getNano();

        return total;
    }

    @Benchmark
    public long threeTenOffsetDateTimeUtc() {
        long total = 0;
        total += mThreeTenOffsetDateTimeUTC.getYear();
        total += mThreeTenOffsetDateTimeUTC.getMonth().getValue();
        total += mThreeTenOffsetDateTimeUTC.getDayOfMonth();
        total += mThreeTenOffsetDateTimeUTC.getHour();
        total += mThreeTenOffsetDateTimeUTC.getMinute();
        total += mThreeTenOffsetDateTimeUTC.getSecond();
        total += mThreeTenOffsetDateTimeUTC.getNano();

        return total;
    }


    @Benchmark
    public long javaLocalDateTime() {
        long total = 0;
        total += mJavaLocalDateTime.getYear();
        total += mJavaLocalDateTime.getMonth().getValue();
        total += mJavaLocalDateTime.getDayOfMonth();
        total += mJavaLocalDateTime.getHour();
        total += mJavaLocalDateTime.getMinute();
        total += mJavaLocalDateTime.getSecond();
        total += mJavaLocalDateTime.getNano();

        return total;
    }
    @Benchmark
    public long javaZoneDateTime() {
        long total = 0;
        total += mJavaZoneDateTime.getYear();
        total += mJavaZoneDateTime.getMonth().getValue();
        total += mJavaZoneDateTime.getDayOfMonth();
        total += mJavaZoneDateTime.getHour();
        total += mJavaZoneDateTime.getMinute();
        total += mJavaZoneDateTime.getSecond();
        total += mJavaZoneDateTime.getNano();

        return total;
    }

    @Benchmark
    public long javaCalendar() {
        long total = 0;
        total += mJavaCalendar.get(Calendar.YEAR);
        total += mJavaCalendar.get(Calendar.MONTH);
        total += mJavaCalendar.get(Calendar.DAY_OF_MONTH);
        total += mJavaCalendar.get(Calendar.HOUR_OF_DAY);
        total += mJavaCalendar.get(Calendar.MINUTE);
        total += mJavaCalendar.get(Calendar.SECOND);
        total += mJavaCalendar.get(Calendar.MILLISECOND);
        return total;
    }

    @Benchmark
    public long time4JPlainTimestamp() {
        long total = 0;
        total += mTime4JPlainTimestamp.getYear();
        total += mTime4JPlainTimestamp.getMonth();
        total += mTime4JPlainTimestamp.getDayOfMonth();
        total += mTime4JPlainTimestamp.getHour();
        total += mTime4JPlainTimestamp.getMinute();
        total += mTime4JPlainTimestamp.getSecond();
        total += mTime4JPlainTimestamp.getNanosecond();
        return total;
    }
}