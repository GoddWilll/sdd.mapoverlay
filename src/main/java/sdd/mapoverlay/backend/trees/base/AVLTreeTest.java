package sdd.mapoverlay.backend.trees.base;

public class AVLTreeTest {
	public static void main(String[] args) {
		BSTree<String> t = new AVLTree<String>();
		
		System.out.println(t.searchMin());
		System.out.println("------------");
		
		t.insert("vero");
		t.insert("georges");
		t.insert("quentin");
		t.insert("vero");
		t.insert("zoe");
		t.insert("xavier");
		t.insert("willy");
		t.print();
		System.out.println("-----------");
		
		System.out.println(t.searchSucc("quentin"));
		System.out.println("-----------");
		
		t.suppress("quentin");
		t.print();
		System.out.println("-----------");
		
		System.out.println(t.searchMax());
		System.out.println("-----------");
		
		Integer[]a = {5,1,5,4,2,3};
		AVLTreeSorter<Integer> sorter = new AVLTreeSorter<Integer>(a);
		sorter.sort();
	}
}
