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
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5)
@Warmup(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
public class FinalVars {

    @State(Scope.Thread)
    public static class MyState {
        public int a = 1;
        public int b = 35;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(FinalVars.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public int finalLocal(MyState state, Blackhole blackhole) {
        final int a = state.a;
        blackhole.consume(a);
        final int b = state.b;
        blackhole.consume(b);
        return a * b + b * b + a;
    }

    @Benchmark // better by 10000 ops /ms
    public int nonFinalLocal(MyState state, Blackhole blackhole) {
        int a = state.a;
        blackhole.consume(a);
        int b = state.b;
        blackhole.consume(b);
        return a * b + b * b + a;
    }

    @Benchmark // better
    public int finalParam(final MyState state) {
        return state.a + state.b;
    }

    @Benchmark
    public int nonFinalParam(MyState state) {
        return state.a + state.b;
    }
}