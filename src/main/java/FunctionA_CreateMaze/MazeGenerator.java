package FunctionA_CreateMaze;

import FunctionA_CreateMaze.constant.CellState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;


public class MazeGenerator {
    private final int rows;               // number of rows in the maze
    private final int cols;               // number of columns in the maze
    private final Cell[][] maze;          // maze array
    public Cell EntryPoint;               // maze EntryPoint
    public Cell ExitPoint;                // maze ExitPoint
    Random random = new Random();
    private boolean foundEnd = false;

    // Constructor
    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new Cell[rows][cols];
        initializeMaze();
    }

    // Initialize the maze grid with cells
    private void initializeMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell(i, j, CellState.BLOCK);
            }
        }
        // Get the EntryPoint at column[0]
        EntryPoint = maze[random.nextInt(cols)][0];
        EntryPoint.value = CellState.ENTRY;
    }

    // Generate the maze
    public void generateMaze() {
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

    private boolean checkValidNeighbors(Cell cell){
        int numNeighboringZeros = 0;
        for (int col = cell.col-1; col < cell.col+2; col++) {
            for (int row = cell.row-1; row < cell.row+2; row++) {
                if (cellOnGrid(row, col) && !cell.equals(maze[row][col]) && maze[row][col].visited) {
                    numNeighboringZeros++;
                }
            }
        }

        return (numNeighboringZeros < 4) && !maze[cell.row][cell.col].visited && !cellOnEdge(cell);
    }

    // Get unvisited neighboring cells of a given cell
    private List<Cell> getValidNeighbors(Cell cell) {
        int row = cell.row;
        int col = cell.col;
        List<Cell> neighbors = new ArrayList<>();

        // Check top neighbor
        if (row > 0 && checkValidNeighbors(maze[row - 1][col])) {
            neighbors.add(maze[row - 1][col]);
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
        // Check left neighbor
        if (col > 0 && checkValidNeighbors(maze[row][col - 1])) {
            neighbors.add(maze[row][col - 1]);
        }

        return neighbors;
    }


    public Cell[][] getMaze() {
        return maze;
    }

    private Boolean cellOnGrid(int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    private Boolean cellOnEdge(Cell cell){
        return (cell.row == 0 || cell.col == 0 || cell.row == rows-1 || cell.col == cols-1);
    }

    private Boolean checkIfExitPoint(Cell cell){
        if(foundEnd)
            return (ExitPoint.row==cell.row && ExitPoint.col==cell.col);
        else
            return (cell.col==cols-1);

    }
}