package FunctionC_TomCatchJerry;

import java.util.Arrays;

import static FunctionC_TomCatchJerry.Game.*;

public class Character {

    private int direction;
    private int lastPos;
    private int[] location;
    private boolean Game_state;
    private int speed;

    public Character() {
        direction = 0;
        location = new int[]{0, 0};
        Game_state = false;
        speed = 50;
        lastPos = toIndex(location);
    }
    public void move(){

        switch (direction) {
            case 0 -> {
                return;
            }
            case 1 -> {
                if (IsWithinBoundary(location[0] - 1)
                        && IsPath(location[0]-1, location[1])) {
                    lastPos = toIndex(location);
                    location[0] -= 1;
                }
            }
            case 2 -> {
                if (IsWithinBoundary(location[0] + 1)
                        && IsPath(location[0]+1, location[1])) {
                    lastPos = toIndex(location);
                    location[0] += 1;
                }
            }
            case 3 -> {
                if (IsWithinBoundary(location[1] - 1)
                        && IsPath(location[0], location[1]-1)) {
                    lastPos = toIndex(location);
                    location[1] -= 1;
                }
            }
            case 4 -> {
                if (IsWithinBoundary(location[1] + 1)
                        && IsPath(location[0], location[1]+1)) {
                    lastPos = toIndex(location);
                    location[1] += 1;
                }
            }
        }
        direction = 0;
        obj1.changeJerryLocation(location);
    }
    public void MoveWithShortestPath(){
        int[] loc = obj1.tomNextMovement();
        if (IsPath(loc[0],loc[1])) {
            lastPos = toIndex(location);;
            location = loc;
            obj1.changeTomLocation(location);
        }
    }
    public boolean IsWithinBoundary(int coordinate){
        return coordinate >= 0 && coordinate < 30;
    }
    public boolean IsPath(int x, int y){
        return maze[x][y] != 1;
    }
    public void setLocation(int x, int y){
        location[0] = x;
        location[1] = y;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public int getDirection(){
        return direction;
    }

    public int[] getLocation() {
        return location;
    }

    public int getLastPos() {
        return lastPos;
    }

    public static int toIndex(int[] location){
        return 30*location[0]+location[1];
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


}

