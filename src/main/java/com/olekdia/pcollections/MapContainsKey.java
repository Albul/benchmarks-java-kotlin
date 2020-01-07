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

import androidx.collection.ArrayMap;
import androidx.collection.SparseArrayCompat;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.javimmutable.collections.JImmutableListMap;
import org.javimmutable.collections.JImmutableMap;
import org.javimmutable.collections.JImmutableSetMap;
import org.javimmutable.collections.util.JImmutables;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;
import org.pcollections.IntTreePMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class MapContainsKey {

    public static final int SIZE = 500_000;

    private volatile Maps mMaps = null;
    private volatile Integer mSearchedObject = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MapContainsKey.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mMaps = new Maps(SIZE);
        mSearchedObject = mMaps.mArray[388_888];
    }

    @Benchmark
    public boolean HashMap() {
        return mMaps.mHashMap.containsKey(mSearchedObject);
    }

    @Benchmark
    public boolean LinkedHashMap() {
        return mMaps.mLinkedHashMap.containsKey(mSearchedObject);
    }

    @Benchmark
    public boolean TreeMap() {
        return mMaps.mTreeMap.containsKey(mSearchedObject);
    }

    @Benchmark
    public boolean ArrayMap() {
        return mMaps.mArrayMap.containsKey(mSearchedObject);
    }

   @Benchmark
    public boolean SparseArray() {
        return mMaps.mSparseArray.containsKey(mSearchedObject);
    }

    @Benchmark
    public boolean HashPMap() {
        return mMaps.mHashPMap.containsKey(mSearchedObject);
    }

    @Benchmark
    public boolean IntTreePMap() {
        return mMaps.mIntTreePMap.containsKey(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableMap() {
        return mMaps.mJImmutableMap.find(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableSortedMap() {
        return mMaps.mJImmutableSortedMap.find(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableListMap() {
        return mMaps.mJImmutableListMap.find(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableSetMap() {
        return mMaps.mJImmutableSetMap.contains(mSearchedObject);
    }

    @Benchmark
    public Object EclipseMutableMap() {
        return mMaps.mEclipseMutableMap.containsKey(mSearchedObject);
    }

    @Benchmark
    public Object EclipseImmutableMap() {
        return mMaps.mEclipseImmutableMap.containsKey(mSearchedObject);
    }

    @Benchmark
    public Object GuavaImmutableMap() {
        return mMaps.mGuavaImmutableMap.containsKey(mSearchedObject);
    }
}
/*
Benchmark                             Mode  Cnt   Score     Error  Units
MapContainsKeyKt.HashMap              avgt    3   5.440 ±   0.269  ns/op
MapContainsKeyKt.LinkedHashMap        avgt    3   5.615 ±   0.235  ns/op
MapContainsKeyKt.EclipseMutableMap    avgt    3   6.447 ±   0.789  ns/op
MapContainsKeyKt.EclipseImmutableMap  avgt    3   6.633 ±   0.239  ns/op
MapContainsKeyKt.GuavaImmutableMap    avgt    3   7.482 ±   0.995  ns/op
MapContainsKeyKt.SparseArray          avgt    3  28.768 ±   2.288  ns/op
MapContainsKeyKt.ArrayMap             avgt    3  31.292 ±   2.682  ns/op
MapContainsKeyKt.JImmutableMap        avgt    3  31.549 ± 174.154  ns/op
MapContainsKeyKt.TreeMap              avgt    3  39.687 ±   4.201  ns/op
MapContainsKeyKt.IntTreePMap          avgt    3  46.327 ±   3.100  ns/op
MapContainsKeyKt.HashPMap             avgt    3  72.740 ± 645.224  ns/op
MapContainsKeyKt.JImmutableSortedMap  avgt    3  80.044 ± 557.053  ns/op
 */