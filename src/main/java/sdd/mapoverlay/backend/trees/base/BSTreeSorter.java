package sdd.mapoverlay.backend.trees.base;

/**
 * This class represents a sorter for binary search trees.
 *
 * @param <D> the type of elements in the binary search tree
 */
public class BSTreeSorter<D extends Comparable> {
	private D[] a;

	/**
	 * Constructs a BSTreeSorter with the given array.
	 *
	 * @param anArray the array to be sorted
	 */
	public BSTreeSorter(D[] anArray) {
		a = anArray;
	}

	/**
	 * Sorts the array using a binary search tree.
	 */
	public void sort() {
		BSTree<D> t = new BSTree<D>();
		for (int i = 0; i < a.length; i++)
			t.insert(a[i]);
	}
}