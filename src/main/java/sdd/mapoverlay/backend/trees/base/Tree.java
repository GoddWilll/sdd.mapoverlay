package sdd.mapoverlay.backend.trees.base;//Classe Tree decrivant un arbre binaire selon la definition recursive vue au cours
//L'arbre vide n'est pas represente par une reference vide, 
//mais par un "noeud vide", c'est-a-dire une feuille sans donnee

public class Tree<D> {
	private D data;
	private Tree<D> left, right;

	private Tree<D> father;
	
//constructeurs
	public Tree(D d, Tree<D> l, Tree<D> r) {
		data = d;
		left = l;
		right = r;
	}


	public Tree() {
		this(null,null,null);
	}


//get 
	public D getData() {
		return data;
	}
	public Tree<D> getLeft() {
		return left;
	}
	public Tree<D> getRight() {
		return right;
	}

//set
	public void setData(D d) {
		data = d;
	}
	public void setLeft(Tree<D> l) {
		left = l;
	}
	public void setRight(Tree<D> r) {
		right = r;
	}

//test de l'arbre vide, c'est-a-dire du "noeud vide"
	public boolean isEmpty() {
		if (data == null && left == null && right == null) 
			return true;
		else
			return false;
	}
	
//remplit un noeud vide avec la donnee d et 2 sous-arbres vides
	public void insertEmpty(D d) {
		data = d;
		left = new Tree();
		right = new Tree();
	}

	public void insertEmptyST(D d, Tree father){
		data = d;
		left = new Tree();
		right = new Tree();
		this.father = father;
	}
	
//calcul de la hauteur
	public int height() {
		if (isEmpty()) 
			return 0;
		else
			return 1 + Math.max(left.height(),right.height());
	}

//affichage recursif
//	public void print() {
//		if (!isEmpty()) {
//			left.print();
//			System.out.println(getData());
//			right.print();
//		}
//	}

	public Tree<D> getFather(){
		return this.father;
	}

	public void print(String prefix, boolean isLeft){
		if (!isEmpty()){
			System.out.println(prefix + (isLeft ? "⌊ " : "⌈ ") + getData() + " FATHER : " + getFather());
			right.print(prefix + (isLeft ? "│   " : "    "), false);
			left.print(prefix + (isLeft ? "│   " : "    "), true);

		}
	}

}