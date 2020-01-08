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
public class ListIterate {

    public static final int SIZE = 200_000;

    volatile Lists mLists = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListIterate.class.getSimpleName())
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
        return CollectionHelper.iterate(mLists.mArrayList);
    }

    @Benchmark
    public Object Stack() {
        return CollectionHelper.iterate(mLists.mStack);
    }

    @Benchmark
    public Object LinkedList() {
        return CollectionHelper.iterate(mLists.mLinkedList);
    }

    @Benchmark
    public Object ConsPStack() {
        return CollectionHelper.iterate(mLists.mConsPStack);
    }

    @Benchmark
    public Object TreePVector() {
        return CollectionHelper.iterate(mLists.mTreePVector);
    }

    @Benchmark
    public Object JImmutableList() {
        return CollectionHelper.iterate(mLists.mJImmutableList);
    }

    @Benchmark
    public Object JImmutableStack() {
        return CollectionHelper.iterate(mLists.mJImmutableStack);
    }
}
/**
 Benchmar            Mode  Cnt         Score         Error  Units
 ArrayList           avgt    3    239669.432 ±   92467.573  ns/op
 JImmutableStack     avgt    3    717536.062 ±   74045.253  ns/op
 ConsPStack          avgt    3    819618.136 ±  137649.083  ns/op
 LinkedList          avgt    3    853572.949 ±  844866.532  ns/op
 TreePVector         avgt    3   3996926.866 ±  160252.301  ns/op
 JImmutableList      avgt    3   4090843.675 ± 7970449.729  ns/op
 Stack               avgt    3   5069004.426 ±  335499.537  ns/op
*/