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
import net.time4j.PlainDate;
import net.time4j.SystemClock;
import org.joda.time.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 Benchmark                              Mode  Cnt        Score        Error   Units
 DaysBetween.emptyMethod               thrpt    5  2936327.465 ± 118728.791  ops/ms
 DaysBetween.jodaDateTimeWithDuration  thrpt    5    92468.724 ±   2720.293  ops/ms
 DaysBetween.javaLocalDate             thrpt    5    58365.858 ±   2396.258  ops/ms
 DaysBetween.threeTenLocalDate         thrpt    5    57324.813 ±   1405.676  ops/ms
 DaysBetween.jodaLocalDate             thrpt    5    46846.123 ±   2737.699  ops/ms
 DaysBetween.threeTenLocalDateTime     thrpt    5    38140.154 ±   1476.085  ops/ms
 DaysBetween.javaLocalDateTime         thrpt    5    37993.317 ±   1109.025  ops/ms
 DaysBetween.javaZoneDateTime          thrpt    5    36549.282 ±   1106.375  ops/ms
 DaysBetween.threeTenZoneDateTime      thrpt    5    36480.015 ±   2239.400  ops/ms
 DaysBetween.jodaDateTime              thrpt    5    30310.337 ±   1095.348  ops/ms
 DaysBetween.jodaDateTimeWithInterval  thrpt    5    29608.832 ±   3598.556  ops/ms
 DaysBetween.jodaDateTimeUTC           thrpt    5    26396.740 ±    859.252  ops/ms
 DaysBetween.time4JPlainDate           thrpt    5    25490.917 ±    349.330  ops/ms
 DaysBetween.time4JPlainTimestamp      thrpt    5    19030.706 ±    530.084  ops/ms
 DaysBetween.javaCalendar              thrpt    5     2816.795 ±    117.897  ops/ms
 DaysBetween.jodaLocalDateTime         thrpt    5     2202.552 ±     59.912  ops/ms
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput})
public class DaysBetween {

    private org.joda.time.DateTime mJodaDateTimeUTCStart;
    private org.joda.time.DateTime mJodaDateTimeUTCEnd;
    private org.joda.time.DateTime mJodaDateTimeStart;
    private org.joda.time.DateTime mJodaDateTimeEnd;
    private org.joda.time.LocalDateTime mJodaLocalDateTimeStart;
    private org.joda.time.LocalDateTime mJodaLocalDateTimeEnd;
    private org.joda.time.LocalDate mJodaLocalDateStart;
    private org.joda.time.LocalDate mJodaLocalDateEnd;
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
    private long mJavaCalendarStart;
    private long mJavaCalendarEnd;
    private Calendar mJavaCalendarTmp;
    private net.time4j.PlainDate m4JPlainDateStart;
    private net.time4j.PlainDate m4JPlainDateEnd;
    private net.time4j.PlainTimestamp m4JPlainTimestampStart;
    private net.time4j.PlainTimestamp m4JPlainTimestampEnd;

    private final static int MONTHS_BETWEEN = 15;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DaysBetween.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup(Blackhole blackhole) {
        mJodaDateTimeUTCStart = org.joda.time.DateTime.now(DateTimeZone.UTC).withTimeAtStartOfDay();
        mJodaDateTimeUTCEnd = mJodaDateTimeUTCStart.plusMonths(MONTHS_BETWEEN);
        assert mJodaDateTimeUTCEnd.getZone().getID().equals(DateTimeZone.UTC.getID());

        mJodaDateTimeStart = org.joda.time.DateTime.now().withTimeAtStartOfDay();
        mJodaDateTimeEnd = mJodaDateTimeStart.plusMonths(MONTHS_BETWEEN);
        mJodaLocalDateStart = org.joda.time.LocalDate.now();
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

        mJavaCalendarTmp = Calendar.getInstance();
        mJavaCalendarTmp.set(Calendar.HOUR_OF_DAY, 0);
        mJavaCalendarTmp.set(Calendar.MINUTE, 0);
        mJavaCalendarTmp.set(Calendar.SECOND, 0);
        mJavaCalendarTmp.set(Calendar.MILLISECOND, 0);
        mJavaCalendarStart = mJavaCalendarTmp.getTimeInMillis();
        mJavaCalendarTmp.add(Calendar.MONTH, MONTHS_BETWEEN);
        mJavaCalendarEnd = mJavaCalendarTmp.getTimeInMillis();

        mJavaCalendarTmp = Calendar.getInstance();

        m4JPlainDateStart = SystemClock.inLocalView().today();
        m4JPlainDateEnd = m4JPlainDateStart.plus(MONTHS_BETWEEN, CalendarUnit.MONTHS);

        m4JPlainTimestampStart = SystemClock.inLocalView().now();
        m4JPlainTimestampEnd = m4JPlainTimestampStart.plus(MONTHS_BETWEEN, CalendarUnit.MONTHS);
    }

