package sdd.mapoverlay.backend.trees.base;//Classe BSTree decrivant un arbre binaire de recherche (Binary Search Tree)
//Elle etend la classe Tree, sachant que les donnees sont a present comparables
//Les doublons ne sont pas autorises

import sdd.mapoverlay.backend.points.EventPoint;
import sdd.mapoverlay.backend.points.types.EventType;
import sdd.mapoverlay.backend.points.types.Position;
import sdd.mapoverlay.backend.segments.Segment;

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
				else 	if (getData().compareTo(d) > 0) {
					getLeft().insert(d);
				} else if (getData().compareTo(d) == 0){
					if (d.getClass().equals(EventPoint.class)){
						if (((EventPoint)d).getEventType() == EventType.UPPERENDPOINT ){
							for (Segment segment : ((EventPoint) d).getSegments()){
								((EventPoint) getData()).addSegment(segment);
							}
						}
					} else if (d.getClass().equals(Segment.class)){
						insertStatusStructureVariant(d);
					}

				}
				equilibrate();
			}
		}
	}


	public void insertStatusStructureVariant(D d){
		if (isEmpty()){
			setData(d);
			setLeft(new AVLTree());
			setRight(new AVLTree());
			getLeft().insertEmpty(d);
		}
		else {
			if (((Segment) getData()).determinePosition(((Segment)d).getUpperEndPoint()) == Position.RIGHT) {
				if (getRight().isEmpty()){
					getLeft().insert(getData());
					getRight().insert(d);
				} else {
					getRight().insertStatusStructureVariant(d);
				}
			} else if (((Segment) getData()).determinePosition(((Segment)d).getUpperEndPoint()) == Position.LEFT){
				if (getLeft().isEmpty()){
					getLeft().insertEmpty(d);
					getRight().insertEmpty(getData());
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
		if (getIsStatus()){
			suppressStatusStructure(d);
		} else {
			if (!isEmpty()) {
				if (getData().compareTo(d) < 0)
					getRight().suppress(d);
				else 	if (getData().compareTo(d) > 0)
					getLeft().suppress(d);
				else 	suppressRoot();
				equilibrate();
			}
		}

	}

	public void suppressStatusStructure(D d){
		if (!isEmpty()){
			if (((Segment) d).determinePosition(((Segment)getData())) == Position.LEFT){
				getRight().suppressStatusStructure(d);
			}
			else if (((Segment)d).determinePosition(((Segment)getData())) == Position.RIGHT) {
				getLeft().suppressStatusStructure(d);
			}
			else {
				if (((Segment) d).getVector() == ((Segment) getData()).getVector() && height() > 2){
					D data = getData();
					suppressRoot();
					D newData = getData(); // on recupere la nouvelle valeur de la racine
					getRight().suppressStatusStructure(newData); // on supprime la valeur residuelle si elle existe
					getLeft().replace(data, newData); // on remplace la donnee la plus a droite du sous arbre gauche par la nouvelle valeur
					equilibrate();
				} else if ( getData().compareTo(d) == 0 && height() == 2){
					if (!getRight().isEmpty() && getLeft().isEmpty()){
						suppressRoot();
					} else if (getRight().isEmpty() && !getLeft().isEmpty()){
						suppressRoot();
						suppressRoot();
					} else {
						suppressRoot();
						getLeft().suppressRoot();
					}
					equilibrate();
				} else if (((Segment) d).getVector() == ((Segment) getData()).getVector() && height() == 1){
					suppressRoot();
					equilibrate();
				}
			}
			equilibrate();
		}
	}

	public void replace(D d, D replacement){
		if (getData().compareTo(d) == 0){
			setData(replacement);
		} else {
			getRight().replace(d, replacement);
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