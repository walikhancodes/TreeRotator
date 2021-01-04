
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class TreeRotator {
  public static void main(String[] args) {
    // an example of two randomly generated BSTs on the same set of keys
    TreeSet<Integer> ts1 = new TreeSet<>();
    TreeSet<Integer> ts2 = new TreeSet<>();
    ArrayList<Integer> list = new ArrayList<>();
    for (int i = 0; i < 6; i++)
      list.add(i);
    Collections.shuffle(list);
    for (Integer in : list)
      ts1.put(in);
    LinkedBinaryTree<Integer> tree1 = (LinkedBinaryTree<Integer>) ts1.getTree();
    Collections.shuffle(list);
    for (Integer in : list)
      ts2.put(in);
    LinkedBinaryTree<Integer> tree2 = (LinkedBinaryTree<Integer>) ts2.getTree();

    System.out.println("this is tree1: ");
    System.out.println(tree1);
    System.out.println("this is tree2: ");
    System.out.println(tree2);
    printRotations(tree1, tree2);

  }

  private static Iterable<Position<Integer>> children(Position<Integer> p, LinkedBinaryTree<Integer> tree) {
    ArrayList<Position<Integer>> snapshot = new ArrayList<>(2);
    if (tree.left(p).getElement() != null) {
      snapshot.add(tree.left(p));
    }
    if (tree.right(p).getElement() != null) {
      snapshot.add(tree.right(p));
    }
    return snapshot;
  }

  private static void preorderSubTree(Position<Integer> p, LinkedBinaryTree<Integer> tree, ArrayList<Integer> x) {
    if (p.getElement() != null)
      x.add(p.getElement());
    for (Position<Integer> c : children(p, tree)) {
      preorderSubTree(c, tree, x);
    }

  }

  private static void preorderSubTreePositions(Position<Integer> p, LinkedBinaryTree<Integer> tree,
    ArrayList<Position<Integer>> x) {
    if (p.getElement() != null)
      x.add(p);
    for (Position<Integer> c : children(p, tree)) {
      preorderSubTreePositions(c, tree, x);
    }

  }

  private static ArrayList<Position<Integer>> preOrderPositions(Position<Integer> p, LinkedBinaryTree<Integer> tree) {
    ArrayList<Position<Integer>> answer = new ArrayList<>();
    preorderSubTreePositions(p, tree, answer);
    return answer;
  }

  private static ArrayList<Integer> preOrder(Position<Integer> p, LinkedBinaryTree<Integer> tree) {
    ArrayList<Integer> answer = new ArrayList<>();
    preorderSubTree(p, tree, answer);
    return answer;
  }

  public static void printRotations(LinkedBinaryTree<Integer> bt1, LinkedBinaryTree<Integer> bt2) {
    // start with shallow copies of each of the trees
    Stack<Integer> rotations = new Stack<>();
    LinkedBinaryTree<Integer> copy1 = new LinkedBinaryTree<>(bt1);
    LinkedBinaryTree<Integer> copy2 = new LinkedBinaryTree<>(bt2);
    // ArrayLists of the elements in both trees in preOrder
    ArrayList<Integer> cp1 = preOrder(copy1.root(), copy1);
    ArrayList<Integer> cp2 = preOrder(copy2.root(), copy2);
    // ArrayLis of the Positions of just tree1
    ArrayList<Position<Integer>> pos1 = preOrderPositions(copy1.root(), copy1);
    int i = 0;// int used to access the next element of the preOrder list of both trees
    while (!cp1.equals(cp2)) { // while the two preOrder lists of the two trees are not equal
      while (cp1.get(i) != cp2.get(i)) { 
        int index = cp1.indexOf(cp2.get(i)); // locate value in cp1
        Position<Integer> rotateThis = pos1.get(index); // locate the Position needed to rotate
        copy1.rotate(rotateThis); // keep rotating until its get index i in the preOrder list of tree1
        rotations.push(rotateThis.getElement()); // keep track of rotations
        cp1 = preOrder(copy1.root(), copy1); // update the list holding the preOrder of elements of tree1
        pos1 = preOrderPositions(copy1.root(), copy1);// update the list holding the preOrder of positions of tree1
      }
      i++; // go to next element in the preOrder for both trees
    }
    System.out.println("This is tree1 transformed into tree2");
    System.out.println("copy1: ");
    System.out.println();
    System.out.println(copy1);
    System.out.println("copy2: ");
    System.out.println();
    System.out.println(copy2);
    System.out.println("This is the sequence of rotations:");
    while (!rotations.isEmpty()) {
      System.out.println(rotations.pop());
    }
  }

}
