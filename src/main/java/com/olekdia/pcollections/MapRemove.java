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

import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.javimmutable.collections.JImmutableMap;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.pcollections.HashPMap;
import org.pcollections.IntTreePMap;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class MapRemove {

    public static final int SIZE = 100_000;

    private volatile Random mRandom = null;
    private volatile Maps mMaps = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MapRemove.class.getSimpleName())
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
        for (int i = SIZE - 100; i >= 0; i--) {
            mMaps.mHashMap.remove(mMaps.mArray[i / 2]);
        }
        return mMaps.mHashMap;
    }

    @Benchmark
    public Object LinkedHashMap() {
        for (int i = SIZE - 100; i >= 0; i--) {
            mMaps.mLinkedHashMap.remove(mMaps.mArray[i / 2]);
        }
        return mMaps.mLinkedHashMap;
    }

    @Benchmark
    public Object TreeMap() {
        for (int i = SIZE - 100; i >= 0; i--) {
            mMaps.mTreeMap.remove(mMaps.mArray[i / 2]);
        }
        return mMaps.mTreeMap;
    }

    @Benchmark
    public Object ArrayMap() {
        for (int i = SIZE - 100; i >= 0; i--) {
            mMaps.mArrayMap.remove(mMaps.mArray[i / 2]);
        }
        return mMaps.mArrayMap;
    }

   @Benchmark
    public Object SparseArray() {
       for (int i = SIZE - 100; i >= 0; i--) {
           mMaps.mSparseArray.remove(mMaps.mArray[i / 2]);
       }
       return mMaps.mSparseArray;
    }

    @Benchmark
    public Object HashPMap() {
        HashPMap<Object, Integer> map = mMaps.mHashPMap;
        for (int i = SIZE - 100; i >= 0; i--) {
            map = map.minus(mMaps.mArray[i / 2]);
        }
        return map;
    }

    @Benchmark
    public Object IntTreePMap() {
        IntTreePMap<Integer> map = mMaps.mIntTreePMap;
        for (int i = SIZE - 100; i >= 0; i--) {
            map = map.minus(mMaps.mArray[i / 2]);
        }
        return map;
    }

    @Benchmark
    public Object JImmutableMap() {
        JImmutableMap<Object, Integer> map = mMaps.mJImmutableMap;
        for (int i = SIZE - 100; i >= 0; i--) {
            map = map.delete(mMaps.mArray[i / 2]);
        }
        return map;
    }

    @Benchmark
    public Object JImmutableSortedMap() {
        JImmutableMap<Object, Integer> map = mMaps.mJImmutableSortedMap;
        for (int i = SIZE - 100; i >= 0; i--) {
            map = map.delete(mMaps.mArray[i / 2]);
        }
        return map;
    }

    @Benchmark
    public Object EclipseMutableMap() {
        for (int i = SIZE - 100; i >= 0; i--) {
            mMaps.mEclipseMutableMap.remove(mMaps.mArray[i / 2]);
        }
        return mMaps.mEclipseMutableMap;
    }

    @Benchmark
    public Object EclipseImmutableMap() {
        ImmutableMap<Object, Integer> map = mMaps.mEclipseImmutableMap;
        MutableMap<Object, Integer> mmap = map.toMap();
        for (int i = SIZE - 100; i >= 0; i--) {
            mmap.remove(mMaps.mArray[i / 2]);
            map = mmap.toImmutable();
        }
        return map;
    }

    @Benchmark
    public Object GuavaImmutableMap() {
        com.google.common.collect.ImmutableMap<Object, Integer> map = mMaps.mGuavaImmutableMap;
        HashMap<Object, Integer> mmap = new HashMap<>(map);
        for (int i = SIZE - 100; i >= 0; i--) {
            mmap.remove(mMaps.mArray[i / 2]);
            map = new com.google.common.collect.ImmutableMap.Builder().putAll(mmap).build();
        }
        return map;
    }
}
/**
 Benchmark            Mode  Cnt             Score             Error  Units
 LinkedHashMap        avgt    3        303094.324 ±        8765.099  ns/op
 HashMap              avgt    3        315987.659 ±       34726.954  ns/op
 EclipseMutableMap    avgt    3        434462.707 ±       30555.853  ns/op
 ArrayMap             avgt    3       2422169.607 ±      155403.329  ns/op
 TreeMap              avgt    3       3033837.070 ±      215616.925  ns/op
 SparseArray          avgt    3       4741959.969 ±      181123.074  ns/op
 IntTreePMap          avgt    3      10098933.989 ±     1458478.288  ns/op
 HashPMap             avgt    3      11884758.012 ±     2950846.472  ns/op
 JImmutableSortedMap  avgt    3      15688043.420 ±     2003986.832  ns/op
 JImmutableMap        avgt    3      20559828.913 ±     2287710.153  ns/op
 EclipseImmutableMap  avgt    3  128161834031.333 ± 14286151614.724  ns/op
 GuavaImmutableMap    avgt    3  231715633402.333 ± 31239189652.561  ns/op
 */