package com.olekdia.pcollections;

import androidx.collection.ArraySet;
import org.javimmutable.collections.JImmutableMultiset;
import org.javimmutable.collections.JImmutableSet;
import org.javimmutable.collections.util.JImmutables;
import org.pcollections.HashTreePSet;
import org.pcollections.MapPSet;

import java.util.HashSet;
import java.util.TreeSet;

public class Sets {
    public final Integer[] mArray;
    public final HashSet<Integer> mHashSet;
    public final TreeSet<Integer> mTreeSet;
    public final ArraySet<Integer> mArraySet;
    public final MapPSet<Integer> mMapPSet;
    public final JImmutableSet<Integer> mJImmutableSet;
    public final JImmutableMultiset<Integer> mJImmutableMultiset;

    public Sets(final int size) {
        mArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            mArray[i] = Integer.valueOf(i);
        }

        mHashSet = CollectionHelper.collectionAdd(new HashSet(size), mArray);
        mTreeSet = CollectionHelper.collectionAdd(new TreeSet(), mArray);
        mArraySet = CollectionHelper.collectionAdd(new ArraySet<>(size), mArray);
        mMapPSet = (MapPSet) CollectionHelper.pCollectionPlus(HashTreePSet.empty(), mArray);
        mJImmutableSet = (JImmutableSet) CollectionHelper.jImmutableInsert(JImmutables.set(), mArray);
        mJImmutableMultiset = (JImmutableMultiset) CollectionHelper.jImmutableInsert(JImmutables.multiset(), mArray);
    }
}
