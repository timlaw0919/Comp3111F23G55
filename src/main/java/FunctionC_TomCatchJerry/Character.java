package FunctionC_TomCatchJerry;

import FunctionC_TomCatchJerry.constant.DirectionState;

import static FunctionC_TomCatchJerry.GameMain.*;
import static FunctionC_TomCatchJerry.constant.DirectionState.*;

public class Character {

    private DirectionState direction;   // Moving direction
    private int lastPos;                // Previous Location (index)
    private int[] location;             // Current location (Coordinates)
    private boolean Game_state;         // Current Game state
    private int speed;                  // Speed of the moving character

    public Character() {
        direction = UNMOVE;
        location = new int[]{0, 0};
        Game_state = false;
        speed = 50;
        lastPos = toIndex(location);
    }

    /**
     * Player controls the direction of the moving object using keyboard
     * 1. Store the previous location to repaint it into path
     * 2. Update the new location
     * 3. Reset the direction to 0 for ensuring that no movement exists when no input is received
     * 4. Update the new location to the algorithm for calculating the newest shortest path
     */
    public void move(){

        switch (direction) {
            case UNMOVE -> {
                return;
            }
            case UPWARD -> {
                if (IsWithinBoundary(location[0] - 1)
                        && IsPath(location[0]-1, location[1])) {
                    lastPos = toIndex(location);
                    location[0] -= 1;
                }
            }
            case DOWNWARD -> {
                if (IsWithinBoundary(location[0] + 1)
                        && IsPath(location[0]+1, location[1])) {
                    lastPos = toIndex(location);
                    location[0] += 1;
                }
            }
            case LEFT -> {
                if (IsWithinBoundary(location[1] - 1)
                        && IsPath(location[0], location[1]-1)) {
                    lastPos = toIndex(location);
                    location[1] -= 1;
                }
            }
            case RIGHT -> {
                if (IsWithinBoundary(location[1] + 1)
                        && IsPath(location[0], location[1]+1)) {
                    lastPos = toIndex(location);
                    location[1] += 1;
                }
            }
        }
        direction = UNMOVE;     // Reset the direction when there is no input received
        shortestPath.changeJerryLocation(location);     // Update Jerry's location to find the newest shortest path
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
     * @param coordinate The x/y coordinate of the location
     * @return true when the index is valid in maze
     */
    public boolean IsWithinBoundary(int coordinate){
        return coordinate >= 0 && coordinate < maze.length;
    }

    /**
     * Check whether the new location is valid for the object to move
     * @param x The x-coordinate of the new location
     * @param y The y-coordinate of the new location
     * @return true if the location is not a BLOCK
     */
    public boolean IsPath(int x, int y){
        return maze[x][y] != 1;
    }

    public void setLocation(int x, int y){
        location[0] = x;
        location[1] = y;
    }

    public void setDirection(DirectionState direction){
        this.direction = direction;
    }

    public DirectionState getDirection(){
        return direction;
    }

    public int[] getLocation() {
        return location;
    }

    public int getLastPos() {
        return lastPos;
    }

    public static int toIndex(int[] location){
        return maze.length*location[0]+location[1];
    }

    public void setGame_state(boolean game_state) {
        Game_state = game_state;
    }

    public boolean getGame_state() {
        return Game_state;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void reset(int row, int col){
        lastPos = toIndex(location);
        location[0] = row;
        location[1] = col;
        direction = UNMOVE;
        Game_state = false;
    }

}

