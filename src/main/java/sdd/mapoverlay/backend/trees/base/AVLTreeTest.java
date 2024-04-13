package sdd.mapoverlay.backend.trees.base;

public class AVLTreeTest {
	public static void main(String[] args) {
		BSTree<String> t = new AVLTree<String>();

		t.searchMin();

		t.insert("vero");
		t.insert("georges");
		t.insert("quentin");
		t.insert("vero");
		t.insert("zoe");
		t.insert("xavier");
		t.insert("willy");
		// t.print();
		// System.out.println("-----------");

		t.searchSucc("quentin");
		// System.out.println("-----------");

		t.suppress("quentin");
		// t.print();

		t.searchMax();

		Integer[] a = { 5, 1, 5, 4, 2, 3 };
		AVLTreeSorter<Integer> sorter = new AVLTreeSorter<Integer>(a);
		sorter.sort();
	}
}