    @Benchmark
    public void emptyMethod() {
    }

    @Benchmark
    public int jodaDateTimeUTC() {
        return Days.daysBetween(mJodaDateTimeUTCStart, mJodaDateTimeUTCEnd).getDays();
    }

    @Benchmark
    public int jodaDateTime() {
        return Days.daysBetween(mJodaDateTimeStart, mJodaDateTimeEnd).getDays();
    }

    @Benchmark
    public long jodaDateTimeWithInterval() {
        return new Interval(mJodaDateTimeStart, mJodaDateTimeEnd).toDuration().getStandardDays();
    }
    @Benchmark
    public long jodaDateTimeWithDuration() {
        return new Duration(mJodaDateTimeStart, mJodaDateTimeEnd).getStandardDays();
    }

    @Benchmark
    public int jodaLocalDateTime() {
        return Days.daysBetween(mJodaLocalDateTimeStart, mJodaLocalDateTimeEnd).getDays();
    }

    @Benchmark
    public int jodaLocalDate() {
        return Days.daysBetween(mJodaLocalDateStart, mJodaLocalDateEnd).getDays();
    }

    @Benchmark
    public long threeTenLocalDateTime() {
        return org.threeten.bp.temporal
                .ChronoUnit.DAYS.between(mThreeTenLocalDateTimeStart, mThreeTenLocalDateTimeEnd);
    }
    @Benchmark
    public long threeTenZoneDateTime() {
        return org.threeten.bp.temporal
                .ChronoUnit.DAYS.between(mThreeTenZoneDateTimeStart, mThreeTenZoneDateTimeEnd);
    }

    @Benchmark
    public long threeTenLocalDate() {
        return org.threeten.bp.temporal
                .ChronoUnit.DAYS.between(mThreeTenLocalDateStart, mThreeTenLocalDateEnd);
    }

    @Benchmark
    public long javaLocalDateTime() {
        return java.time.temporal
                .ChronoUnit.DAYS.between(mJavaLocalDateTimeStart, mJavaLocalDateTimeEnd);
    }
    @Benchmark
    public long javaZoneDateTime() {
        return java.time.temporal
                .ChronoUnit.DAYS.between(mJavaZoneDateTimeStart, mJavaZoneDateTimeEnd);
    }
    @Benchmark
    public long javaLocalDate() {
        return java.time.temporal
                .ChronoUnit.DAYS.between(mJavaLocalDateStart, mJavaLocalDateEnd);
    }

    @Benchmark
    public int javaCalendar() {
        return numDaysBetween(mJavaCalendarTmp, mJavaCalendarStart, mJavaCalendarEnd);
    }

    private static int numDaysBetween(final Calendar c, final long fromTime, final long toTime) {
        int result = 0;
        if (toTime <= fromTime) {
            return result;
        }
        c.setTimeInMillis(toTime);
        final int toYear = c.get(Calendar.YEAR);
        result += c.get(Calendar.DAY_OF_YEAR);

        c.setTimeInMillis(fromTime);
        result -= c.get(Calendar.DAY_OF_YEAR);

        while (c.get(Calendar.YEAR) < toYear) {
            result += c.getActualMaximum(Calendar.DAY_OF_YEAR);
            c.add(Calendar.YEAR, 1);
        }

        return result;
    }

    @Benchmark
    public long time4JPlainDate() {
        return net.time4j.CalendarUnit.DAYS.between(m4JPlainDateStart, m4JPlainDateEnd);
    }

    @Benchmark
    public long time4JPlainTimestamp() {
        return net.time4j.CalendarUnit.DAYS.between(m4JPlainTimestampStart, m4JPlainTimestampEnd);
    }
}