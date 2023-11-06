package FunctionA_CreateMaze;

import java.util.Arrays;

public class Cell {
    public int row;
    public int col;
    public int value;
    boolean[] walls;
    boolean visited;

    // Constructor
    public Cell(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.walls = new boolean[4]; // Representing top, right, bottom, left walls
        this.visited = false;
        Arrays.fill(walls, true); // Initialize all walls as closed
        this.value = value;
    }

}