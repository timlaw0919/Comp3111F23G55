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

    //Check the new nodes and expanded nodes are equal or not
    public boolean equals (List<Node> others){
        for (Node other : others){
            if (this.getCurrentPosition()[0] == other.getCurrentPosition()[0] && this.getCurrentPosition()[1] == other.getCurrentPosition()[1])
                return true;
        }
        return false;
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
