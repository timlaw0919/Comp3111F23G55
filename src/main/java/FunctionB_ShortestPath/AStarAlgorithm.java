package FunctionB_ShortestPath;

import FunctionA_CreateMaze.MazeLoader;

import java.util.*;

public class AStarAlgorithm {
    public int[] tomLocation;
    public int[] jerryLocation;
    private final int[][] maze = MazeLoader.loadMazeFromCSV("maze_map.csv");

    // Constructor
    public AStarAlgorithm(int[] tomLocation, int[] jerryLocation){
        this.tomLocation = tomLocation;
        this.jerryLocation = jerryLocation;
    }

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

    public boolean checkExplored (List<Node> listOfNode, int[] temp){
        if (!listOfNode.isEmpty()){
            for (Node node : listOfNode){
                if (node.getCurrentPosition()[0] == temp[0] && node.getCurrentPosition()[1] == temp[1]){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkValidNode(List<Node> expandedNode, List<Node> frontier, int[] temp){
        if (!checkExplored(expandedNode, temp) && !checkExplored(frontier, temp) && temp[0] >= 0 && temp[0] < this.maze.length
                && temp[1] >= 0 && temp[1] < this.maze[0].length && this.maze[temp[0]][temp[1]] != 1){
            return true;
        }
        return false;
    }

    //Find current node's neighbor
    public List<Node> findNeighbor(Node currentNode, List<Node> expandedNode, List<Node> frontier){
        List<Node> neighbor = new ArrayList<>();
        int[][] fourDirection = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        for (int[] direction : fourDirection){
            int[] temp = Arrays.copyOf(currentNode.getCurrentPosition(), currentNode.getCurrentPosition().length);
            temp[0] += direction[0];
            temp[1] += direction[1];
            if (checkValidNode(expandedNode, frontier, temp)){
                neighbor.add(new Node(temp, currentNode, (Math.abs(temp[0] - this.jerryLocation[0]) + Math.abs(temp[1] - this.jerryLocation[1])), currentNode.getBackwardCost() + 1));
            }
        }
        return neighbor;
    }

    // Generate Path
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
                if (temp.getTotalCost() < minimumTotalCost) {
                    minimumTotalCost = temp.getTotalCost();
                    index = frontier.indexOf(temp);
                }
            }
            Node current = frontier.get(index);
            expandedNode.add(frontier.get(index));
            frontier.remove(index);

            // Reach the goal state, add the path coordinate
            if (current.getCurrentPosition()[0] == jerryLocation[0] && current.getCurrentPosition()[1] == jerryLocation[1]){
                Node temp = current;
                while (temp.getParent() != null) {
                    path.add(temp.getCurrentPosition());
                    temp = temp.getParent();
                }
                path.add(temp.getCurrentPosition());
                break;
            }

            // Find the valid neighbor of current node + add into frontier
            frontier.addAll(findNeighbor(current, expandedNode, frontier));
        }
        return path;
    }

    // Return Tom's next movement by int[]
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
                return neighbor.get(0).getCurrentPosition();
            }
            // Tom is surrounded by barrier
            else {
                return this.tomLocation;
            }
        }
    }
}