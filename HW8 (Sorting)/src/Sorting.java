import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Elijah Peterson
 * @version 1.0
 * @userid epeterson42
 * @GTID 903405747
 *
 * Collaborators: Kia Safai
 *
 * Resources: None
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array "
                    + "or comp can't be null");
        }
        for (int i = arr.length - 1; i >= 1; i--) {
            int max = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[max]) > 0) {
                    max = j;
                }
            }
            T swap = arr[i];
            // arr contains generic type

            arr[i] = arr[max];

            arr[max] = swap;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array"
                    + " or comp can't be null");
        }
        for (int i = 1; i < arr.length; i++) {
            int tracker = i;
            // nested loop O(n^2)

            while (tracker != 0 && (comparator.compare(arr[tracker - 1],
                    arr[tracker]) > 0)) {

                T swap = arr[tracker - 1];
                arr[tracker - 1] = arr[tracker];
                arr[tracker] = swap;
                tracker -= 1;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array "
                    + "or comp can't be null");
        }
        int end;
        boolean swap;
        int start;
        int tracker;

        swap = true;
        start = 0;
        end = arr.length - 1;
        tracker = 0;

        while (swap && (start < end)) {
            swap = false;

            for (int i = start; i < end; i++) {

                if (comparator.compare(arr[i], arr[i + 1]) > 0) {

                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swap = true;
                    tracker = i;
                }
            }
            end = tracker;
            if (swap) {
                swap = false;

                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {

                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        swap = true;
                        tracker = i;
                    }
                }
            }
            start = tracker;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Can't pass "
                    + "null data");
        }
        int middle;
        int leftSize;
        int righSize;
        T[] left;
        T[] right;
        if (arr.length > 1) {

            middle = (arr.length / 2);
            leftSize = (arr.length / 2);
            righSize = arr.length - leftSize;
            left = (T[]) new Object[leftSize];
            right = (T[]) new Object[righSize];

            for (int i = 0; i < leftSize; i++) {
                left[i] = arr[i];
            }
            for (int i = leftSize; i < arr.length; i++) {
                right[i - leftSize] = arr[i];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            int leftindex = 0;
            int rightindex = 0;
            int currindex = 0;
            while (leftindex < middle
                    && rightindex < arr.length - middle) {
                if (comparator.compare(left[leftindex],
                        right[rightindex]) <= 0) {
                    arr[currindex] = left[leftindex];
                    leftindex++;
                } else {
                    arr[currindex] = right[rightindex];
                    rightindex++;
                }
                currindex++;
            }
            while (leftindex < middle) {
                arr[currindex] = left[leftindex];
                leftindex += 1;
                currindex += 1;
            }
            while (rightindex < arr.length - middle) {
                arr[currindex] = right[rightindex];
                rightindex += 1;
                currindex += 1;
            }
        }
    }



    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || rand == null || comparator == null) {
            throw new IllegalArgumentException("Can't pass null data");
        }
        int right;
        right = arr.length - 1;
        int left;
        left = 0;
        quickH(arr, right, left, comparator, rand);
    }

    /**
     * Quicksort helper method
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param right right num
     * @param left left num
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */

    private static <T> void quickH(T[] arr, int right, int left,
                                        Comparator<T> comparator, Random rand) {

        if (left >= right) {
            return;
        }
        int leftIndex;
        int rightIndex;
        int pivot;
        T temp;
        leftIndex = left + 1;
        rightIndex = right;
        pivot = rand.nextInt(right - left) + left;

        temp = arr[pivot];
        arr[pivot] = arr[left];
        arr[left] = temp;
        while (leftIndex <= rightIndex) {
            while (leftIndex <= rightIndex
                    && comparator.compare(arr[leftIndex], temp) <= 0) {
                leftIndex += 1;
            }
            while (leftIndex <= rightIndex
                    && comparator.compare(arr[rightIndex], temp) >= 0) {
                rightIndex -= 1;
            }
            if (leftIndex <= rightIndex) {
                T toSwap = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = toSwap;
                leftIndex += 1;
                rightIndex -= 1;
            }
        }
        T swapper = arr[rightIndex];
        arr[rightIndex] = arr[left];
        arr[left] = swapper;
        quickH(arr, rightIndex - 1, left, comparator, rand);
        quickH(arr, right, rightIndex + 1, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Can't pass null data");
        }
        int maxData;
        maxData = Integer.MIN_VALUE;
        //MUST CHECK FOR THE LOWEST VALUE
        int length;
        length = 1;

        for (int i = 0; i < arr.length; i++) {

            if (Math.abs(arr[i]) > maxData) {
                maxData = Math.abs(arr[i]);
            }
        }
        while ((maxData) >= 10) {

            length += 1;
            maxData /= 10;
        }
        List<Integer>[] buckets = new ArrayList[19];
        // Account for negative numbers
        for (int i = 0; i < 19; i++) {
            buckets[i] = new ArrayList<>();
        }
        int divisor = 1;
        for (int i = 0; i < length; i++) {

            for (Integer num: arr) {
                buckets[((num / divisor) % 10) + 9].add(num);
            }
            int index = 0;
            for (int k = 0; k < buckets.length; k++) {

                for (Integer j: buckets[k]) {
                    arr[index++] = j;
                }
                buckets[k].clear();
            }
            divisor *= 10;
        }
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}
