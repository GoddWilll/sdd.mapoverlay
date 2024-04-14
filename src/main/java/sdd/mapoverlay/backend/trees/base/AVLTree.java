package sdd.mapoverlay.backend.trees.base;

import org.w3c.dom.events.Event;
import sdd.mapoverlay.backend.Logic;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

/**
 * This class represents an AVL Tree, which is a self-balancing binary search
 * tree.
 * It extends the BSTree class and does not allow duplicates.
 *
 * @param <D> the type of data stored in the tree, which must implement the
 *            Comparable interface
 */
public class AVLTree<D extends Comparable> extends BSTree<D> {

	private final Boolean isStatus = false;
	private int height;
	private AVLTree<D> father;

	/**
	 * Constructs an empty AVLTree with height 0.
	 */
	public AVLTree() {
		super();
		height = 0;
	}

	/**
	 * Constructs an AVLTree with the given father and height 0.
	 *
	 * @param father the father AVLTree
	 */
	public AVLTree(AVLTree<D> father) {
		super();
		height = 0;
		this.father = father;
	}

	/**
	 * Returns the left subtree of this AVLTree.
	 *
	 * @return the left subtree
	 */
	public AVLTree<D> getLeft() {
		return (AVLTree<D>) super.getLeft();
	}

	/**
	 * Returns the right subtree of this AVLTree.
	 *
	 * @return the right subtree
	 */
	public AVLTree<D> getRight() {
		return (AVLTree<D>) super.getRight();
	}

	/**
	 * Returns the height of this AVLTree.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Inserts a data into an empty AVLTree.
	 *
	 * @param d the data to insert
	 */
	public void insertEmpty(D d) {
		setData(d);
		setLeft(new AVLTree(this));
		setRight(new AVLTree(this));
		height = 1;
	}

	/**
	 * Computes the height of this AVLTree.
	 */
	public void computeHeight() {
		if (isEmpty())
			height = 0;
		else
			height = 1 + Math.max(getLeft().getHeight(), getRight().getHeight());
	}

	/**
	 * Calculates the balance factor of this AVLTree.
	 *
	 * @return the balance factor
	 */
	public int balance() {
		if (isEmpty())
			return 0;
		else
			return getRight().getHeight() - getLeft().getHeight();
	}

	/**
	 * Performs a left rotation on this AVLTree.
	 */
	public void leftRotation() {
		D d = getData();
		AVLTree<D> t = getRight();
		setData(t.getData());
		setRight(t.getRight());
		getRight().setFather(this);
		t.setData(d);
		t.setRight(t.getLeft());
		t.setLeft(getLeft());
		t.getRight().setFather(t);
		t.getLeft().setFather(t);
		setLeft(t);
		getLeft().setFather(this);
		t.computeHeight();
		computeHeight();
	}

	/**
	 * Performs a right rotation on this AVLTree.
	 */
	public void rightRotation() {
		D d = getData();
		AVLTree<D> t = getLeft();
		setData(t.getData());
		getLeft().setFather(this);
		t.setData(d);
		t.setLeft(t.getRight());
		t.setRight(getRight());
		t.getRight().setFather(t);
		setRight(t);
		getRight().setFather(this);
		t.computeHeight();
		computeHeight();
	}

	/**
	 * Restores the balance of this AVLTree.
	 */
	public void equilibrate() {
		if (balance() == 2)
			if (getRight().balance() >= 0)
				leftRotation();
			else {
				getRight().rightRotation();
				leftRotation();
			}
		else if (balance() == -2)
			if (getLeft().balance() <= 0)
				rightRotation();
			else {
				getLeft().leftRotation();
				rightRotation();
			}
		else
			computeHeight();
	}

	/**
	 * Returns the status of this AVLTree.
	 *
	 * @return the status
	 */
	public Boolean getIsStatus() {
		return isStatus;
	}

	/**
	 * Sets the father of this AVLTree.
	 *
	 * @param father the father AVLTree
	 */
	public void setFather(AVLTree<D> father) {
		this.father = father;
	}

