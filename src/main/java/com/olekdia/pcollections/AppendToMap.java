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
import androidx.collection.ArraySet;
import androidx.collection.SparseArrayCompat;
import org.javimmutable.collections.Insertable;
import org.javimmutable.collections.JImmutableListMap;
import org.javimmutable.collections.JImmutableMap;
import org.javimmutable.collections.JImmutableSetMap;
import org.javimmutable.collections.util.JImmutables;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.pcollections.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 Benchmark                             Mode  Cnt            Score            Error  Units
 AppendToMap.putToHashMap              avgt    3    131601002.774 ±   59181881.585  ns/op
 AppendToMap.putToLinkedHashMap        avgt    3    152490689.005 ±   60311349.743  ns/op
 AppendToMap.putToJImmutableMap        avgt    3    515715286.067 ±   64273061.875  ns/op
 AppendToMap.putToJImmutableSetMap     avgt    3    524345122.967 ±  278033776.931  ns/op
 AppendToMap.putToJImmutableListMap    avgt    3    618397911.310 ±  190592050.917  ns/op
 AppendToMap.putToIntPMap              avgt    3    913247820.056 ±   70296899.101  ns/op
 AppendToMap.putToHashPMap             avgt    3   1243807686.083 ±  765679932.553  ns/op
 AppendToMap.putToJImmutableSortedMap  avgt    3   1271168615.750 ±  158861998.317  ns/op
 AppendToMap.appendToSparseArray       avgt    3  41254405658.333 ± 3933595908.116  ns/op
 AppendToMap.putToSparseArray          avgt    3  41942275143.333 ±  443148217.002  ns/op
 AppendToMap.putToArrayMap             avgt    3  75115745275.667 ± 6946814944.747  ns/op
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class AppendToMap {

    public static final int SIZE = 1_000_000;

    volatile Integer[] mValues = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(AppendToMap.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mValues = new Integer[SIZE];

        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            mValues[i] = Integer.valueOf(random.nextInt(SIZE));
        }
    }

    @Benchmark
    public Object putToHashMap() {
        return mapPut(new HashMap(), mValues);
    }

    @Benchmark
    public Object putToLinkedHashMap() {
        return mapPut(new LinkedHashMap(), mValues);
    }

    @Benchmark
    public Object putToArrayMap() {
        return mapPut(new ArrayMap<String, Object>(), mValues);
    }

    @Benchmark
    public Object appendToSparseArray() {
        return sparseAppend(new SparseArrayCompat<Integer>(), mValues);
    }

    @Benchmark
    public Object putToSparseArray() {
        return sparsePut(new SparseArrayCompat<Integer>(), mValues);
    }

    @Benchmark
    public Object putToHashPMap() {
        return pMapPlus(HashTreePMap.empty(), mValues);
    }

    @Benchmark
    public Object putToIntPMap() {
        return pMapPlus(IntTreePMap.empty(), mValues);
    }

    @Benchmark
    public Object putToJImmutableMap() {
        return jMapAssign(JImmutables.map(), mValues);
    }

    @Benchmark
    public Object putToJImmutableSortedMap() {
        return jMapAssign(JImmutables.sortedMap(), mValues);
    }

    @Benchmark
    public Object putToJImmutableListMap() {
        return jListMapInsert(JImmutables.listMap()	, mValues);
    }

    @Benchmark
    public Object putToJImmutableSetMap() {
        return jSetMapInsert(JImmutables.setMap()	, mValues);
    }

    private static PMap pMapPlus(PMap m, final Object[] list) {
        for (Object e : list) m = m.plus(e, e);
        return m;
    }

    private static <T extends Map> T mapPut(final T m, final Object[] list) {
        for (Object e : list) m.put(e, e);
        return m;
    }

    private static SparseArrayCompat sparseAppend(final SparseArrayCompat m, final Integer[] list) {
        for (Integer e : list) m.append(e, e);
        return m;
    }

    private static SparseArrayCompat sparsePut(final SparseArrayCompat m, final Integer[] list) {
        for (Integer e : list) m.put(e, e);
        return m;
    }

    private static JImmutableMap jMapAssign(JImmutableMap m, final Object[] list) {
        for (Object e : list) m = m.assign(e, e);
        return m;
    }

    private static JImmutableSetMap jSetMapInsert(JImmutableSetMap m, final Object[] list) {
        for (Object e : list) m = m.insert(e, e);
        return m;
    }

    private static JImmutableListMap jListMapInsert(JImmutableListMap m, final Object[] list) {
        for (Object e : list) m = m.insert(e, e);
        return m;
    }
}