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

import org.javimmutable.collections.JImmutableList;
import org.javimmutable.collections.JImmutableStack;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.pcollections.ConsPStack;
import org.pcollections.TreePVector;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class ListRemoveMiddle {

    public static final int SIZE = 100_000;

    volatile Lists mLists = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListRemoveMiddle.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mLists = new Lists(SIZE);
    }

    @Benchmark
    public Object ArrayList() {
        for (int i = SIZE - 100; i >= SIZE / 4; i--) {
            mLists.mArrayList.remove(mLists.mArray[i / 2]);
        }
        return mLists.mArrayList;
    }

    @Benchmark
    public Object Stack() {
        for (int i = SIZE - 100; i >= SIZE / 4; i--) {
            mLists.mStack.remove(mLists.mArray[i / 2]);
        }
        return mLists.mStack;
    }

    @Benchmark
    public Object LinkedList() {
        for (int i = SIZE - 100; i >= SIZE / 4; i--) {
            mLists.mLinkedList.remove(mLists.mArray[i / 2]);
        }
        return mLists.mLinkedList;
    }

    @Benchmark
    public Object ConsPStack() {
        ConsPStack<Integer> l = mLists.mConsPStack;
        for (int i = SIZE - 100; i >= SIZE / 4; i--) {
            l = l.minus(mLists.mArray[i / 2]);
        }

        return l;
    }

    @Benchmark
    public Object TreePVector() {
        TreePVector<Integer> l = mLists.mTreePVector;
        for (int i = SIZE - 100; i >= SIZE / 4; i--) {
            l = l.minus(mLists.mArray[i / 2]);
        }

        return l;
    }

    @Benchmark
    public Object JImmutableList() {
        JImmutableList<Integer> l = mLists.mJImmutableList;
        for (int i = SIZE - 100; i >= SIZE / 4; i--) {
            l = l.delete(mLists.mArray[i / 2]);
        }

        return l;
    }
}
/**
 Benchmark                        Mode  Cnt            Score            Error  Units
 ListRemoveMiddle.JImmutableList  avgt    3     19354452.062 ±    3690789.594  ns/op
 ListRemoveMiddle.Stack           avgt    3   4442481048.667 ±  581796504.858  ns/op
 ListRemoveMiddle.ArrayList       avgt    3   4802332800.333 ± 4234273501.413  ns/op
 ListRemoveMiddle.LinkedList      avgt    3  10669939267.000 ± 1040284126.110  ns/op
 ListRemoveMiddle.TreePVector     avgt    3  71387068447.667 ± 1001083199.505  ns/op
*/