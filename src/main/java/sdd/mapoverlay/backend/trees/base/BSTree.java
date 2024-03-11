package sdd.mapoverlay.backend.trees.base;//Classe BSTree decrivant un arbre binaire de recherche (Binary Search Tree)
//Elle etend la classe Tree, sachant que les donnees sont a present comparables
//Les doublons ne sont pas autorises

import sdd.mapoverlay.backend.points.EventPoint;

public class BSTree<D extends Comparable> extends Tree<D> {
	final Boolean isStatus = false;
//constructeurs
	public BSTree() {
		super();
	}
	public BSTree(D d, BSTree l, BSTree r) {
		super(d,l,r);
	}
	
//On redefinit les "get" de la classe Tree afin d'eviter de faire du casting 
//a chaque usage. 
//En effet "getLeft" de la superclasse renvoie un Tree et pas un BSTree
	public BSTree<D> getLeft() {
		return (BSTree<D>) super.getLeft();
	}
	public BSTree<D> getRight() {
		return (BSTree<D>) super.getRight();
	}

//recherche recursive d'une donnee
	public boolean search(D d) {
		if (isEmpty())
			return false;
		else	if (getData().compareTo(d) < 0) 
				return getRight().search(d);
			else 	if (getData().compareTo(d) > 0)
					return getLeft().search(d);
				else 	return true;
	}

//insertion recursive d'une donnee
//rem: la methode equilibrate ne fait rien ici, mais sera utile pour la classe AVLTree
	public void insert(D d) {
		if (getIsStatus()){
			insertStatusStructureVariant(d);
		} else {
			if (isEmpty())
				insertEmpty(d);
			else	{
				if (getData().compareTo(d) < 0)
					getRight().insert(d);
				else 	if (getData().compareTo(d) > 0)
					getLeft().insert(d);
				equilibrate();
			}
		}
	}

	public void insertStatusStructureVariant(D d){
		if (isEmpty()){
			setData(d);
			setLeft(new AVLTree());
			setRight(new AVLTree());
			getLeft().insert(d);
		}
		else {
			if (getData().compareTo(d) < 0) { // si la donnee a inserer est plus grande que la donnee du noeud
				if (getRight().isEmpty()){
					getLeft().insert(getData());
					getRight().insert(d);
				} else {
					getRight().insertStatusStructureVariant(d);
				}
			} else if (getData().compareTo(d) > 0){ // si la donnee a inserer est plus petite
				if (getLeft().isEmpty()){
					getLeft().insert(d);
					getRight().insert(getData());
					setData(d);
				} else {
					getLeft().insertStatusStructureVariant(d);
				}
			}
			equilibrate();
		}
	}



//On redefinit la methode insertEmpty de la classe Tree, afin de travailler avec 
//le type BSTree au lieu de Tree
	public void insertEmpty(D d) {
		setData(d);
		setLeft(new BSTree());
		setRight(new BSTree());
	}
	
//suppression recursive d'une donnee
//rem: la methode equilibrate ne fait rien ici, mais sera utile pour la classe AVLTree
	public void suppress(D d) {
		if (!isEmpty()) {
			if (getData().compareTo(d) < 0) 
				getRight().suppress(d);
			else 	if (getData().compareTo(d) > 0)
					getLeft().suppress(d);
				else 	suppressRoot();
			equilibrate();
			}
	}

//suppression de la racine (on sait qu'elle existe)
//rem: la methode equilibrate ne fait rien ici, mais sera utile pour la classe AVLTree
	public void suppressRoot() {
		if (getLeft().isEmpty()) {
			BSTree<D> t = getRight();
			setData(t.getData());
			setLeft(t.getLeft());
			setRight(t.getRight());
			}
		else if (getRight().isEmpty()) {
			BSTree<D> t = getLeft();
			setData(t.getData());
			setRight(t.getRight());
			setLeft(t.getLeft());
			}
		else
			setData(getRight().suppressMin());
		equilibrate();
	}

//suppression du minimum et retour de ce minimum (on sait qu'il existe)
//rem: la methode equilibrate ne fait rien ici, mais sera utile pour la classe AVLTree
	public D suppressMin() {
		D min;
		if (getLeft().isEmpty()) {
			min = getData();
			BSTree<D> t = getRight();
			setData(t.getData());
			setLeft(t.getLeft());
			setRight(t.getRight());
			}
		else
			min = getLeft().suppressMin();
		equilibrate();
		return min;
	}
	
//equilibrage vide dans le cas des BSTree, sera defini pour les AVLTree
	public void equilibrate() {
	}
	
//recherche recursive du minimum des donnees
	public D searchMin() {
		if (isEmpty()) 
			return null;
		else if (getLeft().isEmpty()) 
				return getData();
			else 	return getLeft().searchMin();
	}
	
//recherche recursive du maximum des donnees
	public D searchMax() {
		if (isEmpty()) 
			return null;
		else if (getRight().isEmpty()) 
				return getData();
			else 	return getRight().searchMax();
	}
	
//recherche recursive du successeur d'une donnee
//appel a la methode succ avec le parametre auxiliaire x initialise a null
	public D searchSucc(D d) {
		return succ(d,null);
	}
	
	private D succ(D d, D x) {
		if (isEmpty())
			return null;
		else	if (getData().compareTo(d) < 0) 
				return getRight().succ(d,x);
			else 	if (getData().compareTo(d) > 0) 
					return getLeft().succ(d,getData());
				else 	if (getRight().isEmpty()) 
						return x;
					else    return getRight().searchMin();
	}

	public Boolean getIsStatus() {
		return isStatus;
	}
}