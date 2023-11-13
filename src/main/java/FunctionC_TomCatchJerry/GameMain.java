package FunctionC_TomCatchJerry;

import FunctionA_CreateMaze.Cell;
import FunctionA_CreateMaze.MazeGenerator;
import FunctionA_CreateMaze.MazeLoader;
import FunctionB_ShortestPath.AStarAlgorithm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static FunctionA_CreateMaze.CSVOutput.outputCSVFile;


public class GameMain {
    public static int mazeSize = 30;
    public static int[][] maze = newMaze();       // Maze
    public static Character Tom = new Character();      // Computer
    public static Character Jerry = new Character();    // Player
    public static AStarAlgorithm shortestPath = new AStarAlgorithm(Tom.getLocation(), Jerry.getLocation());    // Algorithm for finding shortest Path

//    public static void main(String[] args) {
//
//        GameMazeGUI.makeGUI(args);
//    }

    /**
     * Generate a new maze before the game starts
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
