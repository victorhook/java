package bst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.StringJoiner;


public class BinarySearchTree<E> {
  BinaryNode<E> root;  // Används också i BSTVisaulizer
  int size;            // Används också i BSTVisaulizer
  private Comparator<E> comparator;
    
	/**
	 * Constructs an empty binary search tree.
	 */
	public BinarySearchTree() {
		this.size = 0;
		root = null;
	}
	
	/**
	 * Constructs an empty binary search tree, sorted according to the specified comparator.
	 */
	public BinarySearchTree(Comparator<E> comparator) {
		this.comparator = comparator;
		this.size = 0;
		root = null;
	}

	/**
	 * Inserts the specified element in the tree if no duplicate exists.
	 * @param x element to be inserted
	 * @return true if the the element was inserted
	 */
	public boolean add(E x) {
		if (nodeExists(root, x))
			return false;

		if (root == null) {
			root = new BinaryNode<>(x);
		} else {
			insert(root, x);
		}

		this.size++;
		return true;
	}

	public BinaryNode<E> insert(BinaryNode<E> node, E data) {
		if (node == null) {
			return new BinaryNode<E>(data);
		}

		int res = compare(node, data);
		if (res < 0) {
			node.left = insert(node.left, data);
		} else if (res > 0) {
			node.right = insert(node.right, data);
		}
		return node;
	}

	private boolean nodeExists(BinaryNode<E> node, E data) {
		if (node == null)
			return false;
		if (node.element.equals(data))
			return true;

		int res = compare(node, data);
		boolean leftTree = nodeExists(node.left, data);
		if (leftTree)
			return true;
		return nodeExists(node.right, data);
	}

	/**
	 * Compares the node with the given data, using either the comparator attribute,
	 * or assuming that the E type has implemented the Comparable interface.
	 * @param node
	 * @param data
	 * @return difference
	 */
	private int compare(BinaryNode<E> node, E data) {
		if (comparator != null) {
			return comparator.compare(data, node.element);
		}
		return ((Comparable) data).compareTo(node.element);
	}

	/**
	 * Computes the height of tree.
	 * @return the height of the tree
	 */
	public int height() {
		if (root == null) return 0;
		return Math.max(maxHeight(root.left), maxHeight(root.right)) + 1;
	}


	private int maxHeight(BinaryNode<E> node) {
		if (node == null) return 0;

		int leftHeight = maxHeight(node.left);
		int rightHeight = maxHeight(node.right);
		return (leftHeight > rightHeight) ? leftHeight + 1 : rightHeight + 1;
	}



	/**
	 * Returns the number of elements in this tree.
	 * @return the number of elements in this tree
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Removes all of the elements from this list.
	 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 * Print tree contents in inorder.
	 */
	public void printTree() {
		StringJoiner sj = new StringJoiner(", ");
		print(root, sj);
		System.out.println(sj.toString());
	}

	private void print(BinaryNode<E> node, StringJoiner sj) {
		if (node == null) {
			return;
		}
		print(node.left, sj);
		sj.add(node.element.toString());
		print(node.right, sj);
	}

	/** 
	 * Builds a complete tree from the elements in the tree.
	 */
	public void rebuild() {
		ArrayList<E> sorted = new ArrayList<>();
		toArray(root, sorted);
		root = buildTree(sorted, 0, sorted.size()-1);
	}

	public static void main(String[] args) {
		animate();

	}

	private static void animate() {
		Random rand = new Random();
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		BSTVisualizer visualizer = new BSTVisualizer("Binary Tree", 300, 300);

		final int DELAY = 3000;
		final int NODES = 20;

		new Runnable() {
			public void run() {
				try {
					while (true) {
						tree.clear();
						for (int i = 0; i < 20; i++) {
							tree.add(i);
						}
						visualizer.drawTree(tree);
						Thread.sleep(DELAY);

						tree.rebuild();
						visualizer.drawTree(tree);
						Thread.sleep(DELAY);

						tree.clear();
						for (int i = 0; i < 40; i++) {
							tree.add(rand.nextInt(100) << 4);
						}
						visualizer.drawTree(tree);
						Thread.sleep(DELAY);

						tree.rebuild();
						visualizer.drawTree(tree);
						Thread.sleep(DELAY);
					}
				} catch (Exception e) {}
			}
		}.run();



	}


	/*
	 * Adds all elements from the tree rooted at n in inorder to the list sorted.
	 */
	private void toArray(BinaryNode<E> n, ArrayList<E> sorted) {
		if (n == null)
			return;

		toArray(n.left, sorted);
		sorted.add(n.element);
		toArray(n.right, sorted);
	}
	
	/*
	 * Builds a complete tree from the elements from position first to 
	 * last in the list sorted.
	 * Elements in the list a are assumed to be in ascending order.
	 * Returns the root of tree.
	 */
	private BinaryNode<E> buildTree(ArrayList<E> sorted, int first, int last) {
		// When first index exceeds last, we're out of bounds => no values
		if (first > last) {
			return null;
		}

		// Get middle index, starting from "first".
		int middle = first + (last - first) / 2;
		BinaryNode<E> middleNode = new BinaryNode<>(sorted.get(middle));

		// If start = end, we've reached a lonely node (it has no children).
		if (first == last)
			return middleNode;

		// Recursively build left and right tree of the current node.
		middleNode.left = buildTree(sorted, first, middle-1);
		middleNode.right = buildTree(sorted, middle+1, last);
		return middleNode;
	}
	


	static class BinaryNode<E> {
		E element;
		BinaryNode<E> left;
		BinaryNode<E> right;

		private BinaryNode(E element) {
			this.element = element;
		}

		public String toString() {
			return element.toString();
		}

	}
	
}
