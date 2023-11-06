package FunctionA_CreateMaze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    private int rows;               // number of rows in the maze
    private int cols;               // number of columns in the maze
    private static int PATH = 0;
    private static int BLOCK = 1;
    private static int START = 2;
    private static int END = 3;
    private Cell[][] maze;          // maze array
    public Cell StartingPoint;
    public Cell EndingPoint;
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
                maze[i][j] = new Cell(i, j, BLOCK);
            }
        }
        StartingPoint = maze[random.nextInt(cols)][0];
        StartingPoint.value = 2;
    }

    // Generate the maze using MST algorithm
    public void generateMaze() {
        StartingPoint.visited = true;

        Stack<Cell> stack = new Stack<>();
        stack.push(StartingPoint);

        while (!stack.isEmpty()) {
            Cell currentCell = stack.peek();
            List<Cell> neighbors = getValidNeighbors(currentCell);      // valid = unvisited + numNeighboringZeros< 3 + notonedge

            if (!neighbors.isEmpty()) {
                Cell randomNeighbor = neighbors.get(random.nextInt(neighbors.size()));
                removeWalls(currentCell, randomNeighbor);
                randomNeighbor.visited = true;
                if(!checkIfEndingPoint(randomNeighbor))
                    randomNeighbor.value = PATH;
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
                if (pointOnGrid(row, col) && pointNotCell(cell, row, col) && maze[row][col].visited) {
                    numNeighboringZeros++;
                }
            }
        }

        return (numNeighboringZeros < 3) && !maze[cell.row][cell.col].visited && !pointOnEdge(cell);
    }

    // Get unvisited neighboring cells of a given cell
    private List<Cell> getValidNeighbors(Cell cell) {
        int row = cell.row;
        int col = cell.col;
        List<Cell> neighbors = new ArrayList<>();
        List<Cell> endingPoint = new ArrayList<>();

        // Check top neighbor
        if (row > 0 && checkValidNeighbors(maze[row - 1][col])) {
            neighbors.add(maze[row - 1][col]);
        }
        // Check right neighbor
        if (col < cols - 1 && checkValidNeighbors(maze[row][col + 1])) {
            neighbors.add(maze[row][col + 1]);
        }
        // Check right neighbor can possbily be the ending point
        else if (col < cols-1 && checkIfEndingPoint(maze[row][col + 1]) && !foundEnd) {
            endingPoint.add(maze[row][col + 1]);
            foundEnd = true;
            EndingPoint = maze[row][col + 1];
            EndingPoint.value = 3;
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

    // Remove walls between two neighboring cells
    private void removeWalls(Cell current, Cell neighbor) {
        int rowDiff = neighbor.row - current.row;
        int colDiff = neighbor.col - current.col;

        if (rowDiff == -1) {
            current.walls[0] = false; // Remove top wall of current cell
            neighbor.walls[2] = false; // Remove bottom wall of neighbor cell
        } else if (colDiff == 1) {
            current.walls[1] = false; // Remove right wall of current cell
            neighbor.walls[3] = false; // Remove left wall of neighbor cell
        } else if (rowDiff == 1) {
            current.walls[2] = false; // Remove bottom wall of current cell
            neighbor.walls[0] = false; // Remove top wall of neighbor cell
        } else if (colDiff == -1) {
            current.walls[3] = false; // Remove left wall of current cell
            neighbor.walls[1] = false; // Remove right wall of neighbor cell
        }
    }

    public Cell[][] getMaze() {
        return maze;
    }

    private Boolean pointOnGrid(int row, int col) {
        return row >= 0 && col >= 0 && row < rows && col < cols;
    }

    private Boolean pointOnEdge(Cell cell){
        return (cell.row == 0 || cell.col == 0 || cell.row == rows-1 || cell.col == cols-1);
    }

    private Boolean pointNotCell(Cell cell, int row, int col) {
        return !(row == cell.row && col == cell.col);
    }

    private Boolean checkIfStartingPoint(Cell cell){
        return (StartingPoint.row==cell.row && StartingPoint.col==cell.col);
    }
    private Boolean checkIfEndingPoint(Cell cell){
//        return (EndingPoint.row==cell.row && EndingPoint.col==cell.col);
        return (cell.col==cols-1);
    }
}