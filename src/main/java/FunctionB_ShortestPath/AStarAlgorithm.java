package FunctionB_ShortestPath;

import FunctionA_CreateMaze.MazeLoader;

import java.util.*;

public class AStarAlgorithm {
    private int[] tomLocation;
    private int[] jerryLocation;
    private final int[][] maze = MazeLoader.loadMazeFromCSV("maze_map.csv");

    // Constructor
    public AStarAlgorithm(int[] tomLocation, int[] jerryLocation){
        this.tomLocation = tomLocation;
        this.jerryLocation = jerryLocation;
    }

    public void changeTomLocation(int[] tomLocation){
        this.tomLocation = tomLocation;
    }

    public void changeJerryLocation(int[] jerryLocation){
        this.jerryLocation = jerryLocation;
    }

    // Manhattan Distance Calculation
    public int calculateDistance(int[] currentLocation){
        return (Math.abs(currentLocation[0] - this.jerryLocation[0]) + Math.abs(currentLocation[1] - this.jerryLocation[1]));
    }

    //Find current node's neighbor
    public List<Node> findNeighbor(Node currentNode){
        List<Node> neighbor = new ArrayList<>();
        int[][] fourDirection = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        for (int[] direction : fourDirection){
            int[] temp = Arrays.copyOf(currentNode.getCurrentPosition(), currentNode.getCurrentPosition().length);
            temp[0] += direction[0];
            temp[1] += direction[1];
            neighbor.add(new Node(temp, currentNode, calculateDistance(temp), currentNode.getBackwardCost() + 1));
        }
        return neighbor;
    }

    // Generate Path
    public List<int[]> pathGeneratorByAStar(){
        List<int[]> path = new ArrayList<>(); //The final shortest path
        List<Node> expandedNode = new ArrayList<>(); // The nodes that are expanded (Only if the node is first time expands,
        // then it may be the shortest path)
        List<Node> frontier = new ArrayList<>(); // The potential candidates for expansion

        Node tomNode = new Node(tomLocation, null, calculateDistance(tomLocation), 0);
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
            for (Node neighbor : findNeighbor(current)){
                int[] neighborCurrentLocation = neighbor.getCurrentPosition();
                if (!neighbor.equals(expandedNode) && !neighbor.equals(frontier)
                        && neighborCurrentLocation[0] >= 0 && neighborCurrentLocation[0] < 30  && neighborCurrentLocation[1] >= 0
                        && neighborCurrentLocation[1] < 30 && ((maze[neighborCurrentLocation[0]][neighborCurrentLocation[1]] == 0)
                        || maze[neighborCurrentLocation[0]][neighborCurrentLocation[1]] == 2
                        || maze[neighborCurrentLocation[0]][neighborCurrentLocation[1]] == 3)){
                    frontier.add(neighbor);
                }
            }
        }
        return path;
    }

    // Return Tom's next movement by int[]
    public int[] tomNextMovement(){
        List<int[]> path = pathGeneratorByAStar();
        return path.get(path.size() - 2);
    }
}