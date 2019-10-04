/**
 * Class for union find. Uses path compression and smart union (by size).
 * @author Scout Jarman
 */
class UnionFind {
    /**
     * Variables that store all the nodes, title,
     * and size (which is a little redundant, but makes code clear)
     */
    private int[][] array;
    private String title;
    private int size;

    /**
     * Constructor which makes the array the correct size, 2d array, [0] for parent, [1] for size.
     * Initializes all parents to self, and all sizes as 1.
     * @param size of array/nodes to group and stuff.
     * @param title of the object, just for printing fun.
     */
    UnionFind(int size, String title) {
        this.array = new int[size][2];
        this.title = title;
        this.size = size;

        for(int i = 0; i < size; i++) {
            this.array[i][0] = i;
            this.array[i][1] = 1;
        }
    }

    /**
     * Union, first finds each node, then returns the new parent after union.
     * @param first node to union.
     * @param second node to union.
     * @return the new parent after the union.
     */
    int union(int first, int second) {
        if(first >= 0 && first < this.size && second >= 0 && second < this.size) {
            int root1 = find(first);
            int root2 = find(second);
            return union2(root1, root2);
        }
        return -1;
    }

    /**
     * Private union, by size, if equal points 2 to 1.
     * NOTE: does not update sizes for any nodes, except for the new parent node.
     * @param root1 is the first node.
     * @param root2 is the second node.
     * @return the new parent of the union (the winner).
     */
    private int union2(int root1, int root2){
        if(this.array[root1][1] >= this.array[root2][1]) {
            this.array[root1][1] += this.array[root2][1];
            this.array[root2][0] = root1;
            return root1;
        }
        this.array[root2][1] += this.array[root1][1];
        this.array[root1][0] = root2;
        return root2;
    }

    /**
     * Find with path compression, checks if node is within bounds of array.
     * @param node that you want to find its group.
     * @return the nodes group/parent.
     */
    int find(int node) {
        if(node >= 0 && node < this.size) {
            return find2(node);
        }
        return -1;
    }

    /**
     * Recursive (for path compression) find, updates parents when coming out of recursion.
     * @param node that you want to find's group.
     * @return the parent after recursive call, ends when parent == node (points to its self)
     */
    private int find2(int node) {
        int parent = this.array[node][0];
        if(parent == node) {
            return parent;
        }
        parent = find2(parent);
        this.array[node][0] = parent;
        return parent;
    }

    /**
     * Just a print function that prints the contents of the array.
     * It out puts like, node P = parent S = size.
     */
    private void print() {
        System.out.println("\n" + this.title + "\n");

        for(int i = 0; i < this.array.length; i++){
            System.out.println(i + " P = " + this.array[i][0] + " S = " + this.array[i][1]);
        }

        System.out.println("\n");
    }

    /**
     * The old main function, which hopefully shows the functionality of the union find.
     */
    void oldMain() {
        // Initialize size of 30
        UnionFind test = new UnionFind(30,  "Demo");
        System.out.println(test.array.length);
        test.print();
        // Shows that find works on easy case
        System.out.println(test.find(0));
        test.print();
        // Shows union works on easy case
        System.out.println(test.union(0, 1));
        test.print();
        System.out.println(test.union(0, 3));
        test.print();
        System.out.println(test.union(1, 2));
        test.print();
        // Making new group
        System.out.println(test.union(5, 6));
        test.print();
        System.out.println(test.union(7, 6));
        test.print();
        System.out.println(test.union(5, 8));
        test.print();
        System.out.println(test.union(9, 7));
        test.print();
        System.out.println(test.union(10, 9));
        test.print();
        // Shows that smart union works
        System.out.println(test.union(10, 2));
        test.print();
        // Shows that compression works
        System.out.println(test.find(1));
        test.print();
        System.out.println(test.union(4, 2));
        test.print();
        // Making new group
        for(int i = 12; i < 20; i++) {
            test.union(11, i);
        }
        test.print();
        // Shows that smart union still works
        System.out.println(test.union(19, 3));
        test.print();
        // Making new group or remaining nodes
        for(int i = 21; i < 30; i++) {
            test.union(20, i);
        }
        test.print();
        // Shows smart union still works
        System.out.println(test.union(25, 11));
        test.print();
        // By finding all nodes, shows that path compression still works because they all point to 5 afterwards.
        for(int i = 0; i < 30; i++) {
            test.find(i);
        }
        test.print();
        // Shows that union to self or same group doesn't change anything
        test.union(1,10);
        test.print();
    }

}
