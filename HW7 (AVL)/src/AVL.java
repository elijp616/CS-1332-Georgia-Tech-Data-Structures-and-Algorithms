import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Elijah Peterson
 * @version 1.0
 * @userid YOUR USER ID HERE (epeterson42)
 * @GTID YOUR GT ID HERE (903405747)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new
                    IllegalArgumentException("Data can't"
                    + "be null in AVL");
        }
        for(T piece : data) {
            if (piece == null) {
                throw new
                        IllegalArgumentException("Data can't"
                        + "be null in AVL");
            }
            this.add(piece);
        }

    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        AVLNode<T> node = new AVLNode<>(data);

        if (contains(data)) {
            return;
        }

        if (this.size == 0) {
            root = node;
            size++;
            updateHBF(root);
        } else {
            addH(root, node);
            size++;
        }

    }

    private void addH(AVLNode<T> root, AVLNode<T> node) {
        if (root == null) {
            return;
        }

        T rootData = root.getData();
        T nodeData = node.getData();

        //Root is less than node to add, so added to right
        if (rootData.compareTo(nodeData) < 0) {
            if (root.getRight() == null) {
                root.setRight(node);
            } else {
                addH(root.getRight(), node);
            }

            //Root is greater than node to add, so added to left
        } else if (rootData.compareTo(nodeData) > 0) {
            if (root.getLeft() == null) {
                root.setLeft(node);
            } else {
                addH(root.getLeft(), node);
            }
        }
        updateHBF(root);
        //Restructure
        balanceNode(root);

    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        if (!this.contains(data)) {
            throw new NoSuchElementException("Tree does not contain this data");
        }

        if (size == 1 && root.getData().compareTo(data) == 0) {
            T out = root.getData();
            root = null;
            size--;
            return out;

        } else {
            root = removeH(root, data);
        }
        size--;
        return data;
    }

    private AVLNode<T> removeH(AVLNode<T> node, T data) {
        if (node == null) {
            return null;
        }

        if (data.compareTo(node.getData()) == 0) {

            if (node.getLeft() == null && node.getRight() == null) {
                return null;

            } else if (node.getLeft() == null) {
                return node.getRight();

            } else if (node.getRight() == null) {
                return node.getLeft();

            } else {
                T replaceData = successorH(node);
                node.setData(replaceData);
                node.setRight(removeH(node.getRight(), replaceData));
            }
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeH(node.getLeft(), data));

        } else {
            node.setRight(removeH(node.getRight(), data));
        }


        updateHBF(node);
        balanceNode(node);

        return node;
    }
    private T successorH(AVLNode<T> node) {
        if (node.getRight() != null) {
            node = node.getRight();
        }
        if (node == null) {
            return null;
        }
        AVLNode<T> left = node.getLeft();
        if (left == null) {
            return node.getData();
        } else {
            return successorH(left);
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
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
            throw new IllegalArgumentException("Data cannot be null");
        }
        return getH(root, data);

    }

    private T getH(AVLNode<T> node, T data) {
        if (root == null) {
            throw new NoSuchElementException("Tree does not contain this data");
        }

        if (root.getData().compareTo(data) > 0) {
            return getH(root.getLeft(), data);
        } else if (root.getData().compareTo(data) < 0) {
            return getH(root.getRight(), data);
        }

        return root.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
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


        try {
            get(data);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return false;
        }


        //if no exceptions, then true
        return true;
    }



    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }

    }

    /**
     * @param node node to update height
     * @return int height
     */
    private int heightH(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /**
     *
     * @param node
     */

    private void updateHBF(AVLNode<T> node) {
        int lh = (node.getLeft() != null) ? node.getLeft().getHeight() : -1;
        int rh = (node.getRight() != null) ? node.getRight().getHeight() : -1;

        node.setHeight(Math.max(lh, rh) + 1);
        node.setBalanceFactor(lh - rh);
    }
    private void updateTree(AVLNode<T> node) {
        if (root.getLeft() != null) {
            updateTree(node.getLeft());
        }
        if (node.getRight() != null) {
            updateTree(node.getRight());
        }
        updateHBF(node);
    }

    /**
     *
     * @param node
     * @return AVLNode that's updated
     */
    private void rotateLeft(AVLNode<T> node) {
//        AVLNode<T> right = node.getRight();
//        node.setRight(right.getLeft());
//        right.setLeft(node);
//        updateTree(node);
        T origData = node.getData();
        AVLNode<T> right = node.getRight();
        AVLNode<T> newNode = new AVLNode<>(origData);

        newNode.setLeft(node.getLeft());
        newNode.setRight(right.getLeft());

        node.setLeft(newNode);
        node.setData(right.getData());
        node.setRight(right.getRight());
        right.setRight(null);

        updateTree(node);
    }

    /**
     *
     * @param node
     * @return AVLNode that's updated
     */
    private void rotateRight(AVLNode<T> node) {
        AVLNode<T> left = node.getLeft();
        T data = node.getData();
        AVLNode<T> tempnode = new AVLNode<T>(data);

        tempnode.setRight(node.getRight());
        tempnode.setLeft(left.getRight());

        node.setRight(tempnode);
        node.setData(left.getData());
        node.setRight(left.getLeft());

        left.setLeft(null);

        updateTree(node);

    }

    /**
     *
     * @param node node to balance
     */

    private void balanceNode(AVLNode<T> node) {
        int balance = node.getBalanceFactor();
        AVLNode left = node.getLeft();
        AVLNode right = node.getRight();
        if (balance > 1) {
            if (left != null) {
                if (left.getBalanceFactor() >= 0) {
                    rotateRight(node);
                } else if (left.getBalanceFactor() < 0);
                    rotateLeftRight(node);
            }
        } else if (balance < -1) {
            if (right != null) {
                if (right.getBalanceFactor() > 0) {
                    rotateRightLeft(node);

                } else if (right.getBalanceFactor() <= 0)
                    rotateLeft(node);
            }
        }
    }

    /**
     *
     * @param node node to rotate
     */

    private void rotateLeftRight(AVLNode<T> node) {
        rotateLeft(node.getLeft());
        rotateRight(node);
    }

    private void rotateRightLeft(AVLNode<T> node) {
        rotateRight(node.getRight());
        rotateLeft(node);
    }



    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;

    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        return null;
//        AVLNode<T> node = getH(root, data);
//        if (node.getLeft() != null) {
//            node = node.getLeft();
//        }
//        if (node == null) {
//            return null;
//        }
//        AVLNode<T> right = node.getRight();
//        if (right == null) {
//            return node.getData();
//        } else {
//            return predecessor(right.getData());
//        }

    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                50
     *              /    \
     *            25      75
     *           /  \     / \
     *          12   37  70  80
     *         /  \    \      \
     *        10  15    40    85
     *           /
     *          13
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        return null;

    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
