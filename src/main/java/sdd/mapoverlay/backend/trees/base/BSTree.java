package sdd.mapoverlay.backend.trees.base;

/**
 * This class represents a Binary Search Tree (BST) that extends the Tree class.
 * It provides methods for searching, inserting, and suppressing elements in the
 * tree.
 *
 * @param <D> the type of data stored in the tree, which must implement the
 *            Comparable interface
 */
public class BSTree<D extends Comparable> extends Tree<D> {
	final Boolean isStatus = false;

	/**
	 * Constructs an empty BSTree.
	 */
	public BSTree() {
		super();
	}

	/**
	 * Constructs a BSTree with the specified data, left subtree, and right subtree.
	 *
	 * @param d the data to be stored in the tree
	 * @param l the left subtree
	 * @param r the right subtree
	 */
	public BSTree(D d, BSTree l, BSTree r) {
		super(d, l, r);
	}

	/**
	 * Returns the left subtree of this BSTree.
	 *
	 * @return the left subtree
	 */
	public BSTree<D> getLeft() {
		return (BSTree<D>) super.getLeft();
	}

	/**
	 * Returns the right subtree of this BSTree.
	 *
	 * @return the right subtree
	 */
	public BSTree<D> getRight() {
		return (BSTree<D>) super.getRight();
	}

	/**
	 * Searches for the specified data in the BSTree.
	 *
	 * @param d the data to search for
	 * @return true if the data is found, false otherwise
	 */
	public boolean search(D d) {
		if (isEmpty())
			return false;
		else if (getData().compareTo(d) < 0)
			return getRight().search(d);
		else if (getData().compareTo(d) > 0)
			return getLeft().search(d);
		else
			return true;
	}

	/**
	 * Inserts the specified data into the BSTree.
	 *
	 * @param d the data to insert
	 */
	public void insert(D d) {
		if (isEmpty())
			insertEmpty(d);
		else {
			if (getData().compareTo(d) < 0)
				getRight().insert(d);
			else if (getData().compareTo(d) > 0) {
				getLeft().insert(d);
			}
			equilibrate();
		}
	}

	/**
	 * Inserts the specified data into an empty BSTree.
	 *
	 * @param d the data to insert
	 */
	public void insertEmpty(D d) {
		setData(d);
		setLeft(new AVLTree((AVLTree) this));
		setRight(new AVLTree((AVLTree) this));
	}

	/**
	 * Removes the specified data from the BSTree.
	 *
	 * @param d the data to remove
	 */
	public void suppress(D d) {
		if (!isEmpty()) {
			if (getData().compareTo(d) < 0)
				getRight().suppress(d);
			else if (getData().compareTo(d) > 0)
				getLeft().suppress(d);
			else
				suppressRoot();
			equilibrate();
		}
	}

	/**
	 * Removes the root node of the BSTree.
	 */
	public void suppressRoot() {
		if (getLeft().isEmpty()) {
			BSTree<D> t = getRight();
			setData(t.getData());
			setLeft(t.getLeft());
			setRight(t.getRight());

		} else if (getRight().isEmpty()) {
			BSTree<D> t = getLeft();
			setData(t.getData());
			setRight(t.getRight());
			setLeft(t.getLeft());

		} else
			setData(getRight().suppressMin());
		equilibrate();
	}

	/**
	 * Removes and returns the minimum element in the BSTree.
	 *
	 * @return the minimum element in the tree
	 */
	public D suppressMin() {
		D min;
		if (getLeft().isEmpty()) {
			min = getData();
			BSTree<D> t = getRight();
			setData(t.getData());
			setLeft(t.getLeft());
			setRight(t.getRight());
		} else
			min = getLeft().suppressMin();
		equilibrate();
		return min;
	}

	/**
	 * Removes and returns the maximum element in the BSTree.
	 *
	 * @return the maximum element in the tree
	 */
	public D suppressMax() {
		D max;
		if (getRight().isEmpty()) {
			max = getData();
			BSTree<D> t = getLeft();
			setData(t.getData());
			setRight(t.getRight());
			setLeft(t.getLeft());
		} else {
			max = getRight().suppressMax();
		}
		equilibrate();
		return max;
	}

	/**
	 * Rebalances the BSTree.
	 */
	public void equilibrate() {
	}

	/**
	 * Searches for the minimum element in the BSTree.
	 *
	 * @return the minimum element in the tree, or null if the tree is empty
	 */
	public D searchMin() {
		if (isEmpty())
			return null;
		else if (getLeft().isEmpty())
			return getData();
		else
			return getLeft().searchMin();
	}

	/**
	 * Searches for the maximum element in the BSTree.
	 *
	 * @return the maximum element in the tree, or null if the tree is empty
	 */
	public D searchMax() {
		if (isEmpty())
			return null;
		else if (getRight().isEmpty())
			return getData();
		else
			return getRight().searchMax();
	}

	/**
	 * Searches for the successor of the specified data in the BSTree.
	 *
	 * @param d the data to find the successor for
	 * @return the successor of the data, or null if the data is not found or has no
	 *         successor
	 */
	public D searchSucc(D d) {
		return succ(d, null);
	}

	private D succ(D d, D x) {
		if (isEmpty())
			return null;
		else if (getData().compareTo(d) < 0)
			return getRight().succ(d, x);
		else if (getData().compareTo(d) > 0)
			return getLeft().succ(d, getData());
		else if (getRight().isEmpty())
			return x;
		else
			return getRight().searchMin();
	}
}