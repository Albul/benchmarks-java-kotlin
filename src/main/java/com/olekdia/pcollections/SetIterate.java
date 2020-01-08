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

import androidx.collection.ArraySet;
import org.javimmutable.collections.JImmutableList;
import org.javimmutable.collections.JImmutableMultiset;
import org.javimmutable.collections.JImmutableSet;
import org.javimmutable.collections.JImmutableStack;
import org.javimmutable.collections.util.JImmutables;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.pcollections.ConsPStack;
import org.pcollections.HashTreePSet;
import org.pcollections.MapPSet;
import org.pcollections.TreePVector;

import java.util.*;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class SetIterate {

    public static final int SIZE = 200_000;

    private volatile Sets mSets = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SetIterate.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mSets = new Sets(SIZE);
    }

    @Benchmark
    public Object HashSet() {
        return CollectionHelper.iterate(mSets.mHashSet);
    }

    @Benchmark
    public Object TreeSet() {
        return CollectionHelper.iterate(mSets.mTreeSet);
    }

    @Benchmark
    public Object ArraySet() {
        return CollectionHelper.iterate(mSets.mArraySet);
    }

    @Benchmark
    public Object MapPSet() {
        return CollectionHelper.iterate(mSets.mMapPSet);
    }

    @Benchmark
    public Object JImmutableSet() {
        return CollectionHelper.iterate(mSets.mJImmutableSet);
    }

    @Benchmark
    public Object JImmutableMultiset() {
        return CollectionHelper.iterate(mSets.mJImmutableMultiset);
    }
}
/**
 Benchmar            Mode  Cnt         Score         Error  Units
 ArraySet            avgt    3    444388.283 ±   75054.628  ns/op
 HashSet             avgt    3   1984468.316 ± 2598625.356  ns/op
 TreeSet             avgt    3   2321796.720 ± 2474454.047  ns/op
 MapPSet             avgt    3  10379233.039 ± 1289215.156  ns/op
 JImmutableMultiset  avgt    3  19757874.889 ± 1037905.190  ns/op
 JImmutableSet       avgt    3  19788659.312 ± 1458216.162  ns/op
*/