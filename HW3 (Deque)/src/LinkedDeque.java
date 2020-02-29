import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
 *
 * @author Elijah Peterson
 * @version 1.0
 * @userid epeterson42
 * @GTID 903405747
 *
 * Collaborators: I worked on this homework alone
 *
 * Resources: I used only this course's materials
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't insert null "
                    + "data in the structure.");
        }
        if (size == 0) {
            head = new LinkedNode<>(data);
            tail = head;
            size += 1;
        } else {
            LinkedNode<T> newnode =
                    new LinkedNode<>(data);
            newnode.setNext(head);
            head.setPrevious(newnode);
            head = newnode;
            size += 1;
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't insert null"
                    + " data in the structure.");
        }
        if (size == 0) {
            head = new LinkedNode<>(data);
            tail = head;
            size += 1;
        } else {
            LinkedNode<T> newnode =
                    new LinkedNode<>(data);
            tail.setNext(newnode);
            newnode.setPrevious(tail);
            tail = newnode;
            size += 1;

        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        LinkedNode<T> temp = head;
        if (size == 0) {
            throw new NoSuchElementException("The list is empty, so there"
                    + " is nothing to get.");
        } else {
            if (size == 1) {
                T temp2 = head.getData();
                tail = null;
                head = null;
                size -= 1;
                return temp2;
            } else {
                head = head.getNext();
                head.setPrevious(null);
                size -= 1;
                return temp.getData();
            }
        }
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty, "
                    + "so there is nothing to get.");
        }
        if (size == 1) {
            T data = head.getData();
            head = null;
            tail = null;
            size -= 1;
            return data;
        } else if (size == 2) {
            T temp = tail.getData();
            tail = head;
            tail.setNext(null);
            tail.setPrevious(null);
            size -= 1;
            return temp;

        } else {
            T temp = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size -= 1;
            return temp;



        }
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing in "
                   + "the deque to retrieve");
        }
        if (size == 1) {
            head = tail;
        }
        return head.getData();

    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("There is nothing "
                    + "in the deque to retrieve");
        }
        if (size == 1) {
            head = tail;
        }

        return tail.getData();

    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
