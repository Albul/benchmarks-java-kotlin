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
import kotlin.jvm.Volatile;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.javimmutable.collections.*;
import org.javimmutable.collections.util.JImmutables;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.pcollections.*;

import java.util.*;
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
    public Object JImmutableMap() {
        return mMaps.mJImmutableMap.getMap().containsValue(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableSortedMap() {
        return mMaps.mJImmutableSortedMap.getMap().containsValue(mSearchedObject);
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
/**
 Benchmark                        Mode  Cnt        Score        Error  Units
 MapContains.SparseArray          avgt    3    14385.679 ±   1868.603  ns/op
 MapContains.ArrayMap             avgt    3    31600.787 ±   2524.326  ns/op
 MapContains.GuavaImmutableMap    avgt    3    68962.743 ±  25110.194  ns/op
 MapContains.EclipseImmutableMap  avgt    3   205202.339 ± 215188.159  ns/op
 MapContains.HashMap              avgt    3   372728.530 ±  19711.772  ns/op
 MapContains.EclipseMutableMap    avgt    3   433659.820 ±  24302.814  ns/op
 MapContains.LinkedHashMap        avgt    3   484310.017 ± 648762.363  ns/op
 MapContains.TreeMap              avgt    3   489393.158 ±  55240.791  ns/op
 MapContains.IntTreePMap          avgt    3   844354.772 ± 184813.408  ns/op
 MapContains.HashPMap             avgt    3  1109556.589 ± 142587.544  ns/op
 JImmutableSortedMap              avgt    3  3297182.135 ± 502657.751  ns/op
 JImmutableMap                    avgt    3  7285167.781 ± 693067.381  ns/op
 */