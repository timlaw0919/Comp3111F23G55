package FunctionA_CreateMaze;

import java.util.Arrays;

public class Cell {
    public int row;
    public int col;
    public int value;
    boolean visited;

    // Constructor
    public Cell(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.visited = false;
        this.value = value;
    }

}