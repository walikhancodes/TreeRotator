
import java.util.ArrayList;
import java.util.Collections;

public class TreeSorter<K> {
    public static final int REMOVE_MIN = 0;
    public static final int GET_INORDER = 1;
    private TreeSet<K> set;
    private int removalType;

    public TreeSorter(TreeSet<K> set, int removalType) {
        this.set = set;
        this.removalType = removalType;
    }

    public ArrayList<K> sort(ArrayList<K> input) {
        set.insertList(input);
        switch (removalType) {
            case REMOVE_MIN:
                return priorityQueueSort();
            case GET_INORDER:
                return inOrderTraversal();
        }
        return null;
    }

    public ArrayList<K> priorityQueueSort() {
        // your implementation goes here
        ArrayList<K> answer = new ArrayList<>();
        while (!set.isEmpty()) {
            answer.add(set.removeMin());
        }
        return answer;
    }

    public ArrayList<K> inOrderTraversal() {
        // your implementation goes here
        ArrayList<K> answer = new ArrayList<>();
        answer = set.inOrder();
        return answer;
    }

    public static long test(ArrayList<Integer> list, TreeSet<Integer> tree){
        //** Used to track the time it takes */
        long startTime = System.currentTimeMillis();
        tree.insertList(list);
        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        return elapsed;
    }

    public static void main(String[] args) {

        //** Create the list with 2,000,0000 elements in shuffled order */
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 2000000; i++)
            list.add(i);
        Collections.shuffle(list);

        System.out.println();

         //Down below we are testing how long it takes to insert elements in to the SelfBalanacingTreeSet data structure
        //** Creating a new SelfBalancingTreeSet */
        SelfBalancingTreeSet<Integer> selfBalanceTree = new SelfBalancingTreeSet<>();
        System.out.println("Time it takes to insert elements in to SelfBalancingTreeSet: " + test(list, selfBalanceTree));
        System.out.println();


        //Down below we are testing how long it takes to insert elements randomly in to RandomizedTreeSet 
        //** Creating a new RandomizedTreeSet */
        RandomizedTreeSet<Integer> randomTree = new RandomizedTreeSet<>();
        System.out.println("Time it takes to insert elements in to RandomizedTreeSet: " + test(list, randomTree));
        System.out.println();


        //Down below we are testing how long it takes to extract elements from a TreeSet, which implements the PriorityQueue interface, 
        //by repeteadly calling the removeMin() method 
        TreeSet<Integer> tree = new TreeSet<>();
        TreeSorter<Integer> treeSorter = new TreeSorter<>(tree, 0);
        long startTime = System.currentTimeMillis();
        treeSorter.sort(list);
        long endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        System.out.println("Time for extracting elements from a BSTree implementing PQ interface: " + elapsed);
        System.out.println();

        //Down below we are testing how long it takes to extract elements from a TreeSet in inOrder traversal
        treeSorter = new TreeSorter<>(tree, 1);
        long startTime2 = System.currentTimeMillis();
        treeSorter.sort(list);
        long endTime2 = System.currentTimeMillis();
        long elapsed2 = endTime2 - startTime2;
        System.out.println("Time for extracting elements from a BSTree in inOrder traversal: " + elapsed2);
        System.out.println();

    }

}
