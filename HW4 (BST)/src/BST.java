import java.util.*;
/**
 * Your implementation of a BST.
 *
 * @author Elijah Peterson
 * @version 1.0
 * @userid epeterson42
 * @GTID 903405747
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot create with null data");
        }
        Iterator<T> itr = data.iterator();
        while (itr.hasNext()) {
            T temp = itr.next();
            this.add(temp);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        root = addhelper(root, data);
    }

    /**
     *
     * @param curr the current node
     * @param data the data to add
     * @return the node
     */
    private BSTNode addhelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            size += 1;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addhelper(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addhelper(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its
     * child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("The data wasn't there");
        }
        return rRemove(data, null, root);
    }

    /**
     *
     * @param data the data to remove
     * @param parent the current parent
     * @param dummy a dummy node
     * @return the stored data of the node to be removed
     */

    private T rRemove(T data, BSTNode<T> parent, BSTNode<T> dummy) {
        T temp;
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        if (data.equals(dummy.getData())) {
            temp = dummy.getData();
            if (dummy.getRight() == null && dummy.getLeft() == null) {
                if (parent != null) {
                    if (data.equals(parent.getRight().getData())) {
                        parent.setRight(null);
                    } else {
                        parent.setLeft(null);
                    }
                } else {
                    root = null;
                }
                size -= 1;
                return temp;
            } else if (dummy.getRight() != null && dummy.getLeft() != null) {
                BSTNode<T> pred = findPred(dummy.getLeft());
                BSTNode<T> preparent = findPreParent(dummy);
                T store = pred.getData();
                rRemove(pred.getData(), preparent, pred);
                dummy.setData(store);
                return temp;

            } else if (dummy.getRight() != null) {
                temp = dummy.getData();
                if (parent != null) {
                    if (parent.getLeft() != null
                            &&
                            data.equals(parent.getLeft().getData())) {
                        parent.setLeft(dummy.getLeft());
                    } else {
                        parent.setRight(dummy.getRight());
                    }
                } else {
                    root = dummy.getLeft();
                }
                size -= 1;
                return temp;
            } else if (dummy.getLeft() != null) {
                temp = dummy.getData();
                if (parent != null) {
                    if (parent.getLeft() != null
                            &&
                            data.equals(parent.getLeft().getData())) {
                        parent.setLeft(dummy.getRight());
                    } else {
                        parent.setRight(dummy.getRight());
                    }
                } else {
                    root = dummy.getRight();
                }
                size -= 1;
                return temp;
            }
        }
        if (data.compareTo(dummy.getData()) < 0) {
            if (dummy.getLeft() == null) {
                throw new NoSuchElementException("Data wasn't there");
            }
            return rRemove(data, dummy, dummy.getLeft());
        } else {
            if (dummy.getRight() == null) {
                throw new NoSuchElementException("Data wasn't there");
            }
            return rRemove(data, dummy, dummy.getRight());
        }

    }

    /**
     *
     * @param curr the current node
     * @return BSTNode the pred node
     */

    private BSTNode<T> findPred(BSTNode<T> curr) {
        if (curr.getRight() != null) {
            curr = findPred(curr.getRight());
        }
        return curr;
    }

    /**
     *
     * @param parent a BSTNode of the current parent
     * @return return the pred parent
     */
    private BSTNode<T> findPreParent(BSTNode<T> parent) {
        if (parent.getLeft().getRight() == null) {
            return parent;
        } else {
            parent = parent.getLeft();
            while (parent.getRight().getRight() != null) {
                parent = parent.getRight();
            }
        }
        return parent;
    }




    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't retrieve null data");
        }
        T result = getHelper(root, data);
        if (result == null) {
            throw new NoSuchElementException("Data can't be found in tree");
        }
        return result;
    }

    /**
     *
     * @param curr the current node to be used
     * @param data the data to look for
     * @return the desired node's data
     */

    private T getHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelper(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(curr.getRight(), data);
        } else {
            return curr.getData();
        }



    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return (getHelper(root, data) != null);

    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorderHelper(root, list);
        return list;
    }

    /**
     *
     * @param curr the current node to be used in search
     * @param list a list to be added of the traversal
     */

    private void preorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            list.add(curr.getData());
            preorderHelper(curr.getLeft(), list);
            preorderHelper(curr.getRight(), list);
        }

    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    /**
     *
     * @param curr the current node to be used in search
     * @param list a list to be added of the traversal
     */

    private void inorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            inorderHelper(curr.getLeft(), list);
            list.add(curr.getData());
            inorderHelper(curr.getRight(), list);
        }

    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;
    }

    /**
     *
     * @param curr the current node
     * @param list a list to be added of the traversal
     */

    private void postorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            postorderHelper(curr.getLeft(), list);
            postorderHelper(curr.getRight(), list);
            list.add(curr.getData());
        }

    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode> q = new LinkedList<BSTNode>();
        q.add(root);
        ArrayList<T> arr = new ArrayList<>();
        if (root == null) {
            return arr;
        }
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.poll();
            arr.add(curr.getData());
            if (curr != null) {
                if (curr.getLeft() != null) {
                    q.add(curr.getLeft());
                }
                if (curr.getRight() != null) {
                    q.add(curr.getRight());
                }
            }
        }
        return arr;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return heightHelper(root);
    }

    /**
     *
     * @param curr the current node
     * @return the height
     */


    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int leftside = heightHelper(curr.getLeft());
            int rightside = heightHelper(curr.getRight());
            if (leftside > rightside) {
                return leftside + 1;
            } else {
                return rightside + 1;
            }
        }

    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;


    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     **
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *                 50
     *             /        \
     *           25         75
     *         /    \
     *        12    37
     *       /  \    \
     *     10   15   40
     *         /
     *       13
     * findPathBetween(13, 40) should return the list [13, 15, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Cannot path null data");
        }
        return null;
    private List<T> getPath(T data) {
        if (root == null) {
            return null;
        }
        arr.add(root.getData());
        if (root.getData() == data) {
            return true;
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
