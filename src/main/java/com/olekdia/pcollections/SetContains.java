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
public class SetContains {

    public static final int SIZE = 100_000;

    private volatile Sets mSets = null;

    volatile Integer mSearchedObject = null;


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SetContains.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mSets = new Sets(SIZE);
        mSearchedObject = mSets.mArray[88888];
    }

    @Benchmark
    public Object HashSet() {
        return mSets.mHashSet.contains(mSearchedObject);
    }

    @Benchmark
    public Object TreeSet() {
        return mSets.mTreeSet.contains(mSearchedObject);
    }

    @Benchmark
    public Object ArraySet() {
        return mSets.mArraySet.contains(mSearchedObject);
    }

    @Benchmark
    public Object MapPSet() {
        return mSets.mMapPSet.contains(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableHashSet() {
        return mSets.mJImmutableHashSet.contains(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableTreeSet() {
        return mSets.mJImmutableTreeSet.contains(mSearchedObject);
    }
}