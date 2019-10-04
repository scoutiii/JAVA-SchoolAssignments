/**
 * Class which holds info about paths between nodes.
 * NOTE: I never really used this class.
 * @author Viki Allan, with some tidy up by Scout Jarman.
 */
public class PathInfo {
    private int dist;
    private int predecessor;

    /**
     * Default constructor, just clears the nodes.
     */
    PathInfo() {
        clear();
    }

//    /**
//     * Sets the values for the node and the distance.
//     * NOTE: I never used this.
//     * @param node that you are storing.
//     * @param dist between that node, and some other node.
//     */
//    public void set(int node, int dist) {
//        this.predecessor = node;
//        this.dist = dist;
//    }

    /**
     * Sets values to nonsensical things.
     */
    void clear() {
        this.predecessor = -1;
        this.dist = -1;
    }

    /**
     * Makes a string of the info.
     * @return a string of the info.
     */
    public String toString() {
        return "[" + this.dist + " Predecessor:" + this.predecessor + "] ";
    }


}
