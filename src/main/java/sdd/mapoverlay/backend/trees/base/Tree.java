package sdd.mapoverlay.backend.trees.base;

/**
 * Represents a binary tree data structure.
 *
 * @param <D> the type of data stored in the tree
 */
public class Tree<D> {
	private D data;
	private Tree<D> left, right;
	private Tree<D> father;

	/**
	 * Constructs a tree node with the given data, left subtree, and right subtree.
	 *
	 * @param d the data to be stored in the node
	 * @param l the left subtree
	 * @param r the right subtree
	 */
	public Tree(D d, Tree<D> l, Tree<D> r) {
		data = d;
		left = l;
		right = r;
	}

	/**
	 * Constructs an empty tree node.
	 */
	public Tree() {
		this(null, null, null);
	}

	/**
	 * Returns the data stored in the tree node.
	 *
	 * @return the data stored in the node
	 */
	public D getData() {
		return data;
	}

	/**
	 * Returns the left subtree of the tree node.
	 *
	 * @return the left subtree
	 */
	public Tree<D> getLeft() {
		return left;
	}

	/**
	 * Returns the right subtree of the tree node.
	 *
	 * @return the right subtree
	 */
	public Tree<D> getRight() {
		return right;
	}

	/**
	 * Sets the data of the tree node.
	 *
	 * @param d the data to be set
	 */
	public void setData(D d) {
		data = d;
	}

	/**
	 * Sets the left subtree of the tree node.
	 *
	 * @param l the left subtree to be set
	 */
	public void setLeft(Tree<D> l) {
		left = l;
	}

	/**
	 * Sets the right subtree of the tree node.
	 *
	 * @param r the right subtree to be set
	 */
	public void setRight(Tree<D> r) {
		right = r;
	}

	/**
	 * Checks if the tree node is empty.
	 *
	 * @return true if the node is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (data == null && left == null && right == null)
			return true;
		else
			return false;
	}

	/**
	 * Inserts the given data into an empty tree node.
	 *
	 * @param d the data to be inserted
	 */
	public void insertEmpty(D d) {
		data = d;
		left = new Tree();
		right = new Tree();
	}

	/**
	 * Inserts the given data into an empty tree node and sets the father of the
	 * node.
	 *
	 * @param d      the data to be inserted
	 * @param father the father of the node
	 */
	public void insertEmptyST(D d, Tree father) {
		data = d;
		left = new Tree();
		right = new Tree();
		this.father = father;
	}

	/**
	 * Returns the height of the tree.
	 *
	 * @return the height of the tree
	 */
	public int height() {
		if (isEmpty())
			return 0;
		else
			return 1 + Math.max(left.height(), right.height());
	}

	/**
	 * Returns the father of the tree node.
	 *
	 * @return the father of the node
	 */
	public Tree<D> getFather() {
		return this.father;
	}

	/**
	 * Prints the tree in a readable format.
	 *
	 * @param prefix the prefix string to be added before each line
	 * @param isLeft true if the node is the left child of its parent, false
	 *               otherwise
	 */
	public void print(String prefix, boolean isLeft) {
		if (!isEmpty()) {
			System.out.println(prefix + (isLeft ? "⌊ " : "⌈ ") + getData() + " FATHER : " + getFather());
			right.print(prefix + (isLeft ? "│   " : "    "), false);
			left.print(prefix + (isLeft ? "│   " : "    "), true);
		}
	}
}