import java.util.LinkedList;

/**
 * Class which holds info for edges between nodes.
 * @author Viki Allan, modified slightly by Scout Jarman.
 */
public class GraphNode {
    int nodeID;
    PathInfo p1;
    PathInfo p2;
    LinkedList<EdgeInfo> successor;

//    /**
//     * Default constructor, makes the ID 0.
//     * NOTE: I never used this.
//     */
//    public GraphNode(){
//        this.nodeID = 0;
//        this.p1 = new PathInfo();
//        this.p2 = new PathInfo();
//        this.successor = new LinkedList<>();
//    }

    /**
     * Constructor which creates a node with the given ID.
     * @param nodeID of the node you are creating.
     */
    GraphNode(int nodeID){
        this.nodeID = nodeID;
        this.p2 = new PathInfo();
        this.p1 = new PathInfo();
        this.successor = new LinkedList<>();
    }

    /**
     * Function which turns a node into a string.
     * @return the string version of the node.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(nodeID);
        sb.append(p1.toString());
        sb.append(p2.toString());
        for (EdgeInfo e: successor){
            sb.append(e.toString());
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Function which creates and edge between the two given nodes.
     * @param v1 the "from" node.
     * @param v2 the "to" node.
     */
    void addEdge(int v1, int v2){
        this.successor.addFirst(new EdgeInfo(v1,v2));
    }

}
