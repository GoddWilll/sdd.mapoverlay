package sdd.mapoverlay.backend.trees.base;//Classe BSTree decrivant un arbre binaire de recherche (Binary Search Tree)

//Elle etend la classe Tree, sachant que les donnees sont a present comparables
//Les doublons ne sont pas autorises

import sdd.mapoverlay.backend.segments.Segment;

public class BSTree<D extends Comparable> extends Tree<D> {
	final Boolean isStatus = false;

	public BSTree<D> father;

	// constructeurs
	public BSTree() {
		super();
	}

	public BSTree(BSTree<D> father) {
		super();
		this.father = father;
	}

	public BSTree(D d, BSTree l, BSTree r) {
		super(d, l, r);
	}

	// On redefinit les "get" de la classe Tree afin d'eviter de faire du casting
	// a chaque usage.
	// En effet "getLeft" de la superclasse renvoie un Tree et pas un BSTree
	public BSTree<D> getLeft() {
		return (BSTree<D>) super.getLeft();
	}

	public BSTree<D> getRight() {
		return (BSTree<D>) super.getRight();
	}

	// recherche recursive d'une donnee
	public boolean search(D d) {
		if (isEmpty())
			return false;
		else if (getData().compareTo(d) < 0)
			return getRight().search(d);
		else if (getData().compareTo(d) > 0)
			return getLeft().search(d);
		else
			return true;
	}

	public void insert(D d) {
		if (isEmpty())
			insertEmpty(d);
		else {
			if (getData().compareTo(d) < 0)
				getRight().insert(d);
			else if (getData().compareTo(d) > 0) {
				getLeft().insert(d);
			}
			equilibrate();

		}
	}

