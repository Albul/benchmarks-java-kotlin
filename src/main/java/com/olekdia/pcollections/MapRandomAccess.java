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
package com.olekdia.pcollections;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class MapRandomAccess {

    public static final int SIZE = 100_000;

    private volatile Random mRandom = null;
    private volatile Maps mMaps = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MapRandomAccess.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mRandom = new Random();
        mMaps = new Maps(SIZE);
    }

    @Benchmark
    public Object HashMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mHashMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

    @Benchmark
    public Object LinkedHashMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mLinkedHashMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

    @Benchmark
    public Object TreeMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mTreeMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

    @Benchmark
    public Object ArrayMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mArrayMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

   @Benchmark
    public Object SparseArray() {
       Integer value = null;
       for (int i = 0; i < SIZE; i++) {
           value = mMaps.mSparseArray.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
       }
       return value;
    }

    @Benchmark
    public Object HashPMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mHashPMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

    @Benchmark
    public Object IntTreePMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mIntTreePMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

    @Benchmark
    public Object JImmutableMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mJImmutableMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

    @Benchmark
    public Object JImmutableSortedMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mJImmutableSortedMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

    @Benchmark
    public Object EclipseMutableMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mEclipseMutableMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

    @Benchmark
    public Object EclipseImmutableMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mEclipseImmutableMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }

    @Benchmark
    public Object GuavaImmutableMap() {
        Integer value = null;
        for (int i = 0; i < SIZE; i++) {
            value = mMaps.mGuavaImmutableMap.get(mMaps.mArray[mRandom.nextInt(SIZE)]);
        }
        return value;
    }
}