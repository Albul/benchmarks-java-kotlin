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
public class MapIterate {

    public static final int SIZE = 100_000;

    private volatile Maps mMaps = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MapIterate.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mMaps = new Maps(SIZE);
    }

    @Benchmark
    public Object HashMap() {
        return CollectionHelper.iterate(mMaps.mHashMap.values());
    }

    @Benchmark
    public Object LinkedHashMap() {
        return CollectionHelper.iterate(mMaps.mLinkedHashMap.values());
    }

    @Benchmark
    public Object TreeMap() {
        return CollectionHelper.iterate(mMaps.mTreeMap.values());
    }

    @Benchmark
    public Object ArrayMap() {
        return CollectionHelper.iterate(mMaps.mArrayMap.values());
    }

   @Benchmark
    public Object SparseArray() {
        long sum = 0L;
        for (int i = 0; i <= mMaps.mSparseArray.size(); i++) {
            sum += mMaps.mSparseArray.valueAt(i);
        }
        return sum;
    }

    @Benchmark
    public Object HashPMap() {
        return CollectionHelper.iterate(mMaps.mHashPMap.values());
    }

    @Benchmark
    public Object IntTreePMap() {
        return CollectionHelper.iterate(mMaps.mIntTreePMap.values());
    }

    @Benchmark
    public Object JImmutableMap() {
        return CollectionHelper.iterate(mMaps.mJImmutableMap.values());
    }

    @Benchmark
    public Object JImmutableSortedMap() {
        return CollectionHelper.iterate(mMaps.mJImmutableSortedMap.values());
    }

    @Benchmark
    public Object EclipseMutableMap() {
        return CollectionHelper.iterate(mMaps.mEclipseMutableMap.values());
    }

    @Benchmark
    public Object EclipseImmutableMap() {
        return CollectionHelper.iterate(mMaps.mEclipseImmutableMap.iterator());
    }

    @Benchmark
    public Object GuavaImmutableMap() {
        return CollectionHelper.iterate(mMaps.mGuavaImmutableMap.values());
    }
}
/**
 Benchmark                       Mode  Cnt         Score         Error  Units
 MapIterate.ArrayMap             avgt    3    232476.654 ±   49492.634  ns/op
 MapIterate.EclipseMutableMap    avgt    3    446745.391 ±   97728.436  ns/op
 MapIterate.LinkedHashMap        avgt    3    483910.078 ±  180635.332  ns/op
 MapIterate.GuavaImmutableMap    avgt    3    520165.653 ± 1284320.917  ns/op
 MapIterate.EclipseImmutableMap  avgt    3    567447.082 ± 1103396.067  ns/op
 MapIterate.HashMap              avgt    3    595179.428 ±  714329.433  ns/op
 MapIterate.TreeMap              avgt    3    682662.779 ±   31041.721  ns/op
 MapIterate.IntTreePMap          avgt    3   2403714.311 ±  413160.920  ns/op
 MapIterate.HashPMap             avgt    3   3510105.051 ±  353017.160  ns/op
 MapIterate.JImmutableSortedMap  avgt    3   7237417.355 ± 4619409.769  ns/op
 MapIterate.JImmutableMap        avgt    3  13431226.785 ±  914941.410  ns/op
 */