	public void insertStatusStructureVariant(D d, double yp) {

		if (isEmpty()) { // && getFather() == null
			setData(d);
			setLeft(new AVLTree((AVLTree) this));
			setRight(new AVLTree((AVLTree) this));
			// getLeft().setFather(this);
			// getRight().setFather(this);
			// getLeft().insertEmpty(d);
			// } else if (isEmpty() && getFather() != null ){
			// setData(d);
			// setLeft(new AVLTree((AVLTree) this));
			// setRight(new AVLTree((AVLTree) this));
		} else {
			if (((Segment) getData()).xAtYp(yp) < ((Segment) d).xAtYp(yp)) {
				if (getRight().isEmpty()) {
					getLeft().insertEmpty(getData());
					getRight().insertEmpty(d);

					getLeft().setFather(this);
					getRight().setFather(this);
				} else {
					getRight().insertStatusStructureVariant(d, yp);
				}
			} else if (((Segment) getData()).xAtYp(yp) > ((Segment) d).xAtYp(yp)) {
				if (getLeft().isEmpty()) {
					getLeft().insertEmpty(d);
					getRight().insertEmpty(getData());
					setData(d);

					getLeft().setFather(this);
					getRight().setFather(this);

				} else {
					getLeft().insertStatusStructureVariant(d, yp);
				}
			} else if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp)) {
				if (!((Segment) getData()).isSameSegment((Segment) d)) {
					insertStatusStructureVariant(d, yp - 0.01);
				}
			}
		}
		equilibrate();
	}

	// On redefinit la methode insertEmpty de la classe Tree, afin de travailler
	// avec
	// le type BSTree au lieu de Tree
	public void insertEmpty(D d) {
		setData(d);
		setLeft(new AVLTree((AVLTree) this));
		setRight(new AVLTree((AVLTree) this));
	}

	public void setFather(BSTree father) {
		this.father = father;
	}

	public BSTree<D> getFather() {
		return this.father;
	}

	public void suppress(D d) {
		if (!isEmpty()) {
			if (getData().compareTo(d) < 0)
				getRight().suppress(d);
			else if (getData().compareTo(d) > 0)
				getLeft().suppress(d);
			else
				suppressRoot();
			equilibrate();
		}
	}

	// public void suppressStatusStructure(D d, double yp){
	// if (!isEmpty()){
	//// System.out.println("---------- STEPS ----------");
	//// print("", true);
	// if (((Segment) getData()).xAtYp(yp) < ((Segment)d).xAtYp(yp)){
	//// System.out.println("GOING RIGHT BECAUSE : " + ((Segment)
	// getData()).xAtYp(yp) + " < " + ((Segment) d).xAtYp(yp));
	//
	// getRight().suppressStatusStructure(d, yp);
	// }
	// else if (((Segment) getData()).xAtYp(yp) > ((Segment)d).xAtYp(yp)) {
	//// System.out.println("GOING LEFT BECAUSE : " + ((Segment)
	// getData()).xAtYp(yp) + " > " + ((Segment) d).xAtYp(yp));
	// getLeft().suppressStatusStructure(d, yp);
	//
	// }
	// else { // meme x pour yp!
	// if (!((Segment)getData()).isSameSegment((Segment)d)){
	// if (((Segment) getData()).getUpperEndPoint().getX() > ((Segment)
	// d).getUpperEndPoint().getX()){
	//// System.out.println("GOING RIGHT BECAUSE UPPER ENDPOINT X IS GREATER");
	// getLeft().suppressStatusStructure(d, yp);
	// } else {
	//// System.out.println("GOING LEFT BECAUSE UPPER ENDPOINT X IS SMALLER");
	// getRight().suppressStatusStructure(d, yp);
	// }
	//// getRight().suppressStatusStructure(d, yp+0.01);
	// } else {
	//
	// if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp) && height() >
	// 2 && getRight().height() >= 2) {
	//// System.out.println("HERE : " + d);
	//// print("**", true);
	// D data = getData();
	// suppressRoot();
	// D newData = getData();
	// getRight().suppressStatusStructure(newData, yp);
	//// System.out.println("------ BEFORE REPLACEMENT -------");
	//// System.out.println("SEGMENT FOR REPLACEMENT : " + newData);
	//// if (getFather() != null)
	//// getFather().print("", true);
	//
	// getLeft().replace(data, newData, yp);
	// equilibrate();
	//
	// } else if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp) &&
	// height() > 2 && getRight().height() < 2) {
	//
	// D data = getData();
	// suppressRoot();
	// getRight().suppressStatusStructure(data, yp);
	// equilibrate();
	// } else if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp) &&
	// height() == 2) {
	// if (!getRight().isEmpty() && getLeft().isEmpty()) {
	// suppressRoot();
	// } else if (getRight().isEmpty() && !getLeft().isEmpty()) {
	// suppressRoot();
	// suppressRoot();
	// } else {
	// suppressRoot();
	// getLeft().suppressRoot();
	// }
	// equilibrate();
	// } else if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp) &&
	// height() == 1) {
	// suppressRoot();
	// equilibrate();
	// }
	// }
	// }
	// equilibrate();
	// }
	// }

	// public void replace(D d, D replacement, double yp){
	//
	//
	// if (((Segment) getData()).xAtYp(yp) == ((Segment)d).xAtYp(yp)){
	// if (((Segment)getData()).isSameSegment((Segment)d)){
	// setData(replacement);
	// } else {
	// System.out.println("------ SITUATION ------");
	// print(":", true);
	// getRight().replace(d, replacement, yp);
	// }
	// } else if (((Segment) getData()).xAtYp(yp) < ((Segment)d).xAtYp(yp)){
	// System.out.println("------ SITUATION RIGHT ------");
	// print(":", true);
	// getRight().replace(d, replacement, yp);
	// } else if (((Segment) getData()).xAtYp(yp) > ((Segment)d).xAtYp(yp)){
	// System.out.println("------ SITUATION LEFT ------");
	// print(":", true);
	// System.out.println(((Segment) getData()).xAtYp(yp) + " node, data : " +
	// ((Segment) d).xAtYp(yp));
	// getLeft().replace(d, replacement, yp);
	// }
	// }

	public void replace(D d, D replacement, double yp) {
		if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp)) {
			if (((Segment) getData()).isSameSegment((Segment) d)) {
				setData(replacement);
			} else {
				// System.out.println("------ SITUATION ------");
				// print(":", true);
				getRight().replace(d, replacement, yp);
			}
		} else if (((Segment) getData()).xAtYp(yp) < ((Segment) replacement).xAtYp(yp)) {
			// System.out.println("------ SITUATION RIGHT ------");
			// print(":", true);
			// System.out.println(((Segment) getData()).xAtYp(yp) + " node, data : " +
			// ((Segment) d).xAtYp(yp));
			getRight().replace(d, replacement, yp);
		} else if (((Segment) getData()).xAtYp(yp) > ((Segment) replacement).xAtYp(yp)) {
			// System.out.println("------ SITUATION LEFT ------");
			// print(":", true);
			// System.out.println(((Segment) getData()).xAtYp(yp) + " node, data : " +
			// ((Segment) d).xAtYp(yp));
			getLeft().replace(d, replacement, yp);
		}
	}

	// suppression de la racine (on sait qu'elle existe)
	// rem: la methode equilibrate ne fait rien ici, mais sera utile pour la classe
	// AVLTree
	public void suppressRoot() {
		if (getLeft().isEmpty()) {
			BSTree<D> t = getRight();
			setData(t.getData());
			setLeft(t.getLeft());
			setRight(t.getRight());

		} else if (getRight().isEmpty()) {
			BSTree<D> t = getLeft();
			setData(t.getData());
			setRight(t.getRight());
			setLeft(t.getLeft());
			getRight().setFather(this);
			getLeft().setFather(this);
		} else
			setData(getRight().suppressMin());
		equilibrate();
	}

	// suppression du minimum et retour de ce minimum (on sait qu'il existe)
	// rem: la methode equilibrate ne fait rien ici, mais sera utile pour la classe
	// AVLTree
	public D suppressMin() {
		D min;
		if (getLeft().isEmpty()) {
			min = getData();
			BSTree<D> t = getRight();
			setData(t.getData());
			setLeft(t.getLeft());
			setRight(t.getRight());
		} else
			min = getLeft().suppressMin();
		equilibrate();
		return min;
	}

	public D suppressMax() {
		D max;
		if (getRight().isEmpty()) {
			max = getData();
			BSTree<D> t = getLeft();
			setData(t.getData());
			setRight(t.getRight());
			setLeft(t.getLeft());
		} else {
			max = getRight().suppressMax();
		}
		equilibrate();
		return max;
	}

	// equilibrage vide dans le cas des BSTree, sera defini pour les AVLTree
	public void equilibrate() {
	}

	// recherche recursive du minimum des donnees
	public D searchMin() {
		if (isEmpty())
			return null;
		else if (getLeft().isEmpty())
			return getData();
		else
			return getLeft().searchMin();
	}

	// recherche recursive du maximum des donnees
	public D searchMax() {
		if (isEmpty())
			return null;
		else if (getRight().isEmpty())
			return getData();
		else
			return getRight().searchMax();
	}

	// recherche recursive du successeur d'une donnee
	// appel a la methode succ avec le parametre auxiliaire x initialise a null
	public D searchSucc(D d) {
		return succ(d, null);
	}

	private D succ(D d, D x) {
		if (isEmpty())
			return null;
		else if (getData().compareTo(d) < 0)
			return getRight().succ(d, x);
		else if (getData().compareTo(d) > 0)
			return getLeft().succ(d, getData());
		else if (getRight().isEmpty())
			return x;
		else
			return getRight().searchMin();
	}

	public Boolean getIsStatus() {
		return isStatus;
	}
}