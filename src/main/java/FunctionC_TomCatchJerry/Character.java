package FunctionC_TomCatchJerry;

import static FunctionC_TomCatchJerry.GameMain.maze;
import static FunctionC_TomCatchJerry.GameMain.shortestPath;

public class Character {

    public int newCol;
    public int newRow;
    public int lastPos;                // Previous Location (index)
    public int[] location;             // Current location (Coordinates)
    public boolean Game_state;         // Current Game state
    public int speed;                  // Speed of the moving character

    /**
     * Initialize all the attributes
     */
    public Character() {
        newRow = 0;
        newCol = 0;
        location = new int[]{0, 0};
        Game_state = false;
        speed = 0;
        lastPos = toIndex(location);
    }

    /**
     * Player controls the direction of the moving object using keyboard
     * 1. Check whether the new location is valid for moving
     * 2. Store the previous location
     * 3. Update the new location
     * 4. Update the current location to the algorithm for calculating the new shortest path between Tom and Jerry
     */
    public void move(){
        if (IsWithinBoundary(location[0]+newRow)
                && IsWithinBoundary(location[1]+newCol)
                && IsPath(location[0]+newRow,location[1]+newCol)){
            lastPos = toIndex(location);
            location[0] += newRow;
            location[1] += newCol;
            shortestPath.changeJerryLocation(location);
        }
    }

    /**
     * Computer controls the movement of the moving object using algorithm
     * 1. Get the current NEXT shortest path
     * 2. Check whether the next step is valid
     * 3. Update the last position and the current location
     * 4. Update Tom's Location to find the newest shortest path
     */
    public void MoveWithShortestPath(){
        int[] loc = shortestPath.tomNextMovement();
        if (IsPath(loc[0],loc[1])) {
            lastPos = toIndex(location);;
            location = loc;
            shortestPath.changeTomLocation(location);
        }
    }

    /**
     * Check whether the new coordinate of the location is valid in maze
     * @param coordinate The coordinate of the row/column of the location
     * @return true when the index is valid in maze
     */
    public boolean IsWithinBoundary(int coordinate){
        return coordinate >= 0 && coordinate < maze.length;
    }

    /**
     * Check whether the new location is valid for the object to move
     * @param row The row value of the new location
     * @param col The column value of the new location
     * @return true if the location is not a BLOCK
     */
    public boolean IsPath(int row, int col){
        return maze[row][col] != 1;
    }

    /**
     * Convert the 2D location to 1D index
     * @param location A int array storing the row and column value of a location
     * @return the index value in 1D
     */
    public static int toIndex(int[] location){
        return maze.length*location[0]+location[1];
    }

    /**
     * Store the current location and then reset the character's location back to spawn point
     * @param row The row value of the spawn point
     * @param col The column value of the spawn point
     */
    public void reset(int row, int col){
        lastPos = toIndex(location);
        location[0] = row;
        location[1] = col;
        newRow = 0;
        newCol = 0;
        Game_state = false;
    }

}

