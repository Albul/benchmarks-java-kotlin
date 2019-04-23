package com.olekdia;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 Benchmark                            Mode  Cnt   Score   Error  Units
 LoopsArray.reverseForLoop            avgt    5  11.350 ± 0.047  ms/op
 LoopsArray.forwardForLoopWithConst   avgt    5  11.345 ± 0.057  ms/op
 LoopsArray.forwardForLoopWithLength  avgt    5  12.729 ± 0.157  ms/op
 LoopsArray.forEachLoop               avgt    5  11.479 ± 0.362  ms/op
 LoopsArray.whileLoop                 avgt    5  11.356 ± 0.052  ms/op
 LoopsArray.lambda                    avgt    5  12.550 ± 2.880  ms/op
 LoopsArray.parallelStream            avgt    5   4.610 ± 0.171  ms/op
 LoopsArray.stream                    avgt    5  11.006 ± 0.579  ms/op
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5)
@Warmup(iterations = 5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class LoopsArray {

    public static final int SIZE = 10_000_000;

    volatile int[] mArr = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LoopsArray.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mArr = new int[SIZE];

        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            mArr[i] = random.nextInt(SIZE);
        }
    }

    @Benchmark
    public int forEachLoop() {
        int max = Integer.MIN_VALUE;
        for (int n : mArr) {
            max = Math.max(max, n);
        }
        return max;
    }

    @Benchmark
    public int forwardForLoopWithLength() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < mArr.length; i++) {
            max = Math.max(max, mArr[i]);
        }
        return max;
    }

    @Benchmark
    public int forwardForLoopWithConst() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < SIZE; i++) {
            max = Math.max(max, mArr[i]);
        }
        return max;
    }

    @Benchmark
    public int reverseForLoop() {
        int max = Integer.MIN_VALUE;
        for (int i = SIZE - 1; i >= 0; i--) {
            max = Math.max(max, mArr[i]);
        }
        return max;
    }

    @Benchmark
    public int whileLoop() {
        int max = Integer.MIN_VALUE;
        int i = 0;
        while (i < SIZE) {
            max = Math.max(max, mArr[i]);
            i++;
        }
        return max;
    }

    @Benchmark
    public int parallelStream() {
        OptionalInt max = Arrays.stream(mArr).parallel().reduce(Math::max);
        return max.getAsInt();
    }

    @Benchmark
    public int stream() {
        OptionalInt max = Arrays.stream(mArr).reduce(Math::max);
        return max.getAsInt();
    }

    @Benchmark
    public int lambda() {
        return Arrays.stream(mArr).reduce(Integer.MIN_VALUE, (a, b) -> Math.max(a, b));
    }
}