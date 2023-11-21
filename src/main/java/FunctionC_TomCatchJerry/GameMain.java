package FunctionC_TomCatchJerry;

import FunctionA_CreateMaze.Cell;
import FunctionA_CreateMaze.MazeGenerator;
import FunctionA_CreateMaze.MazeLoader;
import FunctionB_ShortestPath.AStarAlgorithm;

import static FunctionA_CreateMaze.CSVOutput.outputCSVFile;


public class GameMain {
    public static int mazeSize = 30;
    public static int[][] maze = newMaze();       // Maze
    public static Character Tom = new Character();      // Computer
    public static Character Jerry = new Character();    // Player
    public static AStarAlgorithm shortestPath = new AStarAlgorithm(Tom.location, Jerry.location, "maze_map.csv");    // Algorithm for finding shortest Path

    /**
     * Generate a new maze according to the mazeSize before the game starts
     * The row and column value of the maze depends on the value of the mazeSize
     * Remarks: Assign a preferred value to mazeSize before generating the new maze
     * @return The maze which is newly generated
     */
    public static int[][] newMaze(){
        int rows = mazeSize; // Number of rows in the maze
        int cols = mazeSize; // Number of columns in the maze

        // Generator the maze
        MazeGenerator mazeGenerator = new MazeGenerator(rows, cols);
        mazeGenerator.generateMaze();

        // Output the maze as csv file
        Cell[][] Maze = mazeGenerator.getMaze();
        outputCSVFile(Maze, "maze_map.csv");
        return MazeLoader.loadMazeFromCSV("maze_map.csv");
    }


}
