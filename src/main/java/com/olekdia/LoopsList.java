package com.olekdia;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark                        Mode  Cnt  Score   Error  Units
 * Loops.forEachLambda              avgt    5  8.195 ± 5.520  ms/op
 * Loops.forEachLoop                avgt    5  2.188 ± 0.689  ms/op
 * Loops.forwardForLoopWithConst    avgt    5  2.651 ± 0.296  ms/op
 * Loops.reverseForLoop             avgt    5  2.616 ± 0.261  ms/op
 * Loops.iterator                   avgt    5  2.248 ± 0.587  ms/op
 * Loops.lambda                     avgt    5  7.821 ± 3.000  ms/op
 * Loops.parallelStream             avgt    5  5.455 ± 1.655  ms/op
 * Loops.stream                     avgt    5  8.143 ± 1.389  ms/op
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5)
@Warmup(iterations = 5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class LoopsList {

    public static final int SIZE = 10_000_000;

    volatile List<Integer> mList = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LoopsList.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mList = new ArrayList<Integer>(SIZE);
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            mList.add(random.nextInt(SIZE));
        }
    }

    @Benchmark
    public int iterator() {
        int max = Integer.MIN_VALUE;
        for (Iterator<Integer> it = mList.iterator(); it.hasNext(); ) {
            max = Integer.max(max, it.next());
        }
        return max;
    }

    @Benchmark
    public int forEachLoop() {
        int max = Integer.MIN_VALUE;
        for (Integer n : mList) {
            max = Integer.max(max, n);
        }
        return max;
    }

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

    @Benchmark
    public int forwardForLoopWithConst() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < SIZE; i++) {
            max = Integer.max(max, mList.get(i));
        }
        return max;
    }

    @Benchmark
    public int reverseForLoop() {
        int max = Integer.MIN_VALUE;
        for (int i = SIZE - 1; i >= 0; i--) {
            max = Integer.max(max, mList.get(i));
        }
        return max;
    }

    @Benchmark
    public int parallelStream() {
        Optional<Integer> max = mList.parallelStream().reduce(Integer::max);
        return max.get();
    }

    @Benchmark
    public int stream() {
        Optional<Integer> max = mList.stream().reduce(Integer::max);
        return max.get();
    }

    @Benchmark
    public int lambda() {
        return mList.stream().reduce(Integer.MIN_VALUE, (a, b) -> Integer.max(a, b));
    }
}