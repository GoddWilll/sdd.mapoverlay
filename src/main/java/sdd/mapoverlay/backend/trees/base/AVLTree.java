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
		setRight(t);
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
				System.out.println("-------- SEARCHING --------");
				print("", true);
				if (startingLeaf.getFather().getRight() == startingLeaf){ // on part d'un fils droit
					AVLTree<D> currentTree = startingLeaf; // on va dans le pere vu qu'on est un fils droit
					System.out.println("CURRENT TREE : " + currentTree.getData());
					currentTree = currentTree.getFather().getLeft();

					System.out.println("NEW CURRENT TREE : " + currentTree.getData());
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

	@Override
	public void insertStatusStructureVariant(D d, double yp){

		if (isEmpty()) {
			setData(d);
			setLeft(new AVLTree( this));
			setRight(new AVLTree( this));
			equilibrate();
		} else {
			if (((Segment) getData()).xAtYp(yp) < ((Segment)d).xAtYp(yp)) {
				System.out.println("Data : " + getData() + " x : " + ((Segment) getData()).xAtYp(yp) + " < d : " + d + " x : " + ((Segment) d).xAtYp(yp) + " at yp : " + yp);
				if (getRight().isEmpty()){
					getLeft().insertEmpty(getData());
					getRight().insertEmpty(d);

					getLeft().setFather(this);
					getRight().setFather(this);
					equilibrate();
				} else {
					getRight().insertStatusStructureVariant(d, yp);
				}
			} else if (((Segment) getData()).xAtYp(yp) > ((Segment)d).xAtYp(yp)) {
				System.out.println("Data : " + getData() + " x : " + ((Segment) getData()).xAtYp(yp)+ " > d : " + d + " x : " + ((Segment) d).xAtYp(yp)+ " at yp : " + yp);
				if (getLeft().isEmpty()) {
					getLeft().insertEmpty(d);
					getRight().insertEmpty(getData());
					setData(d);

					getLeft().setFather(this);
					getRight().setFather(this);
					equilibrate();

				} else {
					getLeft().insertStatusStructureVariant(d, yp);
				}
			} else if (((Segment) getData()).xAtYp(yp) == ((Segment)d).xAtYp(yp)){
//				System.out.println("Data : " + getData() + " x : " + ((Segment) getData()).xAtYp(yp)+ " = d : " + d + " x : " + ((Segment) d).xAtYp(yp)+ " at yp : " + yp);
				if (!((Segment)getData()).isSameSegment((Segment)d)){
					 insertStatusStructureVariant(d, yp - 0.01);
				}
			}
		}
		equilibrate();
	}



//	@Override
	public void suppressStatusStructure(D d, double yp){
		if (!isEmpty()){
			System.out.println("---------- STEPS ----------");
			print("", true);
			if (((Segment) getData()).xAtYp(yp) < ((Segment)d).xAtYp(yp)){
				System.out.println("Data : " + getData() + " x : " + ((Segment) getData()).xAtYp(yp) + " < d : " + d + " x : " + ((Segment) d).xAtYp(yp) + " at yp : " + yp);
				getRight().suppressStatusStructure(d, yp);
			}
			else if (((Segment) getData()).xAtYp(yp) > ((Segment)d).xAtYp(yp)) {
				System.out.println("Data : " + getData() + " x : " + ((Segment) getData()).xAtYp(yp) + " > d : " + d + " x : " + ((Segment) d).xAtYp(yp) + " at yp : " + yp);
				getLeft().suppressStatusStructure(d, yp);
			}
			else { // meme x pour yp!
				if (!((Segment)getData()).isSameSegment((Segment)d)){
					System.out.println("Same x for yp but not same segment");
					System.out.println("Data : " + getData() + " x : " + ((Segment) getData()).xAtYp(yp) + " = d : " + d + " x : " + ((Segment) d).xAtYp(yp) + " at yp : " + yp);
//					if (((Segment) getData()).getUpperEndPoint().getXCoords() > ((Segment) d).getUpperEndPoint().getXCoords()){
//						getLeft().suppressStatusStructure(d, yp);
//					} else {
//						getRight().suppressStatusStructure(d, yp);
//					}
					if (((Segment) getData()).xAtYp(yp+0.01) > ((Segment)d).xAtYp(yp+0.01)){
						getLeft().suppressStatusStructure(d, yp);
					} else {
						getRight().suppressStatusStructure(d, yp);
					}
				} else {
					if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp) && height() > 2 && getRight().height() >= 2) {
						D data = getData();
						suppressRoot();
						D newData = getData();
						getRight().suppressStatusStructure(newData, yp);
						System.out.println("------ BEFORE REPLACEMENT -------");
						System.out.println("SEGMENT FOR REPLACEMENT : " + newData);
						if (getFather() != null)
							getFather().print("", 	true);

						getLeft().replace(data, newData, yp);
						equilibrate();

					} else if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp) && height() > 2 && getRight().height() < 2) {

						D data = getData();
						suppressRoot();
						getRight().suppressStatusStructure(data, yp);
						equilibrate();
					} else if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp) && height() == 2) {
						if (!getRight().isEmpty() && getLeft().isEmpty()) {
							suppressRoot();
						} else if (getRight().isEmpty() && !getLeft().isEmpty()) {
							getLeft().suppressRoot();
							if (getFather() != null){
								computeHeight();
								getFather().equilibrate();
							}
							suppressRoot();

						} else {
							suppressRoot();
							getLeft().suppressRoot();
						}
						System.out.println("BALANCE : " + balance());
					} else if (((Segment) getData()).xAtYp(yp) == ((Segment) d).xAtYp(yp) && height() == 1) {
						suppressRoot();
						equilibrate();
					}
				}
			}
			System.out.println("TOTAL BALANCE : " + balance());
			equilibrate();
		}
	}

	/**
	 * Fonction permettant de remplacer un segment par un autre, lors d'une suppression. On remplace le segment le plus a droite du sous arbre gauche par la nouvelle valeur de la racine.
	 * @param d Le segment a remplacer
	 * @param replacement Le segment de remplacement
	 * @param yp La coordonnee y de la sweep line
	 */
	@Override
	public void replace(D d, D replacement, double yp){
		System.out.println("data : " + getData() + " tobereplaced : " + d + " yp : " + yp);
 		if (((Segment) getData()).xAtYp(yp) == ((Segment)d).xAtYp(yp)){
//			System.out.println("data : " + getData() + " d : " + d + " replacement : " + replacement + " yp : " + yp);
			if (((Segment)getData()).isSameSegment((Segment)d)){
				setData(replacement);
			} else {
				getRight().replace(d, replacement, yp);
			}
		} else if (((Segment) getData()).xAtYp(yp) < ((Segment)replacement).xAtYp(yp)){
			System.out.println(((Segment) getData()).xAtYp(yp) + " < " + ((Segment)replacement).xAtYp(yp));
			getRight().replace(d, replacement, yp);
		} else if (((Segment) getData()).xAtYp(yp) > ((Segment)replacement).xAtYp(yp)){
			System.out.println(((Segment) getData()).xAtYp(yp) + " > " + ((Segment)replacement).xAtYp(yp));
			getLeft().replace(d, replacement, yp);
		}
	}


}