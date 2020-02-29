import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Elijah Peterson
 * @version 1.0
 * @userid epeterson42
 * @GTID 903405747
 * I worked on this assignment with Kia Safai

*/

public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[this.INITIAL_CAPACITY];

    }

    private void downHeap() {
        for (int i = (this.size/2); i >= 1; i--) {
            try {
                T right = backingArray[i * 2 + 1];
                T left = backingArray[i * 2];
                if ((this.backingArray[i].compareTo(left) < 0) &&
                        (this.backingArray[i].compareTo(right) < 0)) {
                    continue;
                }
                if ((this.backingArray[i].compareTo(left) > 0) ||
                        (this.backingArray[i].compareTo(right) > 0)) {
                    T max = this.backingArray[i];
                    if (right.compareTo(left) > 0) {
                        T min = left;
                        this.backingArray[i * 2] = max;
                        this.backingArray[(i)] = min;
                    } else {
                        T min = right;
                        this.backingArray[(i * 2) + 1] = max;
                        this.backingArray[(i)] = min;
                    }
                }
                if (i * 4 <= size) {
                    i = (i * 4) - 1;
                }
            } catch (IndexOutOfBoundsException | NullPointerException e) {
                if (this.backingArray[i * 2] == null || backingArray[i].compareTo(this.backingArray[i * 2]) < 0) {
                    continue;
                }
                if (this.backingArray[i].compareTo(this.backingArray[i * 2]) > 0) {
                    T max = this.backingArray[i];
                    T min = this.backingArray[i * 2];
                    this.backingArray[i * 2] = max;
                    this.backingArray[(i)] = min;
                }
            }
        }
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Heap cannot contain null data");
        }
        backingArray = (T[]) new Comparable[(2 * data.size()) + 1];
        size = data.size();
        for (int i = 1; i <= data.size(); i++) {
            backingArray[i] = data.get(i - 1);
        }
        this.downHeap();

    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add data that is null");
        }
        if (size == backingArray.length - 1) {
            int oldlength = backingArray.length;
            T[] arr = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < backingArray.length; i++) {
                arr[i] = backingArray[i];
            }
            backingArray = arr;
            backingArray[oldlength] = data;
            size += 1;
            this.downHeap();
        } else {
            backingArray[size + 1] = data;
            size += 1;
            this.downHeap();
        }

    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in the heap");
        }
        T temp = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size -= 1;
        if (size > 1) {
            downHeap();
        }
        return temp;

    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in the heap");
        }
        return backingArray[1];

    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];

    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
