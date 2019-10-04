/**
 * Class which holds the info of an edge (its start and ending nodes).
 * @author Viki Allan, clean up by Scout Jarman
 */
public class EdgeInfo {
    /**
     * Variable which hold the following info...
     * Source of edge.
     * Destination of edge.
     */
    private int from;
    int to;

    /**
     * Default constructor which sets values for to and from.
     * @param from is the node where you are starting from.
     * @param to is the node where you are going to.
     */
    EdgeInfo(int from, int to){
        this.from = from;
        this.to = to;

    }

    /**
     * Turns the edge into a string.
     * @return the edge as a string.
     */
    @Override
    public String toString(){
        return this.from + "->" + this.to + " ";
    }
}
