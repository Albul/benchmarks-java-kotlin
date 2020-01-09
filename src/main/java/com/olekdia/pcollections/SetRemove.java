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

import org.javimmutable.collections.JImmutableMultiset;
import org.javimmutable.collections.JImmutableSet;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.pcollections.MapPSet;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class SetRemove {

    public static final int SIZE = 100_000;

    private volatile Sets mSets = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SetRemove.class.getSimpleName())
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
        for (int i = SIZE - 100; i >= SIZE / 2; i--) {
            mSets.mHashSet.remove(mSets.mArray[i / 2]);
        }
        return mSets.mHashSet;
    }

    @Benchmark
    public Object TreeSet() {
        for (int i = SIZE - 100; i >= SIZE / 2; i--) {
            mSets.mTreeSet.remove(mSets.mArray[i / 2]);
        }
        return mSets.mTreeSet;
    }

    @Benchmark
    public Object ArraySet() {
        for (int i = SIZE - 100; i >= SIZE / 2; i--) {
            mSets.mArraySet.remove(mSets.mArray[i / 2]);
        }
        return mSets.mArraySet;
    }

    @Benchmark
    public Object MapPSet() {
        MapPSet<Integer> s = mSets.mMapPSet;
        for (int i = SIZE - 100; i >= SIZE / 2; i--) {
            s = s.minus(mSets.mArray[i / 2]);
        }
        return s;
    }

    @Benchmark
    public Object JImmutableSet() {
        JImmutableSet<Integer> s = mSets.mJImmutableSet;
        for (int i = SIZE - 100; i >= SIZE / 2; i--) {
            s = s.delete(mSets.mArray[i / 2]);
        }
        return s;
    }

    @Benchmark
    public Object JImmutableMultiset() {
        JImmutableMultiset<Integer> s = mSets.mJImmutableMultiset;
        for (int i = SIZE - 100; i >= SIZE / 2; i--) {
            s = s.delete(mSets.mArray[i / 2]);
        }
        return s;
    }
}