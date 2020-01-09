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
import org.pcollections.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class ListContains {

    public static final int SIZE = 100_000;

    volatile Lists mLists = null;
    volatile Integer mSearchedObject = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListContains.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mLists = new Lists(SIZE);
        mSearchedObject = mLists.mArray[88888];
    }

    @Benchmark
    public Object ArrayList() {
        return mLists.mArrayList.contains(mSearchedObject);
    }

    @Benchmark
    public Object Stack() {
        return mLists.mStack.contains(mSearchedObject);
    }

    @Benchmark
    public Object LinkedList() {
        return mLists.mLinkedList.contains(mSearchedObject);
    }

    @Benchmark
    public Object ConsPStack() {
        return mLists.mConsPStack.contains(mSearchedObject);
    }

    @Benchmark
    public Object TreePVector() {
        return mLists.mTreePVector.contains(mSearchedObject);
    }

    @Benchmark
    public Object JImmutableList() {
        final Object searchedObj = mSearchedObject;

        for (Object obj : mLists.mJImmutableList) {
            if (obj.equals(searchedObj)) {
                return true;
            }
        }
        return false;
    }

    @Benchmark
    public Object JImmutableStack() {
        final Object searchedObj = mSearchedObject;

        for (Object obj : mLists.mJImmutableStack) {
            if (obj.equals(searchedObj)) {
                return true;
            }
        }
        return false;
    }
}