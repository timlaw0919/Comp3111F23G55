package FunctionB_ShortestPath;

import java.util.List;

public class Node {
    public final int[] currentPosition;
    public final Node parent;
    public final int forwardCost;
    public final int backwardCost;
    public final int totalCost;

    public Node (int[] currentPosition, Node parent, int forwardCost, int backwardCost){
        this.currentPosition = currentPosition;
        this.parent = parent;
        this.forwardCost = forwardCost;
        this.backwardCost = backwardCost;
        this.totalCost = (this.forwardCost + this.backwardCost);
    }
}
