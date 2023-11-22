package FunctionA_CreateMaze;

import FunctionA_CreateMaze.constant.CellState;

/**
 * The Cell class represents the building elements of the maze.
 * Each Cell contains information about its position on the maze which is represented by row and col, CellState and condition of being visited by DFS algorithm from MazeGenerator Class or not
 *
 * @see MazeGenerator
 */

public class Cell {
    public int row;             // The row index of the cell
    public int col;             // The column index of the cell
    public CellState value;     // The state of the cell: PATH, BLOCK, ENTRY, EXIT
    boolean visited;            // Indicates whether the cell has been visited or not

    /**
     * Constructs a new Cell object.
     *
     * @param row   The row index of the cell.
     * @param col   The column index of the cell.
     * @param value The state/value of the cell.
     */
    public Cell(int row, int col, CellState value) {
        this.row = row;
        this.col = col;
        this.visited = false;
        this.value = value;
    }

    /**
     * Checks if the current cell is equal to the specified object.
     * Equal if the object is Cell. Also, it has the same row, col with the current cell
     *
     * @param obj The object to compare.
     * @return True if the cells are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cell)
            return (this.row == ((Cell)obj).row) && (this.col == ((Cell)obj).col);

        return false;
    }
}