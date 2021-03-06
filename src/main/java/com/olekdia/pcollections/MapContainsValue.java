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

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class MapContainsValue {

    public static final int SIZE = 100_000;

    private volatile Maps mMaps = null;
    private volatile Integer mSearchedObject = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MapContainsValue.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mMaps = new Maps(SIZE);
        mSearchedObject = mMaps.mArray[88_888];
    }

    @Benchmark
    public boolean HashMap() {
        return mMaps.mHashMap.containsValue(mSearchedObject);
    }

    @Benchmark
    public boolean LinkedHashMap() {
        return mMaps.mLinkedHashMap.containsValue(mSearchedObject);
    }

    @Benchmark
    public boolean TreeMap() {
        return mMaps.mTreeMap.containsValue(mSearchedObject);
    }

    @Benchmark
    public boolean ArrayMap() {
        return mMaps.mArrayMap.containsValue(mSearchedObject);
    }

   @Benchmark
    public boolean SparseArray() {
        return mMaps.mSparseArray.containsValue(mSearchedObject);
    }

    @Benchmark
    public boolean HashPMap() {
        return mMaps.mHashPMap.containsValue(mSearchedObject);
    }

    @Benchmark
    public boolean IntTreePMap() {
        return mMaps.mIntTreePMap.containsValue(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableHashMap() {
        return mMaps.mJImmutableHashMap.getMap().containsValue(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableTreeMap() {
        return mMaps.mJImmutableTreeMap.getMap().containsValue(mSearchedObject);
    }

    @Benchmark
    public Object EclipseMutableMap() {
        return mMaps.mEclipseMutableMap.containsValue(mSearchedObject);
    }

    @Benchmark
    public Object EclipseImmutableMap() {
        return mMaps.mEclipseImmutableMap.containsValue(mSearchedObject);
    }

    @Benchmark
    public Object GuavaImmutableMap() {
        return mMaps.mGuavaImmutableMap.containsValue(mSearchedObject);
    }
}