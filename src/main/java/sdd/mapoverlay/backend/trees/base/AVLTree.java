package sdd.mapoverlay.backend.trees.base;//Classe AVLTree décrivant un arbre AVL
//Elle étend la classe BSTree décrivant les arbres binaires de recherche
//Les doublons ne sont pas autorisés

import org.w3c.dom.events.Event;
import sdd.mapoverlay.backend.Logic;
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

	//On redéfinit la méthode insertEmpty de la classe BSTree, afin de travailler avec
//le type AVLTree au lieu de BSTree

	/**
	 * Insertion d'une donnée dans un arbre vide
	 * @param d la donnée à insérer
	 */
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

	/**
	 * Fonction permettant de recuperer les voisins droits contenant le point p
	 * @param p EventPoint le point dont on veut les voisins
	 * @return ArrayList<D> une liste de segments voisins
	 */
	public ArrayList<D> getRightNeighbors(EventPoint p){
		ArrayList<D> neighbors = new ArrayList<>();
		if (getFather() == null){
			return neighbors;
		} else {
			AVLTree<D> tree = this; // on part de la feuille dans laquelle on est arrive au depart
			while (true){
				if (tree.getFather().getLeft() == tree) { // on est dans un fils gauche
					tree = tree.getFather().getRight(); // on se place dans le frere droit

					while (!tree.getLeft().isEmpty()){
						tree = tree.getLeft();
					}

					if (((Segment) tree.getData()).containsPoint(p)) { // si le frere droit est vide, alors il n'y a pas de voisins
						neighbors.add(tree.getData());
					} else {
						return neighbors;
					}
				} else if (tree.getFather().getRight() == tree){ // on est dans un fils droit
					if (tree.getFather().getFather() == null){
						return neighbors;
					} else {
						while (tree.getFather() != null && tree.getFather().getRight() == tree){
							tree = tree.getFather();
						}

						if (tree.getFather() == null){
							return neighbors;
						}

						// On est dans le fils gauche du pere, on passe dans le fils droit
						tree = tree.getFather().getRight();

						// Tant qu'un fils gauche existe, on va a gauche
						while (!tree.getLeft().isEmpty()){
							tree = tree.getLeft();
						}

						if (((Segment) tree.getData()).containsPoint(p)){
							neighbors.add(tree.getData());
						} else {
							return neighbors;
						}

					}
				}
			}

		}
	}


	/**
	 * Fonction permettant de recuperer les voisins gauches contenant le point p
	 * @param p EventPoint le point dont on veut les voisins
	 * @return ArrayList<D> une liste de segments voisins
	 */
	public ArrayList<D> getLeftNeighbors(EventPoint p){
		ArrayList<D> neighbors = new ArrayList<>();
		if (getFather() == null){
			return neighbors;
		} else {
			AVLTree<D> tree = this; // on part de la feuille dans laquelle on est arrive au depart
			while (true){
				if (tree.getFather().getRight() == tree) { // on est dans un fils droit
					tree = tree.getFather().getLeft(); // on se place dans le frere gauche
					while (!tree.getRight().isEmpty()){
						tree = tree.getRight();
					}
					if (((Segment) tree.getData()).containsPoint(p)) { // si le frere droit est vide, alors il n'y a pas de voisins
						neighbors.add(tree.getData());
					} else {
						return neighbors;
					}
				} else if (tree.getFather().getLeft() == tree){ // on est dans un fils droit
					if (tree.getFather().getFather() == null){
						return neighbors;
					} else {
						while (tree.getFather() != null && tree.getFather().getLeft() == tree){
							tree = tree.getFather();
						}
						if (tree.getFather() == null)
							return neighbors;

						// On est dans le fils gauche du pere, on passe dans le fils droit
						tree = tree.getFather().getLeft();

						// Tant qu'on a un fils gauche non vide, on descend a gauche
						while (!tree.getRight().isEmpty()){
							tree = tree.getRight();
						}

						if (((Segment) tree.getData()).containsPoint(p)){
							neighbors.add(tree.getData());
						} else {
							return neighbors;
						}

					}
				}
			}

		}
	}



	@Override
	public String toString(){
		return "" + getData();
	}

	/**
	 * Fonction permettant d'insérer un segment dans la StatusStructure
	 * @param d Segment le segment a inserer
	 * @param p EventPoint le point d'intersection
	 */
	public void insertStatusStructureVariant(D d, EventPoint p){
		if (isEmpty()) {
			insertEmpty(d);
		} else {
			if (height() == 1){
				if (Math.abs(((Segment)getData()).xAtYp(p) - ((Segment)d).xAtYp(p)) < Logic.EPSILON){
					getLeft().insertEmpty(getData());
					getRight().insertEmpty(d);

					getLeft().setFather(this);
					getRight().setFather(this);

				} else if (((Segment)getData()).xAtYp(p) > ((Segment)d).xAtYp(p)){
					getLeft().insertEmpty(d);
					getRight().insertEmpty(getData());
					setData(d);

					getLeft().setFather(this);
					getRight().setFather(this);

				} else if (((Segment)getData()).xAtYp(p) < ((Segment)d).xAtYp(p)){
					getLeft().insertEmpty(getData());
					getRight().insertEmpty(d);

					getLeft().setFather(this);
					getRight().setFather(this);
				}
			} else {
				if (Math.abs(((Segment)getData()).xAtYp(p) - ((Segment)d).xAtYp(p)) < Logic.EPSILON){
					getRight().insertStatusStructureVariant(d, p);
				} else if (((Segment)getData()).xAtYp(p) < ((Segment)d).xAtYp(p)){
					getRight().insertStatusStructureVariant(d, p);
				} else if (((Segment)getData()).xAtYp(p) > ((Segment)d).xAtYp(p)){
					getLeft().insertStatusStructureVariant(d, p);
				}
			}
		}
		equilibrate();
	}

	/**
	 * Fonction permettant de supprimer un segment de la structure de status
	 * @param d Segment le segment a supprimer
	 * @param p EventPoint le point d'intersection
	 */
	public void suppressStatusStructure(D d, EventPoint p){
		if (!isEmpty()){
			if (Math.abs(((Segment) getData()).xAtYp(p) - ((Segment) d).xAtYp(p)) < Logic.EPSILON){
				if (!((Segment)getData()).isSameSegment((Segment)d)){
					getLeft().suppressStatusStructure(d, p);
				} else {
					if (height() == 1){ // Si on est dans un arbre de hauteur 1 (donc une feuille)
						if (getFather() == null){ // Si on est la racine
							suppressRoot();
						} else {
							if (getFather().getLeft().height() == 1){ // Si le frere gauche de la feuille est de hauteur 1 (donc une feuille)
								getFather().getLeft().suppressRoot(); // On supprime la feuille gauche
								getFather().getRight().suppressRoot(); // On supprime la feuille droite
							} else {
								suppressRoot();
								getFather().suppressRoot();
							}
						}
					} else if (height() == 2){ // Si on est dans un arbre de hauteur 2
						if (getRight().isEmpty()){ // Si le fils droit est vide
							suppressRoot(); // On supprime la racine
							suppressRoot(); // On supprime la racine
						} else {
							suppressRoot(); // On supprime la racine
							getLeft().suppressRoot(); // On supprime le fils gauche
						}
					} else if (height() > 2){
						suppressRoot();
						D newData = getData();
						getRight().suppressMin();
						getLeft().replace(newData);
					}
				}
			} else if (((Segment) getData()).xAtYp(p) < ((Segment)d).xAtYp(p)){
				getRight().suppressStatusStructure(d, p);
			}
			else if (((Segment) getData()).xAtYp(p) > ((Segment)d).xAtYp(p)) {
				getLeft().suppressStatusStructure(d, p);
			}
			equilibrate();
		} else {
			System.out.println("Segment non trouve");
		}
	}

	/**
	 * Fonction permettant de supprimer la racine d'un arbre
	 */
	@Override
	public void suppressRoot() {
		if (getLeft().isEmpty()) {
			AVLTree<D> t = getRight();
			setData(t.getData());
			setLeft(t.getLeft());
			setRight(t.getRight());

		}
		else if (getRight().isEmpty()) {
			AVLTree<D> t = getLeft();
			setData(t.getData());
			setRight(t.getRight());
			setLeft(t.getLeft());
			getRight().setFather(this);
			getLeft().setFather(this);
		}
		else
			setData(getRight().suppressMin());
		equilibrate();
	}


	/**
	 * Fonction permettant de remplacer un segment par un autre, lors d'une suppression. On remplace le segment le plus a droite du sous arbre gauche par la nouvelle valeur de la racine.
	 * @param replacement Le segment de remplacement
	 */
	public void replace(D replacement){
		if (getRight().getData() != null){
			getRight().replace(replacement);
		} else {
			setData(replacement);
		}
	}

	/**
	 * Permet de faire une recherche dans la structure de donnée
	 * @param d la donnée a rechercher
	 * @return boolean true si la donnee est trouvee, false sinon
	 */
	@Override
	public boolean search(D d) {
		if (isEmpty()) {
			return false;
		} else if (Math.abs(((EventPoint)getData()).getY() - ((EventPoint)d).getY()) < Logic.EPSILON && Math.abs(((EventPoint)getData()).getX() - ((EventPoint)d).getX()) < Logic.EPSILON){
			return true;
		} else if (((EventPoint)getData()).getY() < ((EventPoint)d).getY()){
			return getRight().search(d);
		} else {
			return getLeft().search(d);
		}
	}


}