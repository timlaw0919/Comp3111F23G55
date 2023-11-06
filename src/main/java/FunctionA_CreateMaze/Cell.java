package FunctionA_CreateMaze;

import FunctionA_CreateMaze.constant.CellState;

public class Cell {
    public int row;
    public int col;
    public CellState value;
    boolean visited;

    // Constructor
    public Cell(int row, int col, CellState value) {
        this.row = row;
        this.col = col;
        this.visited = false;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cell)
            return (this.row == ((Cell)obj).row) && (this.col == ((Cell)obj).col);

        return false;
    }
}