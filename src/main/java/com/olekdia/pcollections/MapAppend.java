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
import org.eclipse.collections.impl.map.mutable.ConcurrentHashMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
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
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class MapAppend {

    public static final int SIZE = 100_000;

    volatile Integer[] mArray = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MapAppend.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mArray = new Integer[SIZE];

        final Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            mArray[i] = Integer.valueOf(random.nextInt(SIZE));
        }
    }

    @Benchmark
    public Object HashMap() {
        return CollectionHelper.put(new HashMap(), mArray);
    }

    @Benchmark
    public Object ConcurrentHashMap() {
        return CollectionHelper.put(new java.util.concurrent.ConcurrentHashMap<Object, Object>(), mArray);
    }

    @Benchmark
    public Object LinkedHashMap() {
        return CollectionHelper.put(new LinkedHashMap(), mArray);
    }

    @Benchmark
    public Object ArrayMap() {
        return CollectionHelper.put(new ArrayMap<Object, Object>(), mArray);
    }

    @Benchmark
    public Object SparseArray() {
        return CollectionHelper.put(new SparseArrayCompat<Integer>(), mArray);
    }

    @Benchmark
    public Object HashPMap() {
        return CollectionHelper.plus(HashTreePMap.empty(), mArray);
    }

    @Benchmark
    public Object IntPMap() {
        return CollectionHelper.plus(IntTreePMap.empty(), mArray);
    }

    @Benchmark
    public Object JImmutableMap() {
        return CollectionHelper.put(JImmutables.map(), mArray);
    }

    @Benchmark
    public Object JImmutableSortedMap() {
        return CollectionHelper.put(JImmutables.sortedMap(), mArray);
    }

    @Benchmark
    public Object JImmutableListMap() {
        return CollectionHelper.put(JImmutables.listMap(), mArray);
    }

    @Benchmark
    public Object JImmutableSetMap() {
        return CollectionHelper.put(JImmutables.setMap(), mArray);
    }

    @Benchmark
    public Object EclipseConcurrentHashMap() {
        return CollectionHelper.put(new ConcurrentHashMap<>(), mArray);
    }

    @Benchmark
    public Object EclipseUnifiedMap() {
        return CollectionHelper.put(new UnifiedMap<Object, Object>(), mArray);
    }

//    @Benchmark
//    public Object EclipseImmutableMap() {
//        return CollectionHelper.put(new UnifiedMap<Object, Object>().toImmutable(), mValues);
//    }
//
//    @Benchmark
//    public Object GuavaImmutableMap() {
//        return CollectionHelper.putGuava(
//                ImmutableMap.of(1,1,2,2),
//                mValues
//        );
//    }
}

/**
 Benchmark                           Mode  Cnt          Score   Error  Units
 MapAppend.EclipseUnifiedMap         avgt    2    4616675.939          ns/op
 MapAppend.HashMap                   avgt    2    5903254.857          ns/op
 MapAppend.LinkedHashMap             avgt    2    6696584.220          ns/op
 MapAppend.EclipseConcurrentHashMap  avgt    2    9804946.514          ns/op
 MapAppend.JImmutableSetMap          avgt    2   34985327.042          ns/op
 MapAppend.JImmutableMap             avgt    2   39071872.393          ns/op
 MapAppend.JImmutableListMap         avgt    2   40579842.197          ns/op
 MapAppend.IntPMap                   avgt    2   49561724.256          ns/op
 MapAppend.ConcurrentHashMap         avgt    2   11176820.377          ns/op
 MapAppend.JImmutableSortedMap       avgt    2   62281212.901          ns/op
 MapAppend.HashPMap                  avgt    2   70886001.687          ns/op
 MapAppend.SparseArray               avgt    2  316250855.844          ns/op
 MapAppend.ArrayMap                  avgt    2  506483387.800          ns/op

 */