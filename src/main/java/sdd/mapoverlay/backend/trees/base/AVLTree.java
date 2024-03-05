package sdd.mapoverlay.backend.trees.base;//Classe AVLTree décrivant un arbre AVL
//Elle étend la classe BSTree décrivant les arbres binaires de recherche
//Les doublons ne sont pas autorisés

public class AVLTree<D extends Comparable> extends BSTree<D> {

//Ajout de la hauteur dans la liste des variables d'instance
//La hauteur est ainsi stockée dans la racine de l'arbre
	private int height;
	
//constructeur
	public AVLTree() {
		super();
		height = 0;
	}
	
//On redéfinit les "get" de la classe AVLTree afin d'éviter de faire du casting 
//à chaque usage. 
//En effet "getLeft" de la superclasse renvoie un BSTree et pas un AVLTree 
	public AVLTree<D> getLeft() {
		return (AVLTree<D>) super.getLeft();
	}
	public AVLTree<D> getRight() {
		return (AVLTree<D>) super.getRight();
	}
	
//"get" et "set" pour la hauteur
	public int getHeight() {
		return height;
	}
	public void setHeight(int h) {
		height = h;
	}

//On redéfinit la méthode insertEmpty de la classe BSTree, afin de travailler avec 
//le type AVLTree au lieu de BSTree
	public void insertEmpty(D d) {
		setData(d);
		setLeft(new AVLTree());
		setRight(new AVLTree());
		height = 1;
	}
	
//Calcul la hauteur en fonction des hauteurs des sous-arbres
//Le fait d'avoir stocké la hauteur donne ici un algorithme en O(1)
	public void computeHeight() {
		if (isEmpty()) 
			height = 0;
		else 
			height = 1 + Math.max(getLeft().getHeight(),
				     getRight().getHeight());
	}
	
//Calcul de la balance
	public int balance() {
		if (isEmpty()) 
			return 0;
		else 
			return getRight().getHeight() - getLeft().getHeight();
	}

//rotation gauche
	public void leftRotation() {
		D d = getData();
		AVLTree<D> t = getRight();
		setData(t.getData());
		setRight(t.getRight());
		t.setData(d);
		t.setRight(t.getLeft());
		t.setLeft(getLeft());
		setLeft(t);
		t.computeHeight();
		computeHeight();
	}

//rotation droite
	public void rightRotation() {
		D d = getData();
		AVLTree<D> t = getLeft();
		setData(t.getData());
		setLeft(t.getLeft());
		t.setData(d);
		t.setLeft(t.getRight());
		t.setRight(getRight());
		setRight(t);
		t.computeHeight();
		computeHeight();
	}

//equilibrage
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
	else computeHeight();
	}
}