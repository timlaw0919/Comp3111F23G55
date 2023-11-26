package FunctionA_CreateMaze;

import FunctionA_CreateMaze.constant.CellState;

import java.util.*;

/**
 * The MazeGenerator class is to generate a maze with multiple paths, one ENTRY and one EXIT on the opposite edge randomly
 * The algorithm for generating the maze is Depth-First-Search Algorithm (DFS)
 * DFS starts with the randomly created ENTRY on edge (initial current Cell) while all the remaining Cells are BLOCKS
 * Then it expands the PATHs on maze by randomly selecting one of the valid neighbouring Cells of the current Cell which is also the next current cells
 * Also, it finds the EXIT during the expansion process which is the first neighbouring cell of the current cell on the opposite edge of ENTRY
 *
 */

public class MazeGenerator {
    private final int rows;               // number of rows in the maze
    private final int cols;               // number of columns in the maze
    private final Cell[][] maze;          // maze array
    public Cell EntryPoint;               // maze EntryPoint
    public Cell ExitPoint;                // maze ExitPoint
    Random random = new Random();
    private boolean foundEnd = false;

    /**
     * Constructs a MazeGenerator object with the specified number of rows and columns.
     *
     * @param rows The number of rows in the maze.
     * @param cols The number of columns in the maze.
     */
    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new Cell[rows][cols];
    }

    /**
     * Generates the entry point of the maze.
     */
    public void EntryPointGenerator(Integer testRandomNumber){
        do {
            int randomNumber;
            if(testRandomNumber != null) {
                randomNumber = testRandomNumber;
            } else {
                randomNumber = random.nextInt(100);
            }
            if (randomNumber % 4 == 0) {
                // Set EntryPoint at left edge
                EntryPoint = maze[random.nextInt(rows)][0];
                EntryPoint.value = CellState.ENTRY;
            } else if (randomNumber % 4 == 1) {
                // Set EntryPoint at right edge
                EntryPoint = maze[random.nextInt(rows)][cols - 1];
                EntryPoint.value = CellState.ENTRY;
            } else if (randomNumber % 4 == 2) {
                // Set EntryPoint at top edge
                EntryPoint = maze[0][random.nextInt(cols)];
                EntryPoint.value = CellState.ENTRY;
            } else {
                // Set EntryPoint at bottom edge
                EntryPoint = maze[rows - 1][random.nextInt(cols)];
                EntryPoint.value = CellState.ENTRY;
            }

            if(cellOnCorner(EntryPoint)){
                EntryPoint.value = CellState.BLOCK;
            }

        }while(cellOnCorner(EntryPoint));
    }

    /**
     * Initializes the maze grid with cells.
     */
    public void initializeMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell(i, j, CellState.BLOCK);
            }
        }
        // Get the Random EntryPoint
        EntryPointGenerator(null);
    }

    /**
     * Generates the maze by DFS algorithm.
     */
    public void generateMaze() {
        initializeMaze();
        EntryPoint.visited = true;

        Stack<Cell> stack = new Stack<>();
        stack.push(EntryPoint);

        while (!stack.isEmpty()) {
            Cell currentCell = stack.peek();
            List<Cell> neighbors = getValidNeighbors(currentCell);

            if (!neighbors.isEmpty()) {
                Cell randomNeighbor = neighbors.get(random.nextInt(neighbors.size()));
                randomNeighbor.visited = true;
                if(!checkIfExitPoint(randomNeighbor))
                    randomNeighbor.value = CellState.PATH;
                stack.push(randomNeighbor);
            } else {
                stack.pop();
            }
        }

    }

    /**
     * Checks if a cell is a valid neighboring cell.
     * Criteria for a valid neighboring cell if the Exit of the maze is not yet found
     * The less than 4 of the 8 connected cells of the cell is PATH, the cell is not yet visited by the DFS algorithm, the cell is not on the edge (excluding the opposite edge of ENTRY)
     * Criteria for a valid neighbouring cell if the Exit of the maze is found
     * The less than 4 of the 8 connected cells of the cell is PATH, the cell is not yet visited by the DFS algorithm, the cell is not on the edges (all)
     *
     * @param cell The cell to check.
     * @return True if the cell is a valid neighboring cell, false otherwise.
     */
    public boolean checkValidNeighbors(Cell cell){
        int numNeighboringZeros = 0;
        for (int col = cell.col-1; col < cell.col+2; col++) {
            for (int row = cell.row-1; row < cell.row+2; row++) {
                if (cellOnGrid(row, col) && !cell.equals(maze[row][col]) && maze[row][col].value==CellState.PATH) {
                    numNeighboringZeros++;
                }
            }
        }

        return (numNeighboringZeros < 4) && !maze[cell.row][cell.col].visited && !cellOnEdge(cell) && !checkIfEntryPoint(cell);
    }

    /**
     * Retrieves the valid neighboring cells of a given cell and set information for EXIT if found.
     *
     * @param cell The cell to retrieve the neighbors for.
     * @return A list of valid neighboring cells.
     */
    public List<Cell> getValidNeighbors(Cell cell) {
        int row = cell.row;
        int col = cell.col;
        List<Cell> neighbors = new ArrayList<>();

        // Check top neighbor
        if (row > 0 && checkValidNeighbors(maze[row - 1][col])) {
            neighbors.add(maze[row - 1][col]);
        }
        // Check top neighbor can possibly be the Exit point
        else if (row > 0 && checkIfExitPoint(maze[row - 1][col]) && !foundEnd) {
            List<Cell> endingPoint = new ArrayList<>();
            endingPoint.add(maze[row - 1][col]);
            foundEnd = true;
            ExitPoint = maze[row - 1][col];
            ExitPoint.value = CellState.EXIT;
            return endingPoint;
        }

        // Check right neighbor
        if (col < cols - 1 && checkValidNeighbors(maze[row][col + 1])) {
            neighbors.add(maze[row][col + 1]);
        }
        // Check right neighbor can possibly be the Exit point
        else if (col < cols-1 && checkIfExitPoint(maze[row][col + 1]) && !foundEnd) {
            List<Cell> endingPoint = new ArrayList<>();
            endingPoint.add(maze[row][col + 1]);
            foundEnd = true;
            // Get the ExitPoint at column[cols-1]
            ExitPoint = maze[row][col + 1];
            ExitPoint.value = CellState.EXIT;
            return endingPoint;
        }

        // Check bottom neighbor
        if (row < rows - 1 && checkValidNeighbors(maze[row + 1][col])) {
            neighbors.add(maze[row + 1][col]);
        }
        // Check bottom neighbor can possibly be the Exit point
        else if (row < rows - 1 && checkIfExitPoint(maze[row + 1][col]) && !foundEnd) {
            List<Cell> endingPoint = new ArrayList<>();
            endingPoint.add(maze[row + 1][col]);
            foundEnd = true;
            ExitPoint = maze[row + 1][col];
            ExitPoint.value = CellState.EXIT;
            return endingPoint;
        }

        // Check left neighbor
        if (col > 0 && checkValidNeighbors(maze[row][col - 1])) {
            neighbors.add(maze[row][col - 1]);
        }
        // Check left neighbor can possibly be the Exit point
        else if (col > 0 && checkIfExitPoint(maze[row][col - 1]) && !foundEnd) {
            List<Cell> endingPoint = new ArrayList<>();
            endingPoint.add(maze[row][col - 1]);
            foundEnd = true;
            ExitPoint = maze[row][col - 1];
            ExitPoint.value = CellState.EXIT;
            return endingPoint;
        }

        return neighbors;
    }

    /**
     * Checks if a cell is on the grid.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return True if the cell is on the grid, false otherwise.
     */
    public Boolean cellOnGrid(int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    /**
     * Checks if a cell is on the edge of the maze.
     *
     * @param cell The cell to check.
     * @return True if the cell is on the edge, false otherwise.
     */
    public Boolean cellOnEdge(Cell cell){
        return (cell.row == 0 || cell.col == 0 || cell.row == rows-1 || cell.col == cols-1);
    }

    /**
     * Checks if a cell is on the corner of the maze.
     *
     * @param cell The cell to check.
     * @return True if the cell is on the corner, false otherwise.
     */
    public Boolean cellOnCorner(Cell cell){
        return ((cell.row==0 && cell.col==0) || (cell.row==0 && cell.col==cols-1) ||
                (cell.row==rows-1 && cell.col==0) || (cell.row==rows-1 && cell.col==cols-1));
    }

    /**
     * Checks if a cell is the entry point of the maze.
     *
     * @param cell The cell to check.
     * @return True if the cell is the entry point, false otherwise.
     */
    public Boolean checkIfEntryPoint(Cell cell){
        return (EntryPoint.equals(cell));
    }

    /**
     * Checks if a cell is the exit point of the maze.
     *
     * @param cell The cell to check.
     * @return True if the cell is the exit point, false otherwise.
     */
    public Boolean checkIfExitPoint(Cell cell){
        if(foundEnd)
            return (ExitPoint.equals(cell));
        else
            return (cell.row==rows-1 || cell.row==0 || cell.col==cols-1 || cell.col==0) && CellOnOppositeEdge(EntryPoint,cell);

    }

    /**
     * Checks if two cells are on the opposite edges of the maze.
     *
     * @param cell_1 The first cell.
     * @param cell_2 The second cell.
     * @return True if the cells are on opposite edges, false otherwise.
     */
    public Boolean CellOnOppositeEdge(Cell cell_1, Cell cell_2){
        return ((cell_1.row==0 && cell_2.row==rows-1) || (cell_1.col==0 && cell_2.col==cols-1) ||
                (cell_1.row==rows-1 && cell_2.row==0) || (cell_1.col==cols-1 && cell_2.col==0));

    }

    /**
     * Retrieves the maze array.
     *
     * @return The maze array.
     */
    public Cell[][] getMaze() {
        return maze;
    }

}