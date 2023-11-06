package FunctionA_CreateMaze;


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
    }
}