	/**
	 * Returns the father of this AVLTree.
	 *
	 * @return the father AVLTree
	 */
	public AVLTree<D> getFather() {
		return this.father;
	}

	/**
	 * Returns the right neighbors of the given EventPoint in this AVLTree.
	 *
	 * @param p the EventPoint
	 * @return the list of right neighbors
	 */
	public ArrayList<D> getRightNeighbors(EventPoint p) {
		ArrayList<D> neighbors = new ArrayList<>();
		if (getFather() == null) {
			return neighbors;
		} else {
			AVLTree<D> tree = this;
			while (true) {
				if (tree.getFather().getLeft() == tree) {
					tree = tree.getFather().getRight();

					while (!tree.getLeft().isEmpty()) {
						tree = tree.getLeft();
					}

					if (((Segment) tree.getData()).containsPoint(p)) {
						neighbors.add(tree.getData());
					} else {
						return neighbors;
					}
				} else if (tree.getFather().getRight() == tree) {
					if (tree.getFather().getFather() == null) {
						return neighbors;
					} else {
						while (tree.getFather() != null && tree.getFather().getRight() == tree) {
							tree = tree.getFather();
						}

						if (tree.getFather() == null) {
							return neighbors;
						}

						tree = tree.getFather().getRight();

						while (!tree.getLeft().isEmpty()) {
							tree = tree.getLeft();
						}

						if (((Segment) tree.getData()).containsPoint(p)) {
							neighbors.add(tree.getData());
						} else {
							return neighbors;
						}

					}
				}
			}

		}
	}

	/**
	 * Returns the left neighbors of the given EventPoint in this AVLTree.
	 *
	 * @param p the EventPoint
	 * @return the list of left neighbors
	 */
	public ArrayList<D> getLeftNeighbors(EventPoint p) {
		ArrayList<D> neighbors = new ArrayList<>();
		if (getFather() == null) {
			return neighbors;
		} else {
			AVLTree<D> tree = this;
			while (true) {
				if (tree.getFather().getRight() == tree) {
					tree = tree.getFather().getLeft();
					while (!tree.getRight().isEmpty()) {
						tree = tree.getRight();
					}
					if (((Segment) tree.getData()).containsPoint(p)) {
						neighbors.add(tree.getData());
					} else {
						return neighbors;
					}
				} else if (tree.getFather().getLeft() == tree) {
					if (tree.getFather().getFather() == null) {
						return neighbors;
					} else {
						while (tree.getFather() != null && tree.getFather().getLeft() == tree) {
							tree = tree.getFather();
						}
						if (tree.getFather() == null)
							return neighbors;

						tree = tree.getFather().getLeft();

						while (!tree.getRight().isEmpty()) {
							tree = tree.getRight();
						}

						if (((Segment) tree.getData()).containsPoint(p)) {
							neighbors.add(tree.getData());
						} else {
							return neighbors;
						}

					}
				}
			}

		}
	}

	/**
	 * Returns a string representation of this AVLTree.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "" + getData();
	}

	/**
	 * Inserts a data into this AVLTree using the status structure variant.
	 *
	 * @param d the data to insert
	 * @param p the EventPoint
	 */
	public void insertStatusStructureVariant(D d, EventPoint p) {
		if (isEmpty()) {
			insertEmpty(d);
		} else {
			if (height() == 1) {
				if (Math.abs(((Segment) getData()).xAtYp(p) - ((Segment) d).xAtYp(p)) < Logic.EPSILON) {
					getLeft().insertEmpty(getData());
					getRight().insertEmpty(d);

					getLeft().setFather(this);
					getRight().setFather(this);

				} else if (((Segment) getData()).xAtYp(p) > ((Segment) d).xAtYp(p)) {
					getLeft().insertEmpty(d);
					getRight().insertEmpty(getData());
					setData(d);

					getLeft().setFather(this);
					getRight().setFather(this);

				} else if (((Segment) getData()).xAtYp(p) < ((Segment) d).xAtYp(p)) {
					getLeft().insertEmpty(getData());
					getRight().insertEmpty(d);

					getLeft().setFather(this);
					getRight().setFather(this);
				}
			} else {
				if (Math.abs(((Segment) getData()).xAtYp(p) - ((Segment) d).xAtYp(p)) < Logic.EPSILON) {
					getRight().insertStatusStructureVariant(d, p);
				} else if (((Segment) getData()).xAtYp(p) < ((Segment) d).xAtYp(p)) {
					getRight().insertStatusStructureVariant(d, p);
				} else if (((Segment) getData()).xAtYp(p) > ((Segment) d).xAtYp(p)) {
					getLeft().insertStatusStructureVariant(d, p);
				}
			}
		}
		equilibrate();
	}

