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
package com.olekdia.basics;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 Benchmark                               Mode  Cnt   Score    Error  Units
 LoopsList.forEachLambda                 avgt    5  69.213 ± 10.011  ms/op
 LoopsList.forEachLoop                   avgt    5  18.301 ±  0.069  ms/op
 LoopsList.forwardForLoopWithConst       avgt    5  18.376 ±  0.507  ms/op
 LoopsList.iterator                      avgt    5  17.933 ±  1.690  ms/op
 LoopsList.lambda                        avgt    5  55.534 ±  0.565  ms/op
 LoopsList.parallelStream                avgt    5  56.016 ± 10.742  ms/op
 LoopsList.reverseForLoop                avgt    5  18.008 ±  1.232  ms/op
 LoopsList.reverseForLoopWithFinalLocal  avgt    5  18.027 ±  1.375  ms/op
 LoopsList.stream                        avgt    5  61.308 ±  4.598  ms/op
 LoopsListKt.forEach                     avgt    5  17.392 ±  1.038  ms/op
 LoopsListKt.forInIndexes                avgt    5  17.806 ±  0.749  ms/op
 LoopsListKt.forInItems                  avgt    5  17.539 ±  0.656  ms/op
 LoopsListKt.forRange                    avgt    5  17.840 ±  0.509  ms/op
 LoopsListKt.forRangeLocalList           avgt    5  17.794 ±  0.398  ms/op
 LoopsListKt.forRangeReversed            avgt    5  18.323 ±  0.229  ms/op
 LoopsListKt.forRangeUntil               avgt    5  17.756 ±  0.244  ms/op
 LoopsListKt.iterator                    avgt    5  17.337 ±  0.649  ms/op
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5)
@Warmup(iterations = 2)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class LoopsList {

    public static final int SIZE = 10_000_000;

    final List<Integer> mList = new ArrayList<>(SIZE);

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LoopsList.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        final Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            mList.add(random.nextInt(SIZE));
        }
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    public int iterator() {
        int max = Integer.MIN_VALUE;
        for (Iterator<Integer> it = mList.iterator(); it.hasNext(); ) {
            max = Integer.max(max, it.next());
        }
        return max;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    public int forEachLoop() {
        int max = Integer.MIN_VALUE;
        for (Integer n : mList) {
            max = Integer.max(max, n);
        }
        return max;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    public int forEachLambda() {
        final Wrapper wrapper = new Wrapper();
        wrapper.inner = Integer.MIN_VALUE;

        mList.forEach(i -> helper(i, wrapper));
        return wrapper.inner.intValue();
    }

    public static class Wrapper {
        public Integer inner;
    }

    private int helper(int i, Wrapper wrapper) {
        wrapper.inner = Math.max(i, wrapper.inner);
        return wrapper.inner;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    public int forwardForLoopWithConst() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < SIZE; i++) {
            max = Integer.max(max, mList.get(i));
        }
        return max;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    public int reverseForLoopWithFinalLocal() {
        int max = Integer.MIN_VALUE;
        final List<Integer> list = mList;
        for (int i = SIZE - 1; i >= 0; i--) {
            max = Integer.max(max, list.get(i));
        }
        return max;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    public int reverseForLoop() {
        int max = Integer.MIN_VALUE;
        for (int i = SIZE - 1; i >= 0; i--) {
            max = Integer.max(max, mList.get(i));
        }
        return max;
    }


    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    public int parallelStream() {
        Optional<Integer> max = mList.parallelStream().reduce(Integer::max);
        return max.get();
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    public int stream() {
        Optional<Integer> max = mList.stream().reduce(Integer::max);
        return max.get();
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    public int lambda() {
        return mList.stream().reduce(Integer.MIN_VALUE, (a, b) -> Integer.max(a, b));
    }
}