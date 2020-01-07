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
public class ListSetIterate {

    public static final int SIZE = 200_000;

    volatile Integer[] mValues = null;

    volatile Stack<Integer> mStack = null;
    volatile LinkedList<Integer> mLinkedList = null;
    volatile ArrayList<Integer> mArrayList = null;
    volatile HashSet<Integer> mHashSet = null;
    volatile TreeSet<Integer> mTreeSet = null;
    volatile ArraySet<Integer> mArraySet = null;

    volatile ConsPStack<Integer> mConsPStack = null;
    volatile TreePVector<Integer> mTreePVector = null;
    volatile MapPSet<Integer> mMapPSet = null;
    volatile JImmutableList<Integer> mJImmutableList = null;
    volatile JImmutableStack<Integer> mJImmutableStack = null;
    volatile JImmutableSet<Integer> mJImmutableSet = null;
    volatile JImmutableMultiset<Integer> mJImmutableMultiset = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListSetIterate.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mValues = new Integer[SIZE];

        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            mValues[i] = Integer.valueOf(random.nextInt(SIZE));
        }

        mStack = CollectionHelper.collectionAdd(new Stack<>(), mValues);
        mLinkedList = CollectionHelper.collectionAdd(new LinkedList(), mValues);
        mArrayList = CollectionHelper.collectionAdd(new ArrayList(SIZE), mValues);
        mConsPStack = (ConsPStack) CollectionHelper.pCollectionPlus(ConsPStack.empty(), mValues);
        mTreePVector = (TreePVector) CollectionHelper.pCollectionPlus(TreePVector.empty(), mValues);
        mHashSet = CollectionHelper.collectionAdd(new HashSet(SIZE), mValues);
        mTreeSet = CollectionHelper.collectionAdd(new TreeSet(), mValues);
        mArraySet = CollectionHelper.collectionAdd(new ArraySet<>(SIZE), mValues);
        mMapPSet = (MapPSet) CollectionHelper.pCollectionPlus(HashTreePSet.empty(), mValues);
        mJImmutableList = (JImmutableList) CollectionHelper.jImmutableInsert(JImmutables.list(), mValues);
        mJImmutableStack = (JImmutableStack) CollectionHelper.jImmutableInsert(JImmutables.stack(), mValues);
        mJImmutableSet = (JImmutableSet) CollectionHelper.jImmutableInsert(JImmutables.set(), mValues);
        mJImmutableMultiset = (JImmutableMultiset) CollectionHelper.jImmutableInsert(JImmutables.multiset(), mValues);
    }

    @Benchmark
    public Object iterateArrayList() {
        return CollectionHelper.iterate(mArrayList);
    }

    @Benchmark
    public Object iterateStack() {
        return CollectionHelper.iterate(mStack);
    }

    @Benchmark
    public Object iterateLinkedList() {
        return CollectionHelper.iterate(mLinkedList);
    }

    @Benchmark
    public Object iterateConsPStack() {
        return CollectionHelper.iterate(mConsPStack);
    }

    @Benchmark
    public Object iterateTreePVector() {
        return CollectionHelper.iterate(mTreePVector);
    }

    @Benchmark
    public Object iterateHashSet() {
        return CollectionHelper.iterate(mHashSet);
    }

    @Benchmark
    public Object iterateTreeSet() {
        return CollectionHelper.iterate(mTreeSet);
    }

    @Benchmark
    public Object iterateArraySet() {
        return CollectionHelper.iterate(mArraySet);
    }

    @Benchmark
    public Object iterateMapPSet() {
        return CollectionHelper.iterate(mMapPSet);
    }

    @Benchmark
    public Object iterateJImmutableList() {
        return CollectionHelper.iterate(mJImmutableList);
    }

    @Benchmark
    public Object iterateJImmutableStack() {
        return CollectionHelper.iterate(mJImmutableStack);
    }

    @Benchmark
    public Object iterateJImmutableSet() {
        return CollectionHelper.iterate(mJImmutableSet);
    }

    @Benchmark
    public Object iterateJImmutableMultiset() {
        return CollectionHelper.iterate(mJImmutableMultiset);
    }
}
/**
 Benchmar                   Mode  Cnt         Score         Error  Units
 iterateArrayList           avgt    3    239669.432 ±   92467.573  ns/op
 iterateArraySet            avgt    3    444388.283 ±   75054.628  ns/op
 iterateJImmutableStack     avgt    3    717536.062 ±   74045.253  ns/op
 iterateConsPStack          avgt    3    819618.136 ±  137649.083  ns/op
 iterateLinkedList          avgt    3    853572.949 ±  844866.532  ns/op
 iterateHashSet             avgt    3   1984468.316 ± 2598625.356  ns/op
 iterateTreeSet             avgt    3   2321796.720 ± 2474454.047  ns/op
 iterateTreePVector         avgt    3   3996926.866 ±  160252.301  ns/op
 iterateJImmutableList      avgt    3   4090843.675 ± 7970449.729  ns/op
 iterateStack               avgt    3   5069004.426 ±  335499.537  ns/op
 iterateMapPSet             avgt    3  10379233.039 ± 1289215.156  ns/op
 iterateJImmutableMultiset  avgt    3  19757874.889 ± 1037905.190  ns/op
 iterateJImmutableSet       avgt    3  19788659.312 ± 1458216.162  ns/op
*/