import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.NoSuchElementException;






/**
 * Your implementation of a LinearProbingHashMap.
 *
 * @author Elijah Peterson
 * @version 1.0
 * @userid epeterson42
 * @GTID 903405747
 *
 * Collaborators: I worked on this homework with Kia Safai
 *
 * Resources: None
 */
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;

    }

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[initialCapacity];
        size = 0;

    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null");
        }
        if ((double) (size + 1) / (double) (table.length) > MAX_LOAD_FACTOR) {
            resizeBackingTable((table.length * 2) + 1);
        }
        V temp;

        LinearProbingMapEntry<K, V> add =
                new LinearProbingMapEntry<>(key, value);
        int hashnum = Math.abs(key.hashCode() % table.length);
        if (table[hashnum] == null) {
            table[hashnum] = new LinearProbingMapEntry<>(key, value);
        } else {
            Integer del = null;
            LinearProbingMapEntry<K, V> curr = table[hashnum];
            // try block to catch nullpointer exception
            while (curr != null) {
                if (curr == null) {
                    continue;
                }
                curr = table[hashnum];
                try {
                    if (curr.getKey().equals(key)) {
                        temp = curr.getValue();
                        table[hashnum] = add;
                        return temp;
                    }
                    if (curr.isRemoved() && del == null) {
                        del = hashnum;
                    }
                    if (hashnum == table.length - 1) {
                        hashnum = 0;
                    } else {
                        hashnum += 1;
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
            if (del == null) {
                table[hashnum] = add;
            } else {
                table[del] = add;
            }
        }
        size++;
        return null;

    }


    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null data");
        }
        if (!this.containsKey(key)) {
            throw new NoSuchElementException("Key not there");
        }
        int hashnum = Math.abs(key.hashCode() % this.table.length);
        //make sure data isn't null
        while (this.table[hashnum] != null

                && !(table[hashnum].getKey().equals(key))) {
            if (this.table[hashnum] == null) {
                continue;
            }
            hashnum = (hashnum + 1) % table.length;
        }
        V temp = table[hashnum].getValue();
        table[hashnum].setRemoved(true);
        size -= 1;
        return temp;


    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key data can't be null");
        }
        int hashnum = Math.abs(key.hashCode() % this.table.length);

        int check = 0;
        //make sure data isn't null
        while (!(this.table[hashnum] == null)

                && !(table[hashnum].getKey().equals(key))

                && check <= table.length) {
            if (table[hashnum] == null) {
                continue;
            }
            hashnum = Math.abs((hashnum + 1) % table.length);
            check += 1;
        }
        if (table[hashnum] == null || table[hashnum].isRemoved()) {

            throw new NoSuchElementException("key data cannot be null");
        }
        return table[hashnum].getValue();

    }


    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        //try block to make sure data is there
        try {
            get(key);
            return true;

        } catch (NoSuchElementException nse) {

            return false;
        }


    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> result = new HashSet<>();
        if (size == 0) {
            return result;
        }
        for (int i = 0; i < this.table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            if (table[i] != null && !(table[i].isRemoved())) {
                result.add(table[i].getKey());
            }
        }
        return result;

    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        LinkedList<V> result = new LinkedList<V>();
        // linked list of values, don't have to worry about size
        for (int i = 0; i < this.table.length; i++) {
            if ((table[i] != null) && !(table[i].isRemoved())) {
                if (table[i] == null) {
                    continue;
                }
                result.add(table[i].getValue());
            }
        }
        return result;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     * number of items in the hash map
     */
    public void resizeBackingTable(int length) {
        int size2 = size;

        if (length <= 0 || length < size) {

            throw new IllegalArgumentException("Can't resize "
                    + "because of dimensions");
        }
        LinearProbingMapEntry<K, V> temp;

        size = 0;

        LinearProbingMapEntry<K, V>[] temptable = table;
        // new temp table
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[length];

        for (LinearProbingMapEntry<K, V> item: temptable) {
            size = 0;
            if (item != null) {
                if (item == null) {
                    continue;
                }
                put(item.getKey(), item.getValue());
                temp = item;
                int hashnum =
                        Math.abs(temp.getKey().hashCode() % this.table.length);
                try {
                    while (temptable[(hashnum + 1) % table.length] != null) {
                        hashnum = (hashnum + 1) % table.length;
                        put(temp.getKey(), temp.getValue());
                        temp = temptable[hashnum];
                    }
                } catch (ArrayIndexOutOfBoundsException aie) {
                    continue;
                }
                put(temp.getKey(), temp.getValue());
            }
        }
        size = size2;

    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[INITIAL_CAPACITY];

    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
