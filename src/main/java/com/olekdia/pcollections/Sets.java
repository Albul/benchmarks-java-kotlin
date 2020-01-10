package com.olekdia.pcollections;

import androidx.collection.ArraySet;
import org.javimmutable.collections.JImmutableMultiset;
import org.javimmutable.collections.JImmutableSet;
import org.javimmutable.collections.hash.JImmutableHashSet;
import org.javimmutable.collections.tree.JImmutableTreeSet;
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
    public final JImmutableHashSet<Integer> mJImmutableHashSet;
    public final JImmutableTreeSet<Integer> mJImmutableTreeSet;

    public Sets(final int size) {
        mArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            mArray[i] = Integer.valueOf(i);
        }

        mHashSet = CollectionHelper.add(new HashSet(size), mArray);
        mTreeSet = CollectionHelper.add(new TreeSet(), mArray);
        mArraySet = CollectionHelper.add(new ArraySet<>(size), mArray);
        mMapPSet = (MapPSet) CollectionHelper.plus(HashTreePSet.empty(), mArray);
        mJImmutableHashSet = (JImmutableHashSet) CollectionHelper.add(JImmutables.set(), mArray);
        mJImmutableTreeSet = (JImmutableTreeSet) CollectionHelper.add(JImmutables.sortedSet(), mArray);
    }
}
