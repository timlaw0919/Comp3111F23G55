package FunctionA_CreateMaze;

import javafx.stage.Stage;

import static FunctionA_CreateMaze.CSVOutput.outputCSVFile;
import static FunctionA_CreateMaze.MazeGUI.makeGUI;

public class Main {

    public static void main(String[] args) {
        int rows = 30; // Number of rows in the maze
        int cols = 30; // Number of columns in the maze

        MazeGenerator mazeGenerator = new MazeGenerator(rows, cols);
        mazeGenerator.generateMaze();

        Cell[][] maze = mazeGenerator.getMaze();

        outputCSVFile(maze, "maze_map.csv");

        MazeGUI mazeGUI = new MazeGUI();
        MazeGUI.makeGUI(args);
    }
}
