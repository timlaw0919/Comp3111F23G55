package Main;

import FunctionA_CreateMaze.Cell;
import FunctionA_CreateMaze.MazeGUI;
import FunctionA_CreateMaze.MazeGenerator;
import FunctionA_CreateMaze.MazeLoader;
import FunctionB_ShortestPath.AStarAlgorithm;
import FunctionB_ShortestPath.CSVOutput;
import FunctionB_ShortestPath.CSVOutputForGUI;
import FunctionB_ShortestPath.MazeWithShortestPathGUI;

import static FunctionA_CreateMaze.CSVOutput.outputCSVFile;

public class Main {
    public static void main(String[] args) {
        int rows = 30; // Number of rows in the maze
        int cols = 30; // Number of columns in the maze

        // Generator the maze
        MazeGenerator mazeGenerator = new MazeGenerator(rows, cols);
        mazeGenerator.generateMaze();

        // Output the maze as csv file
        Cell[][] maze = mazeGenerator.getMaze();
        outputCSVFile(maze, "maze_map.csv");

        // Output GUI maze
        MazeGUI.makeGUI(args);

        // Find start & end point + Create Object
        int[] tomLocation = {mazeGenerator.EntryPoint.row,mazeGenerator.EntryPoint.col};
        int[] jerryLocation = {mazeGenerator.ExitPoint.row,mazeGenerator.ExitPoint.col};

        AStarAlgorithm obj1 = new AStarAlgorithm(tomLocation, jerryLocation);

//        // GUI with Shortest Path
//        CSVOutputForGUI.outputCSVFile(obj1.pathGeneratorByAStar(), "maze_map_with_path.csv");
//        MazeWithShortestPathGUI.makeGUI(args);
//
//        // Shortest Path Coordinate output to csv file
//        CSVOutput.outputCSVFile(obj1.pathGeneratorByAStar(), "path_coordinates.csv");
    }
}
