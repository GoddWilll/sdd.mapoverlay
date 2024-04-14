package sdd.mapoverlay.backend.trees.base;

/**
 * This class is used to test the functionality of the BSTree class.
 */
public class BSTreeTest {
	/**
	 * The main method that runs the test cases.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		BSTree<String> t = new BSTree<String>();

		System.out.println(t.searchMin());
		System.out.println("------------");

		t.insert("vero");
		t.insert("georges");
		t.insert("quentin");
		t.insert("vero");
		t.insert("zoe");
		t.insert("xavier");
		t.insert("willy");
		// t.print();
		System.out.println("-----------");

		System.out.println(t.searchSucc("quentin"));
		System.out.println("-----------");
		t.suppress("vero");
		// t.print();
		System.out.println("-----------");

		System.out.println(t.searchMin());
		System.out.println("-----------");

		Integer[] a = { 5, 1, 5, 4, 2, 3 };
		BSTreeSorter<Integer> sorter = new BSTreeSorter<Integer>(a);
		sorter.sort();
	}
}
