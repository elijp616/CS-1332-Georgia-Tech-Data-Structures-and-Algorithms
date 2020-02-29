/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Elijah Peterson
 * @version 1.0
 * @userid epeterson42
 * @GTID 903405747
 *
 * Collaborators: I worked on this homework with Kia Safai
 *
 * Resources: I only used this course's material
 */

import java.util.NoSuchElementException;

public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't insert null "
                    + "data in the structure.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The data you are trying "
                   + "to access is null");
        }
        DoublyLinkedListNode<T> newnode = new DoublyLinkedListNode<T>(data);
        if (size == 0) {
            head = newnode;
            tail = head;
            size += 1;
        } else if (index == 0) {
            addToFront(data);
        } else if (size == index) {
            addToBack(data);
        } else {
            DoublyLinkedListNode<T> pointer = head;
            for (int i = 0; i < index - 1; i++) {
                pointer = pointer.getNext();
            }
            newnode.setNext(pointer.getNext());
            newnode.getNext().setPrevious(newnode);
            pointer.setNext(newnode);
            newnode.setPrevious(pointer);
            size += 1;
        }



    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't insert null "
                   + "data in the structure.");
        }
        if (size == 0) {
            head = new DoublyLinkedListNode<T>(data);
            tail = head;
            size += 1;
        } else {
            DoublyLinkedListNode<T> newnode =
                    new DoublyLinkedListNode<T>(data);
            newnode.setNext(head);
            head.setPrevious(newnode);
            head = newnode;
            size += 1;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't insert null"
                   + " data in the structure.");
        }
        if (size == 0) {
            head = new DoublyLinkedListNode<>(data);
            tail = head;
            size += 1;
        } else {
            DoublyLinkedListNode<T> newnode =
                    new DoublyLinkedListNode<>(data);
            tail.setNext(newnode);
            newnode.setPrevious(tail);
            tail = newnode;
            size += 1;

        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The data you are "
                   + "trying to access is null");
        }
        if (index == 0) {
            return removeFromFront();
        }
        if (index == size - 1) {
            return removeFromBack();
        } else if (size / 2 >= index) {
            DoublyLinkedListNode<T> pointer = head;
            for (int i = 0; i < index - 1; i++) {
                pointer = pointer.getNext();
            }
            DoublyLinkedListNode<T> temp = pointer.getNext();
            pointer.setNext(null);
            size -= 1;
            return temp.getData();
        } else if (size / 2 < index) {
            DoublyLinkedListNode<T> pointer = tail;
            for (int i = size; i > index + 1; i--) {
                pointer = pointer.getPrevious();
            }
            DoublyLinkedListNode<T> temp = pointer.getPrevious();
            pointer.setPrevious(null);
            size -= 1;
            return temp.getData();
        }
        return null;

    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        DoublyLinkedListNode<T> temp = head;
        if (size == 0) {
            throw new NoSuchElementException("The list is empty, so there"
                    + " is nothing to get.");
        } else {
            if (head == tail) {
                head = tail;
                tail = null;
                return temp.getData();
            } else {
                head = head.getNext();
                head.setPrevious(null);
                size -= 1;
                return temp.getData();
            }
        }


    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty, "
                    + "so there is nothing to get.");
        }
        if (head == tail) {
            head.setNext(null);
            head.setPrevious(null);
            tail.setNext(null);
            tail.setPrevious(null);
            size -= 1;
            return tail.getData();
        } else {
            T temp = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size -= 1;
            return temp;

        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Cannot access data outside "
                   + "the size of the data structure(null)");
        } else {
            if (index == 0) {
                return head.getData();
            } else if (index == size - 1) {
                return tail.getData();
            }
            Object[] myarray = this.toArray();
            return (T) myarray[index];
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);

    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;

    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't insert null "
                    + "data in the structure.");
        }
        int indextoremove = 0;
        int count = 0;
        boolean check = false;
        if (tail.getData().equals(data)) {
            return removeFromBack();
        }
        DoublyLinkedListNode<T> pointer = head;
        while (pointer != null) {
            if (pointer.getData().equals(data)) {
                indextoremove = count;
                check = true;
            }
            pointer = pointer.getNext();
            count += 1;
        }
        if (check) {
            return removeAtIndex(indextoremove);
        } else {
            throw new NoSuchElementException("That piece of data is "
                   + "no where in the list.");
        }

    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] myarray = new Object[size];
        DoublyLinkedListNode<T> pointer = head;
        for (int i = 0; i < size; i++) {
            myarray[i] = pointer.getData();
            pointer = pointer.getNext();
        }
        return myarray;


    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
