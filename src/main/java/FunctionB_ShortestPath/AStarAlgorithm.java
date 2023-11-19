package FunctionB_ShortestPath;
import FunctionA_CreateMaze.MazeLoader;
import java.util.*;

/**
 * The AStarAlgorithm class implements the A Star algorithm for finding the shortest path between Tom and Jerry in a maze.
 * It takes the locations of Tom and Jerry, as well as the maze configuration (read by Function A's MazeLoader), and calculates the shortest path using the A Star algorithm.
 * A Star algorithm's heuristic function is using Manhattan Distance which is admissible and will lead to an optimal solution.
 * This class provides methods to change the locations of Tom and Jerry, check if a node has been explored, check if a node is valid,
 * find the neighbors of a given node, generate the shortest path using A Star Algorithm, and determine Tom's next movement based on the calculated path.
 *
 * @see Node
 * @see FunctionA_CreateMaze.MazeLoader
 */

public class AStarAlgorithm {
    public int[] tomLocation;
    public int[] jerryLocation;
    public final int[][] maze;

    /**
     * AStarAlgorithm's constructor for constructing the object.
     * @param tomLocation Tom's current location
     * @param jerryLocation Jerry's current location
     * @param maze CSV file name of the maze
     */
    public AStarAlgorithm(int[] tomLocation, int[] jerryLocation, String maze){
        this.tomLocation = tomLocation;
        this.jerryLocation = jerryLocation;
        this.maze = MazeLoader.loadMazeFromCSV(maze);
    }

    /**
     * Changing the location of Tom or Jerry
     * @param location The latest location
     * @param who 0 for changing Tom's location, other for changing Jerry's location
     * @return The latest location
     */
    public int[] changeLocation(int[] location, int who){
        if (who == 0){
            this.tomLocation = location;
            return this.tomLocation;
        }
        else{
            this.jerryLocation = location;
            return this.jerryLocation;
        }
    }

    /**
     * Check the node is already explored or not.
     * @param listOfNode The nodes inside the list is explored
     * @param temp The current location
     * @return True if it is already explored, False if not explored
     */
    public boolean checkExplored (List<Node> listOfNode, int[] temp){
        if (!listOfNode.isEmpty()){
            for (Node node : listOfNode){
                if (node.currentPosition[0] == temp[0] && node.currentPosition[1] == temp[1]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check the location is valid to be a neighbor or not
     * @param expandedNode A list of nodes which have already expanded
     * @param frontier A list of nodes which are candidates to be expanded
     * @param temp The current location
     * @return True if not the node does not explore and within the maze, otherwise False
     */
    public boolean checkValidNode(List<Node> expandedNode, List<Node> frontier, int[] temp){
        if (!checkExplored(expandedNode, temp) && !checkExplored(frontier, temp) && temp[0] >= 0 && temp[0] < this.maze.length
                && temp[1] >= 0 && temp[1] < this.maze[0].length && this.maze[temp[0]][temp[1]] != 1){
            return true;
        }
        return false;
    }

    /**
     * Find all neighbor near the current node
     * @param currentNode The current node
     * @param expandedNode A list of nodes which have already expanded
     * @param frontier A list of nodes which are candidates to be expanded
     * @return A list of valid neighbor with type Node
     */
    public List<Node> findNeighbor(Node currentNode, List<Node> expandedNode, List<Node> frontier){
        List<Node> neighbor = new ArrayList<>();
        int[][] fourDirection = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        for (int[] direction : fourDirection){
            int[] temp = Arrays.copyOf(currentNode.currentPosition, currentNode.currentPosition.length);
            temp[0] += direction[0];
            temp[1] += direction[1];
            if (checkValidNode(expandedNode, frontier, temp)){
                neighbor.add(new Node(temp, currentNode, (Math.abs(temp[0] - this.jerryLocation[0]) + Math.abs(temp[1] - this.jerryLocation[1])), currentNode.backwardCost + 1));
            }
        }
        return neighbor;
    }

    /**
     * Generate the shortest path between Tom and Jerry
     * @return A list of coordinate with type int[] which form the shortest path
     */
    public List<int[]> pathGeneratorByAStar(){
        List<int[]> path = new ArrayList<>(); //The final shortest path
        List<Node> expandedNode = new ArrayList<>(); // The nodes that are expanded (Only if the node is first time expands, then it may be the shortest path)
        List<Node> frontier = new ArrayList<>(); // The potential candidates for expansion

        Node tomNode = new Node(tomLocation, null, (Math.abs(tomLocation[0] - this.jerryLocation[0]) + Math.abs(tomLocation[1] - this.jerryLocation[1])), 0);
        frontier.add(tomNode);

        while (!(frontier.isEmpty())){
            // Find out the lowest total cost node + Remove it from frontier + Add to expanded node
            int minimumTotalCost = 10000;
            int index = -1;
            for (Node temp : frontier){
                if (temp.totalCost < minimumTotalCost) {
                    minimumTotalCost = temp.totalCost;
                    index = frontier.indexOf(temp);
                }
            }
            Node current = frontier.get(index);
            expandedNode.add(frontier.get(index));
            frontier.remove(index);

            // Reach the goal state, add the path coordinate
            if (current.currentPosition[0] == jerryLocation[0] && current.currentPosition[1] == jerryLocation[1]){
                Node temp = current;
                while (temp.parent != null) {
                    path.add(temp.currentPosition);
                    temp = temp.parent;
                }
                path.add(temp.currentPosition);
                break;
            }

            // Find the valid neighbor of current node + add into frontier
            frontier.addAll(findNeighbor(current, expandedNode, frontier));
        }
        return path;
    }

    /**
     * Tom's next action
     * @return The coordinate of Tom's next movement.
     */
    public int[] tomNextMovement(){
        List<int[]> path = pathGeneratorByAStar();

        // At least having one path between Tom and Jerry
        if (path.size() >= 2){
            return path.get(path.size() - 2);
        }

        // No path between Tom and Jerry
        else {
            List<Node> temp = new ArrayList<>();
            List<Node> neighbor = findNeighbor(new Node(this.tomLocation, null, 0, 0), temp, temp);
            // Some movable cell near Tom
            if (!neighbor.isEmpty()) {
                return neighbor.get(0).currentPosition;
            }
            // Tom is surrounded by barrier
            else {
                return this.tomLocation;
            }
        }
    }
}