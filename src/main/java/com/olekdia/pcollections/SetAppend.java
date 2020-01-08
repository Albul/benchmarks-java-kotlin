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
import org.javimmutable.collections.util.JImmutables;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.pcollections.ConsPStack;
import org.pcollections.HashTreePSet;
import org.pcollections.TreePVector;

import java.util.*;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class SetAppend {

    public static final int SIZE = 1_000_000;

    volatile Object[] mValues = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SetAppend.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mValues = new Object[SIZE];

        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            mValues[i] = Integer.valueOf(random.nextInt(SIZE));
        }
    }

    @Benchmark
    public Object HashSet() {
        return CollectionHelper.collectionAdd(new HashSet(), mValues);
    }

    @Benchmark
    public Object TreeSet() {
        return CollectionHelper.collectionAdd(new TreeSet(), mValues);
    }

    @Benchmark
    public Object ArraySet() {
        return CollectionHelper.collectionAdd(new ArraySet(), mValues);
    }

    @Benchmark
    public Object MapPSet() {
        return CollectionHelper.pCollectionPlus(HashTreePSet.empty(), mValues);
    }

    @Benchmark
    public Object JImmutableSet() {
        return CollectionHelper.jImmutableInsert(JImmutables.set(), mValues);
    }

    @Benchmark
    public Object JImmutableMultiset() {
        return CollectionHelper.jImmutableInsert(JImmutables.multiset(), mValues);
    }
}
/**
 Benchmark           Mode  Cnt            Score            Error  Units
 HashSet             avgt    3    130738821.386 ±   58064798.504  ns/op
 JImmutableSet       avgt    3    434394386.889 ±  213710947.704  ns/op
 JImmutableMultiset  avgt    3    568090231.593 ±  138751570.471  ns/op
 TreeSet             avgt    3    782037380.714 ±  262579653.825  ns/op
 MapPSet             avgt    3   1011342491.600 ±   64620203.511  ns/op
 ArraySet            avgt    3  41545653723.333 ± 8760972793.302  ns/op
 */