package sdd.mapoverlay.backend.trees.base;//Classe AVLTree décrivant un arbre AVL
//Elle étend la classe BSTree décrivant les arbres binaires de recherche
//Les doublons ne sont pas autorisés

import org.w3c.dom.events.Event;
import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.segments.Segment;

import java.util.ArrayList;

public class AVLTree<D extends Comparable> extends BSTree<D> {

	final Boolean isStatus = false;
//Ajout de la hauteur dans la liste des variables d'instance
//La hauteur est ainsi stockée dans la racine de l'arbre
	private int height;

	private AVLTree<D> father;
	
//constructeur
	public AVLTree() {
		super();
		height = 0;
	}

	public AVLTree(AVLTree<D> father){
		super();
		height = 0;
		this.father = father;
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
		setLeft(new AVLTree(this));
		setRight(new AVLTree(this));
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
		getRight().setFather(this);
		t.setData(d);
		t.setRight(t.getLeft());
		t.setLeft(getLeft());
		t.getRight().setFather(t);
		t.getLeft().setFather(t);
		setLeft(t);
		getLeft().setFather(this);
//		t.setFather(this);
		t.computeHeight();
		computeHeight();
	}

//rotation droite
	public void rightRotation() {
		D d = getData();
		AVLTree<D> t = getLeft(); // t = fils gauche
		setData(t.getData()); // on defini notre donnee comme celles de t
		setLeft(t.getLeft());
		getLeft().setFather(this);
		t.setData(d);
		t.setLeft(t.getRight());
		t.setRight(getRight());

		t.getRight().setFather(t);

//		t.getLeft().setFather(t);
//		t.getRight().setFather(t);
		setRight(t);
//		t.setFather(this);
		getRight().setFather(this);
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

	public Boolean getIsStatus(){
		return isStatus;
	}

	public void setFather(AVLTree<D> father){
		this.father = father;
	}

	public AVLTree<D> getFather(){
		return this.father;
	}


	public ArrayList<D> getRightNeighbors(EventPoint p){
		ArrayList<D> neighbors = new ArrayList<>();
		if (getFather() == null){
			return neighbors;
		} else {
			boolean searching = true;
			AVLTree<D> startingLeaf = this;
			startingLeaf.getFather().print("", true);
			while (searching){
				if (startingLeaf.getFather().getLeft() == startingLeaf){
					AVLTree<D> currentTree = startingLeaf;
					currentTree = currentTree.getFather().getRight();
					if (currentTree.getData() == null){
						return neighbors;
					}
					if (currentTree.getLeft().getData() != null){
						currentTree = currentTree.getLeft();
					}
					System.out.println(((Segment)currentTree.getData()).containsPoint(p));
					if (((Segment)currentTree.getData()).containsPoint(p)){
						neighbors.add(currentTree.getData());
						startingLeaf = currentTree;
					} else {
						return neighbors;
					}
				} else if (startingLeaf.getFather().getRight() == startingLeaf){
					AVLTree<D> currentTree = startingLeaf;
					if (currentTree.getFather().getFather() == null) {
						return neighbors;
					}

					while(currentTree.getFather() != null && currentTree.getFather().getRight() == currentTree  ){
						currentTree = currentTree.getFather();
					}
					if (currentTree.getFather() == null){
						return neighbors;
					}

					currentTree = currentTree.getFather().getRight();

					while (currentTree.getLeft().getData() != null){
						currentTree = currentTree.getLeft();
					}
					if (((Segment)currentTree.getData()).containsPoint(p)){
						neighbors.add(currentTree.getData());
						startingLeaf = currentTree;
					} else {
						return neighbors;
					}
				}
			}
		}
		return neighbors;
	}

	public ArrayList<D> getLeftNeighbors(EventPoint p){
		ArrayList<D> neighbors = new ArrayList<>();
		if (getFather() == null){
			return neighbors;
		} else {
			boolean searching = true;
			AVLTree<D> startingLeaf = this;
			System.out.println(startingLeaf.getData());
			while (searching){
				if (startingLeaf.getFather().getRight() == startingLeaf){ // on part d'un fils droit
					AVLTree<D> currentTree = startingLeaf; // on va dans le pere vu qu'on est un fils droit
					currentTree = currentTree.getFather().getLeft();
					if (currentTree.getRight().getData() != null){
						currentTree = currentTree.getRight();
					}
					if (((Segment)currentTree.getData()).containsPoint(p)){
						neighbors.add(currentTree.getData());
						startingLeaf = currentTree;
					} else {
						return neighbors;
					}
				} else if (startingLeaf.getFather().getLeft() == startingLeaf){
					AVLTree<D> currentTree = startingLeaf;
					if (currentTree.getFather().getFather() == null) {
						return neighbors;
					}

					while(currentTree.getFather() != null && currentTree.getFather().getLeft() == currentTree){
						currentTree = currentTree.getFather();
					}
					if (currentTree.getFather() == null){
						return neighbors;
					}
					currentTree = currentTree.getFather().getLeft();

					while (currentTree.getRight().getData() != null){
						currentTree = currentTree.getRight();
					}
					if (((Segment)currentTree.getData()).containsPoint(p)){
						neighbors.add(currentTree.getData());
						startingLeaf = currentTree;
					} else {
						return neighbors;
					}
				}
			}
		}
		return neighbors;
	}


	@Override
	public String toString(){
		return "" + getData();
	}


}