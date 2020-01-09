package com.olekdia.pcollections;

import org.javimmutable.collections.JImmutableList;
import org.javimmutable.collections.JImmutableStack;
import org.javimmutable.collections.util.JImmutables;
import org.pcollections.ConsPStack;
import org.pcollections.TreePVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Lists {
    public final Integer[] mArray;
    public final Stack<Integer> mStack;
    public final LinkedList<Integer> mLinkedList;
    public final ArrayList<Integer> mArrayList;
    public final ConsPStack<Integer> mConsPStack;
    public final TreePVector<Integer> mTreePVector;
    public final JImmutableList<Integer> mJImmutableList;
    public final JImmutableStack<Integer> mJImmutableStack;

    public Lists(final int size) {
        mArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            mArray[i] = Integer.valueOf(i);
        }

        mStack = CollectionHelper.add(new Stack<>(), mArray);
        mLinkedList = CollectionHelper.add(new LinkedList(), mArray);
        mArrayList = CollectionHelper.add(new ArrayList(size), mArray);
        mConsPStack = (ConsPStack) CollectionHelper.plus(ConsPStack.empty(), mArray);
        mTreePVector = (TreePVector) CollectionHelper.plus(TreePVector.empty(), mArray);
        mJImmutableList = (JImmutableList) CollectionHelper.add(JImmutables.list(), mArray);
        mJImmutableStack = (JImmutableStack) CollectionHelper.add(JImmutables.stack(), mArray);
    }
}
