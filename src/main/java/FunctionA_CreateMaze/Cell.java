package FunctionA_CreateMaze;

import java.util.Arrays;

public class Cell {
    int row;
    int col;
    boolean[] walls;
    boolean visited;
    int value;

    // Constructor
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.walls = new boolean[4]; // Representing top, right, bottom, left walls
        this.visited = false;
        Arrays.fill(walls, true); // Initialize all walls as closed
        this.value = 1;
    }

}