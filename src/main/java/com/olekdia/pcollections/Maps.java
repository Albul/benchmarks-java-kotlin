package com.olekdia.pcollections;

import androidx.collection.ArrayMap;
import androidx.collection.SparseArrayCompat;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.javimmutable.collections.JImmutableListMap;
import org.javimmutable.collections.JImmutableMap;
import org.javimmutable.collections.JImmutableSetMap;
import org.javimmutable.collections.util.JImmutables;
import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;
import org.pcollections.IntTreePMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class Maps {

    public final Integer[] mArray;
    public final HashMap<Object, Integer> mHashMap;
    public final LinkedHashMap<Object, Integer> mLinkedHashMap;
    public final TreeMap<Object, Integer> mTreeMap;
    public final ArrayMap<Object, Integer> mArrayMap;
    public final SparseArrayCompat<Integer> mSparseArray;
    public final HashPMap<Object, Integer> mHashPMap;
    public final IntTreePMap<Integer> mIntTreePMap;
    public final JImmutableMap<Object, Integer> mJImmutableMap;
    public final JImmutableMap<Object, Integer> mJImmutableSortedMap;
    public final JImmutableListMap<Object, Integer> mJImmutableListMap;
    public final JImmutableSetMap<Object, Integer> mJImmutableSetMap;
    public final MutableMap<Object, Integer> mEclipseMutableMap;
    public final ImmutableMap<Object, Integer> mEclipseImmutableMap;
    public final com.google.common.collect.ImmutableMap<Object, Integer> mGuavaImmutableMap;

    public Maps(final int size) {
        mArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            mArray[i] = Integer.valueOf(i);
        }

        mHashMap = CollectionHelper.put(new HashMap<>(size), mArray);

        mLinkedHashMap = CollectionHelper.put(new LinkedHashMap<>(size), mArray);
        mTreeMap = CollectionHelper.put(new TreeMap<>(), mArray);
        mArrayMap = CollectionHelper.put(new ArrayMap<>(), mArray);
        mSparseArray = CollectionHelper.put(new SparseArrayCompat<>(size), mArray);
        mHashPMap = (HashPMap) CollectionHelper.plus(HashTreePMap.empty(), mArray);
        mIntTreePMap = (IntTreePMap) CollectionHelper.plus(IntTreePMap.empty(), mArray);
        mJImmutableMap = CollectionHelper.put(JImmutables.map(), mArray);
        mJImmutableSortedMap = CollectionHelper.put(JImmutables.sortedMap(), mArray);
        mJImmutableListMap = CollectionHelper.put(JImmutables.listMap(), mArray);
        mJImmutableSetMap = CollectionHelper.put(JImmutables.setMap(), mArray);
        mEclipseMutableMap = (MutableMap) CollectionHelper.put(new UnifiedMap<>(), mArray);
        mEclipseImmutableMap = mEclipseMutableMap.toImmutable();
        mGuavaImmutableMap = new com.google.common.collect.ImmutableMap.Builder().putAll(mHashMap).build();
    }
}
