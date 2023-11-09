package FunctionC_TomCatchJerry;

import FunctionA_CreateMaze.Cell;
import FunctionA_CreateMaze.MazeGenerator;
import FunctionA_CreateMaze.MazeLoader;
import FunctionB_ShortestPath.AStarAlgorithm;

import static FunctionA_CreateMaze.CSVOutput.outputCSVFile;


public class GameMain {
    public static final int[][] maze = newMaze();       // Maze
    public static Character Tom = new Character();      // Computer
    public static Character Jerry = new Character();    // Player
    public static AStarAlgorithm shortestPath = new AStarAlgorithm(Tom.getLocation(), Jerry.getLocation());    // Algorithm for finding shortest Path

    public static void main(String[] args) {

        GameMazeGUI.makeGUI(args);
    }

    public static int[][] newMaze(){
        int rows = 30; // Number of rows in the maze
        int cols = 30; // Number of columns in the maze

        // Generator the maze
        MazeGenerator mazeGenerator = new MazeGenerator(rows, cols);
        mazeGenerator.generateMaze();

        // Output the maze as csv file
        Cell[][] Maze = mazeGenerator.getMaze();
        outputCSVFile(Maze, "maze_map.csv");
        return MazeLoader.loadMazeFromCSV("maze_map.csv");
    }
}
