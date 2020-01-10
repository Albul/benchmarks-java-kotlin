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
    public Object JImmutableHashMap() {
        JImmutableMap<Object, Integer> map = mMaps.mJImmutableHashMap;
        for (int i = SIZE - 100; i >= 0; i--) {
            map = map.delete(mMaps.mArray[i / 2]);
        }
        return map;
    }

    @Benchmark
    public Object JImmutableTreeMap() {
        JImmutableMap<Object, Integer> map = mMaps.mJImmutableTreeMap;
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