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
    public final HashMap<Object, Object> mHashMap;
    public final LinkedHashMap<Object, Object> mLinkedHashMap;
    public final TreeMap<Object, Object> mTreeMap;
    public final ArrayMap<Object, Object> mArrayMap;
    public final SparseArrayCompat<Object> mSparseArray;
    public final HashPMap<Object, Object> mHashPMap;
    public final IntTreePMap<Object> mIntTreePMap;
    public final JImmutableMap<Object, Object> mJImmutableMap;
    public final JImmutableMap<Object, Object> mJImmutableSortedMap;
    public final JImmutableListMap<Object, Object> mJImmutableListMap;
    public final JImmutableSetMap<Object, Object> mJImmutableSetMap;
    public final MutableMap<Object, Object> mEclipseMutableMap;
    public final ImmutableMap<Object, Object> mEclipseImmutableMap;
    public final com.google.common.collect.ImmutableMap<Object, Object> mGuavaImmutableMap;

    public Maps(final int size) {
        mArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            mArray[i] = Integer.valueOf(i);
        }

        mHashMap = CollectionHelper.put(new HashMap<Object, Object>(size), mArray);

        mLinkedHashMap = CollectionHelper.put(new LinkedHashMap<Object, Object>(size), mArray);
        mTreeMap = CollectionHelper.put(new TreeMap<Object, Object>(), mArray);
        mArrayMap = CollectionHelper.put(new ArrayMap<Object, Object>(), mArray);
        mSparseArray = CollectionHelper.put(new SparseArrayCompat<Object>(size), mArray);
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
