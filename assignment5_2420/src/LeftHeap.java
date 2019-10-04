/**
 * This is a max priority que using a leftist heap.
 * @param <G> a generic data type which is comparable.
 * @author Scout Jarman
 */
public class LeftHeap<G extends Comparable> {
    /**
     * Variable which hold the root node, and a name.
     */
    private Node<G> root;
    private String name;

    /**
     * Default constructor, adds the title.
     * @param title what ever you want to call the tree.
     */
    LeftHeap(String title) {
        this.name = title;
    }

    /**
     * Deletes the max value of the tree, which is the root, then merges the children.
     * @return the node of the max value.
     */
    Node<G> deleteMax() {
        Node<G> max = this.root;
        this.root = merge(this.root.left, this.root.right);
        return max;
    }

    /**
     * Checks to see if the tree is empty.
     * @return true if the root is null.
     */
    boolean isNotEmpty() {
        return root != null;
    }

    /**
     * An override that will print the tree prettily.
     * @return a string of the pretty tree.
     */
    @Override
    public String toString() {
        return prettyTree(this.root);
    }

    /**
     * Wrapper function to make a pretty string, and handles empty trees.
     * @param node the node to start printing, which is the root.
     * @return the string of the tree.
     */
    private String prettyTree(Node<G> node) {
        if(node == null) {
            return "\nEmpty Tree\n";
        }
        return "\n" + this.name + "\n\n" + prettyTree(node, 0) + "\n";
    }

    /**
     * Recursive function to print out the tree nicely.
     * @param node is the current node in the tree.
     * @param height how far down you have gone (for indenting).
     * @return the string of the tree.
     */
    private String prettyTree(Node<G> node, int height) {
        if(node == null) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        str.append(prettyTree(node.right, height + 1));
        for(int i = 0; i < height; i++){
            str.append("\t");
        }
        str.append(node);
        str.append("\n");
        str.append(prettyTree(node.left, height + 1));
        return str.toString();
    }

    /**
     * Takes an element G and inserts it into the tree (by merging).
     * @param element that you want to add.
     */
    void insert(G element) {
        Node<G> newSingleTree = new Node<>(element);
        this.root = merge(this.root, newSingleTree);
    }

    /**
     * This is used to merge the current tree, with the given tree.
     * @param tree2 is the tree that you want merge to your self.
     */
    void merge(LeftHeap<G> tree2) {
        this.root = merge(this.root, tree2.root);
    }

    /**
     * Function which will merge two trees together recursively, and makes it a max priority que.
     * @param tree1 is the first tree/node.
     * @param tree2 is the other tree/node.
     * @return the new root of the tree.
     */
    private Node<G> merge(Node<G> tree1, Node<G> tree2) {
        Node<G> big;
        // Base cases, ones you have reached the end of the trees
        if(tree1 == null) {
            return tree2;
        }
        if(tree2 == null) {
            return tree1;
        }

        if(tree1.element.compareTo(tree2.element) > 0) {
            tree1.right = merge(tree1.right, tree2);
            big = tree1;
        }
        else {
            tree2.right = merge(tree2.right, tree1);
            big = tree2;
        }
        if(notLeftist(big)) {
            swapKids(big);
        }
        setNullPathLength(big);
        return big;

    }

    /**
     * To check if a tree is not leftist.
     * @param parent is the node you want to check for leftism.
     * @return whether it is leftist or not.
     */
    private boolean notLeftist(Node<G> parent) {
        return (getNullPathLength(parent.left) < getNullPathLength(parent.right));
    }

    /**
     * Gets the NPL for a given node, handles null nodes.
     * @param node you want to get the NPL of.
     * @return it's NPL, -1 if null.
     */
    private int getNullPathLength(Node<G> node) {
        if(node == null) {
            return -1;
        }
        return node.nullPathLength;
    }

    /**
     * Takes a node, and makes sure its NPL is correct.
     * @param node that you want to fix its NPL.
     */
    private void setNullPathLength(Node<G> node) {
        if(node == null) {
            return;
        }
        node.nullPathLength = Math.min(getNullPathLength(node.left), getNullPathLength(node.right)) + 1;
    }

    /**
     * Will swap the kids of a given node.
     * @param node that you want to have its kids swapped.
     */
    private void swapKids(Node<G> node) {
        Node<G> oldLeft = node.left;
        node.left = node.right;
        node.right = oldLeft;
    }

    /**
     * The class for nodes in the leftist heap, has element, left, right, and its null path length.
     * @param <AnyType> is the generic type of your tree.
     */
    private static class Node<AnyType> {
        /**
         * Variables which store the element, left and right children, and its NPL.
         */
        AnyType element;
        Node<AnyType> left;
        Node<AnyType> right;
        int nullPathLength;

        /**
         * Default constructor of a node.
         * @param element that you want the node to have.
         */
        Node(AnyType element) {
            this(element, null, null);
        }

        /**
         * Sets the values for the new node.
         * @param element that you want.
         * @param left child of node.
         * @param right child of node.
         */
        Node(AnyType element, Node<AnyType> left, Node<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.nullPathLength = 0;
        }

        /**
         * Prints the element and NPL of the node.
         * @return the string of element and NPL.
         */
        @Override
        public String toString() {
            return String.valueOf(element) /*+ " [ " + nullPathLength + " ] "*/;
        }
    }

//    /**
//     * Test function to show that the max priority que is working.
//     * @param args is nothing.
//     */
//    public static void oldMain(String[] args) {
//        System.out.println("test");
//
//        System.out.println("making tree with values 10, 5, 15, 1, 16, 18, 5, 18");
//        int[] nums = {10, 5, 15, 1, 16, 18, 5, 18};
//        LeftHeap<Integer> test = new LeftHeap<>("tree 1");
//        for (int num : nums) {
//            System.out.println("adding a number\n");
//            test.insert(num);
//            System.out.println(test);
//        }
//
//        System.out.println("Creating a new tree using 132, 52, 01, 24, 13, 71, 0");
//        int[] nums2 = {132, 52, 1, 24, 13, 71, 0};
//        LeftHeap<Integer> test2 = new LeftHeap<>("tree 2");
//        for (int num : nums2) {
//            System.out.println("adding a number\n");
//            test2.insert(num);
//            System.out.println(test2);
//        }
//
//        System.out.println("Merging tree 2 into tree 1");
//        test.merge(test2);
//        System.out.println(test);
//
//        System.out.println("Removing the largest value a couple of times");
//        int[] rem = new int[5];
//        for(int i = 0; i < rem.length; i++){
//            rem[i] = test.deleteMax().element;
//            System.out.println("\nremoved:" + rem[i] + "\n");
//            System.out.println(test);
//        }
//
//        System.out.println("Now I will empty the tree.");
//        while(test.isNotEmpty()){
//            System.out.println("deleted:" + test.deleteMax().element);
//            System.out.println(test);
//        }
//
//    }
}
