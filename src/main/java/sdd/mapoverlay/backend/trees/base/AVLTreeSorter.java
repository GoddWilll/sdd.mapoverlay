package sdd.mapoverlay.backend.trees.base;

/**
 * This class represents an AVL tree sorter.
 *
 * @param <D> the type of elements in the array to be sorted
 */
public class AVLTreeSorter<D extends Comparable> {
	private D[] a;

	/**
	 * Constructs an AVLTreeSorter with the given array.
	 *
	 * @param anArray the array to be sorted
	 */
	public AVLTreeSorter(D[] anArray) {
		a = anArray;
	}

	/**
	 * Sorts the array using AVL tree.
	 */
	public void sort() {
		AVLTree<D> t = new AVLTree<D>();
		for (int i = 0; i < a.length; i++)
			t.insert(a[i]);
	}
}