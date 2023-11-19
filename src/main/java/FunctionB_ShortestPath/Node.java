package FunctionB_ShortestPath;

/**
 * The Node class represents a node in the context of A star algorithm.
 * Each node contains information about its current position, parent node, forward cost, backward cost, and total cost.
 * The current position is represented by an array of two integers (x,y coordinate).
 * The parent node represents the previous node in the path.
 * The forward cost is the cost of moving from the current node to the goal node by Manhattan Distance.
 * The backward cost is the cost from the current node to the starting node (each step costs 1).
 * The total cost is the sum of the forward cost and backward cost.
 * Objects of this class are used in A Star algorithms to track and evaluate the path.
 *
 * @see AStarAlgorithm
 */

public class Node {
    public final int[] currentPosition;
    public final Node parent;
    public final int forwardCost;
    public final int backwardCost;
    public final int totalCost;

    /**
     * Node class's constructor to construct the node's position, parent and all costs
     * @param currentPosition The current position that node
     * @param parent The parent node of current node
     * @param forwardCost The cost of moving from the current node to the goal node by Manhattan Distance
     * @param backwardCost The cost from the current node to the starting node (each step costs 1)
     */
    public Node (int[] currentPosition, Node parent, int forwardCost, int backwardCost){
        this.currentPosition = currentPosition;
        this.parent = parent;
        this.forwardCost = forwardCost;
        this.backwardCost = backwardCost;
        this.totalCost = (this.forwardCost + this.backwardCost);
    }
}