	/**
	 * Removes the data from this AVLTree using the status structure.
	 *
	 * @param d the data to remove
	 * @param p the EventPoint
	 */
	public void suppressStatusStructure(D d, EventPoint p) {
		if (!isEmpty()) {
			if (Math.abs(((Segment) getData()).xAtYp(p) - ((Segment) d).xAtYp(p)) < Logic.EPSILON) {
				if (!((Segment) getData()).isSameSegment((Segment) d)) {
					getLeft().suppressStatusStructure(d, p);
				} else {
					if (height() == 1) {
						if (getFather() == null) {
							suppressRoot();
						} else {
							if (getFather().getLeft().height() == 1) {
								getFather().getLeft().suppressRoot();
								getFather().getRight().suppressRoot();
							} else {
								suppressRoot();
								getFather().suppressRoot();
							}
						}
					} else if (height() == 2) {
						if (getRight().isEmpty()) {
							suppressRoot();
							suppressRoot();
						} else {
							suppressRoot();
							getLeft().suppressRoot();
						}
					} else if (height() > 2) {
						suppressRoot();
						D newData = getData();
						getRight().suppressMin();
						getLeft().replace(newData);
					}
				}
			} else if (((Segment) getData()).xAtYp(p) < ((Segment) d).xAtYp(p)) {
				getRight().suppressStatusStructure(d, p);
			} else if (((Segment) getData()).xAtYp(p) > ((Segment) d).xAtYp(p)) {
				getLeft().suppressStatusStructure(d, p);
			}
			equilibrate();
		}
	}

	/**
	 * Removes the root of this AVLTree.
	 */
	@Override
	public void suppressRoot() {
		if (getLeft().isEmpty()) {
			AVLTree<D> t = getRight();
			setData(t.getData());
			setLeft(t.getLeft());
			setRight(t.getRight());

		} else if (getRight().isEmpty()) {
			AVLTree<D> t = getLeft();
			setData(t.getData());
			setRight(t.getRight());
			setLeft(t.getLeft());
			getRight().setFather(this);
			getLeft().setFather(this);
		} else
			setData(getRight().suppressMin());
		equilibrate();
	}

	/**
	 * Replaces the data in this AVLTree with the given replacement.
	 *
	 * @param replacement the replacement data
	 */
	public void replace(D replacement) {
		if (getRight().getData() != null) {
			getRight().replace(replacement);
		} else {
			setData(replacement);
		}
	}

	/**
	 * Searches for the given data in this AVLTree.
	 *
	 * @param d the data to search for
	 * @return true if the data is found, false otherwise
	 */
	@Override
	public boolean search(D d) {
		if (isEmpty()) {
			return false;
		} else if (Math.abs(((EventPoint) getData()).getY() - ((EventPoint) d).getY()) < Logic.EPSILON
				&& Math.abs(((EventPoint) getData()).getX() - ((EventPoint) d).getX()) < Logic.EPSILON) {
			return true;
		} else if (((EventPoint) getData()).getY() < ((EventPoint) d).getY()) {
			return getRight().search(d);
		} else {
			return getLeft().search(d);
		}
	}
}