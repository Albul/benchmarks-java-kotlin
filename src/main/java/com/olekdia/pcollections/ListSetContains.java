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
public class ListSetContains {

    public static final int SIZE = 100_000;

    volatile Object[] mValues = null;
    volatile Object mSearchedObject = null;

    volatile Stack<Object> mStack = null;
    volatile LinkedList<Object> mLinkedList = null;
    volatile ArrayList<Object> mArrayList = null;
    volatile HashSet<Object> mHashSet = null;
    volatile TreeSet<Object> mTreeSet = null;
    volatile ArraySet<Object> mArraySet = null;

    volatile ConsPStack<Object> mConsPStack = null;
    volatile TreePVector<Object> mTreePVector = null;
    volatile MapPSet<Object> mMapPSet = null;
    volatile JImmutableList<Object> mJImmutableList = null;
    volatile JImmutableStack<Object> mJImmutableStack = null;
    volatile JImmutableSet<Object> mJImmutableSet = null;
    volatile JImmutableMultiset<Object> mJImmutableMultiset = null;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListSetContains.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        mValues = new Object[SIZE];

        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            mValues[i] = Integer.valueOf(random.nextInt(SIZE));
        }

        mSearchedObject = mValues[88888];

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
    public Object containsArrayList() {
        return mArrayList.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsStack() {
        return mStack.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsLinkedList() {
        return mLinkedList.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsConsPStack() {
        return mConsPStack.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsTreePVector() {
        return mTreePVector.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsHashSet() {
        return mHashSet.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsTreeSet() {
        return mTreeSet.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsArraySet() {
        return mArraySet.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsMapPSet() {
        return mMapPSet.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsJImmutableList() {
        final Object searchedObj = mSearchedObject;

        for (Object obj : mJImmutableList) {
            if (obj.equals(searchedObj)) {
                return true;
            }
        }
        return false;
    }

    @Benchmark
    public Object containsJImmutableStack() {
        final Object searchedObj = mSearchedObject;

        for (Object obj : mJImmutableStack) {
            if (obj.equals(searchedObj)) {
                return true;
            }
        }
        return false;
    }

    @Benchmark
    public Object containsJImmutableSet() {
        return mJImmutableSet.contains(mSearchedObject);
    }

    @Benchmark
    public Object containsJImmutableMultiset() {
        return mJImmutableMultiset.contains(mSearchedObject);
    }
}
/**
 Benchmark                   Mode  Cnt        Score        Error  Units
 containsHashSet             avgt    3        9.295 ±      0.564  ns/op
 containsTreeSet             avgt    3       23.523 ±      1.752  ns/op
 containsJImmutableSet       avgt    3       27.011 ±      8.597  ns/op
 containsArraySet            avgt    3       27.352 ±      2.567  ns/op
 containsJImmutableMultiset  avgt    3       28.332 ±      1.514  ns/op
 containsMapPSet             avgt    3       54.518 ±      8.765  ns/op
 containsArrayList           avgt    3    21330.006 ±   4085.652  ns/op
 containsConsPStack          avgt    3    26286.167 ±    126.947  ns/op
 containsJImmutableStack     avgt    3    32304.469 ±  42974.235  ns/op
 containsStack               avgt    3    85272.092 ±  33635.596  ns/op
 containsJImmutableList      avgt    3   882873.080 ± 838760.879  ns/op
 containsLinkedList          avgt    3   160295.575 ±  12800.602  ns/op
 containsTreePVector         avgt    3  2061153.413 ± 394914.038  ns/op
*/