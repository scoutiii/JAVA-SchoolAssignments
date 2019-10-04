import java.io.File;
import java.util.Properties;
import java.util.Scanner;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Class which creates a word net graph from a file.
 * Can find the distance between nodes in the graph.
 * @author Scout Jarman.
 */
public class Graph {
    /**
     * Variable which hold the following...
     * Number of vertices in the graph.
     * Adjacency list for graph.
     * The file from which the graph was created.
     */
    private int numVertex;
    private GraphNode[] G;
    private String graphName;

    /**
     * Default constructor, sets the number of vertices to 0, and the file name to nothing.
     */
    private Graph() {
        this.numVertex = 0;
        this.graphName = "";
    }

//    /**
//     * Constructor which takes in the number of vertices of the graph you want to create.
//     * NOTE: Never used.
//     * @param numVertex is the number of vertices in the graph you want to create.
//     */
//    public Graph(int numVertex) {
//        this.numVertex = numVertex;
//        G = new GraphNode[numVertex];
//        for (int i = 0; i < numVertex; i++) {
//            G[i] = new GraphNode(i);
//        }
//    }

//    /**
//     * Function which will take two nodes and add an edge between them.
//     * NOTE: I never used this method.
//     * @param source node that will do the pointing.
//     * @param destination node which is getting pointed at.
//     * @return whether it was done successfully.
//     */
//    public boolean addEdge(int source, int destination) {
//        if (source < 0 || source >= numVertex) {
//            return false;
//        }
//        if (destination < 0 || destination >= numVertex) {
//            return false;
//        }
//        // add edge
//        G[source].addEdge(source, destination);
//        return true;
//    }

    /**
     * To string method which will just print the graph out.
     * @return the string version of the graph.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("The Graph ").append(graphName).append(" \n");

        for (int i = 0; i < numVertex; i++) {
            sb.append( G[i].toString() );
        }
        return sb.toString();
    }

    /**
     * Takes in a file name, and creates a graph based on the file.
     * @param filename of the file you want to create a graph of.
     */
    private void makeGraph(String filename) {
        try {
            this.graphName = filename;
            Scanner reader = new Scanner(new File(filename));
            System.out.println( "\n" + filename );
            this.numVertex = reader.nextInt();
            this.G = new GraphNode[this.numVertex];
            for (int i = 0; i < this.numVertex; i++) {
                this.G[i] = new GraphNode(i);
            }
            while (reader.hasNextInt()) {
                int v1 = reader.nextInt();
                int v2 = reader.nextInt();
                this.G[v1].addEdge(v1, v2);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function which goes through and resets all predecessors of each node in graph
     * NOTE: I don't actually make too much use of this.
     */
    private void clearAllPredecessor() {
        for (int i = 0; i < numVertex; i++) {
            G[i].p1.clear();
            G[i].p2.clear();
        }
    }

//    /**
//     * Find the path from v1 to v2 going through anc.
//     * NOTE: I decided not to use this. The given code was too weird.
//     * @param v1:  first vertex
//     * @param v2:  second vertex
//     * @param anc: ancestor of v1 and v2
//     * @return the path
//     */
//    public String reportPath(int v1, int v2, int anc) {
//        return ("Wish I knew the path from " + v1 + " " + anc + " " + v2);
//    }

    /**
     * Computes the least common ancestor of v1 and v2,
     * prints the length of the path, the ancestor, and the path itself.
     * @param v1: first vertex
     * @param v2: second vertex
     * @return the length of the shortest ancestral path.
     */
    private int lca(int v1, int v2) {
        // This bit of code creates a list of v1 and its ancestors (just as ints)
        LinkedList<Integer> firstPath = new LinkedList<>();
        int element = v1;
        while(this.G[element].successor.size() > 0) {
            firstPath.add(element);
            element = this.G[element].successor.getFirst().to;
        }
        firstPath.add(element);

        // This then creates a list of v2 and its ancestors
        LinkedList<Integer> secondPath = new LinkedList<>();
        element = v2;
        while(this.G[element].successor.size() > 0) {
            secondPath.add(element);
            element = this.G[element].successor.getFirst().to;
        }
        secondPath.add(element);

        // This goes through and finds where v2 first intersects v1 (in terms of ancestors)
        int leastCA = this.G[0].nodeID;
        boolean found = false;
        for(int i = 0; i < secondPath.size() && !found; i++) {
            if(firstPath.contains(secondPath.get(i))) {
                found = true;
                leastCA = secondPath.get(i);
            }
        }

        // Calculates the distance between the two nodes (in a clever way)
        int dist = firstPath.indexOf(leastCA) + secondPath.indexOf(leastCA);
        // Creates the path as a string
        // Goes through the first path in order
        StringBuilder path = new StringBuilder();
        found = false;
        for(int i = 0; i < firstPath.size() && !found; i++) {
            path.append(firstPath.get(i));
            path.append(" ");
            if(firstPath.get(i).equals(leastCA)) {
                found = true;
            }
        }
        // Then goes through the second path backwards (skipping the common ancestor)
        for(int i = secondPath.indexOf(leastCA) - 1; i >= 0; i--) {
            path.append(secondPath.get(i));
            path.append(" ");
        }

        // This is a modified version of what was here previously
        System.out.println( graphName + " Best lca " + v1 + " " + v2 + " Distance: " +
                dist + " Ancestor " + leastCA + " Path: " + path);
        clearAllPredecessor();
        return dist;
    }


    /**
     * Function which takes a set of nodes in the graph,
     * then calculates the distances between every node, returns the max of the sum for each node.
     * @param v is the array containing the list of nodes to check.
     * @return the vertex which is the outcast.
     */
    private int outcast(int[] v) {
        int[] sums = new int[v.length];
        for(int i = 0; i < sums.length; i++) {
            sums[i] = 0;
        }
        for(int i = 0; i < v.length; i++) {
            for(int j = i + 1; j < v.length; j++) {
                int value = lca(v[i], v[j]);
                sums[i] += value;
                sums[j] += value;
            }
        }
        int index = 0;
        int sum = 0;
        for(int i = 0; i < sums.length; i++) {
            if(sums[i] >= sum) {
                sum = sums[i];
                index = i;
            }
        }
        int outcast = v[index];
        System.out.println( "The outcast of " + Arrays.toString(v) + " is " + outcast +
                            " with distance sum of " + sum);
        return outcast;
    }

    /**
     * Main function which demonstrates the shortest path stuff.
     * @param args are not used.
     */
    public static void main(String[] args) {
        Graph graph1 = new Graph();
        graph1.makeGraph("digraph1.txt");
        //System.out.println(graph.toString());
        int[] set1 = {7, 10, 2};
        int[] set2 = {7, 17, 5, 11, 4, 23};
        int[] set3 = {10, 17, 13};
        LinkedList<Integer> dummy = new LinkedList<>();

        graph1.lca(3, 3);
        graph1.lca(3, 7);
        graph1.lca(5, 6);
        graph1.lca(9, 1);
        dummy.add(graph1.outcast(set1));

        Graph graph2 = new Graph();
        graph2.makeGraph("digraph2.txt");
        //System.out.println(graph2.toString());
        graph2.lca(3, 24);
        dummy.add(graph2.outcast(set3));
        dummy.add(graph2.outcast(set2));
        dummy.add(graph2.outcast(set1));
        if(dummy.pop() == 420) {
            System.out.println("This has no purpose....");
        }
    }
}