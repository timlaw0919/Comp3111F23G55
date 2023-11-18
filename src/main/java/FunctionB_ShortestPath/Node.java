package FunctionB_ShortestPath;

import java.util.List;

public class Node {
    private final int[] currentPosition;
    private final Node parent;
    private final int forwardCost;
    private final int backwardCost;
    private final int totalCost;

    public Node (int[] currentPosition, Node parent, int forwardCost, int backwardCost){
        this.currentPosition = currentPosition;
        this.parent = parent;
        this.forwardCost = forwardCost;
        this.backwardCost = backwardCost;
        this.totalCost = (this.forwardCost + this.backwardCost);
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    public Node getParent() {
        return parent;
    }

    public int getBackwardCost() {
        return backwardCost;
    }
}
