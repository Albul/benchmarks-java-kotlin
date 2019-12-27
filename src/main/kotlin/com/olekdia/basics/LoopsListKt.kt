package com.olekdia.basics

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.RunnerException
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Benchmark                      Mode  Cnt   Score   Error  Units
    LoopsListKt.forEach            avgt    5  16.518 ± 0.908  ms/op
    LoopsListKt.forInIndexes       avgt    5  17.450 ± 0.343  ms/op
    LoopsListKt.forInItems         avgt    5  17.092 ± 0.223  ms/op
    LoopsListKt.forRange           avgt    5  17.629 ± 0.538  ms/op
    LoopsListKt.forRangeLocalList  avgt    5  17.976 ± 2.055  ms/op
    LoopsListKt.forRangeReversed   avgt    5  17.617 ± 0.166  ms/op
    LoopsListKt.forRangeUntil      avgt    5  17.947 ± 0.442  ms/op
    LoopsListKt.iterator           avgt    5  17.194 ± 0.291  ms/op
 */
const val SIZE = 10_000_000

@Throws(RunnerException::class)
fun main(args: Array<String>) {
    val opt = OptionsBuilder()
            .include(LoopsListKt::class.java.simpleName)
            .forks(1)
            .build()
    Runner(opt).run()
}

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
open class LoopsListKt {

    private val list: MutableList<Int> = Random().let { rand ->
        MutableList<Int>(SIZE) {
            rand.nextInt()
        }
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    fun iterator(): Int {
        var max = Int.MIN_VALUE
        val it: Iterator<Int> = list.iterator()
        while (it.hasNext()) {
            max = Integer.max(max, it.next())
        }
        return max
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    open fun forInItems(): Int {
        var max = Int.MIN_VALUE
        for (n in list) {
            max = Integer.max(max, n)
        }
        return max
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    open fun forInIndexes(): Int {
        var max = Int.MIN_VALUE
        for (i in list.indices) {
            max = Integer.max(max, list[i])
        }
        return max
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    open fun forRange(): Int {
        var max = Int.MIN_VALUE
        for (i in 0..list.lastIndex) {
            max = Integer.max(max, list[i])
        }
        return max
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    open fun forRangeLocalList(): Int {
        var max = Int.MIN_VALUE
        val list = list
        for (i in 0..list.lastIndex) {
            max = Integer.max(max, list[i])
        }
        return max
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    open fun forRangeUntil(): Int {
        var max = Int.MIN_VALUE
        for (i in 0 until list.size) {
            max = Integer.max(max, list[i])
        }
        return max
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    open fun forRangeReversed(): Int {
        var max = Int.MIN_VALUE
        for (i in list.lastIndex downTo 0) {
            max = Integer.max(max, list[i])
        }
        return max
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    @Benchmark
    open fun forEach(): Int {
        var max = Int.MIN_VALUE
        list.forEach {
            max = Integer.max(max, it)
        }

        return max
    }
}