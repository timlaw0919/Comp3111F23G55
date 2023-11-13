package Main;

import java.util.Scanner;

import FunctionA_CreateMaze.Cell;
import FunctionA_CreateMaze.MazeGUI;
import FunctionA_CreateMaze.MazeGenerator;
import FunctionA_CreateMaze.MazeLoader;
import static FunctionA_CreateMaze.CSVOutput.outputCSVFile;
import FunctionB_ShortestPath.AStarAlgorithm;
import FunctionB_ShortestPath.CSVOutput;
import FunctionB_ShortestPath.CSVOutputForGUI;
import FunctionB_ShortestPath.MazeWithShortestPathGUI;
import FunctionC_TomCatchJerry.GameMain;

public class Main{
    public static void main(String[] args) {

        int row = 30, col = 30; // Default row and col

        int choice;
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("Menu:");
            System.out.println("Press 1 to test Function A - Generate a maze map");
            System.out.println("Press 2 to test Function B - Generate shortest path");
            System.out.println("Press 3 to test Function C - Play the game");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt(); // Get the user's choice

            switch (choice){
                case 1: // Test Function A
                    MazeGenerator mazeGenerator = new MazeGenerator(row, col);
                    mazeGenerator.generateMaze();
                    // Output the maze as csv file
                    Cell[][] maze = mazeGenerator.getMaze();
                    outputCSVFile(maze, "maze_map.csv");
                    // Output GUI maze
                    MazeGUI.makeGUI(args);
                    System.exit(0);
                case 2: // Test Function B
                    // Find start & end point + Create Object
                    int[] tomLocation = {0,0};
                    int[] jerryLocation = {0,0};
                    int[][] mazeFunctionB = MazeLoader.loadMazeFromCSV("maze_map.csv");
                    for (int i = 0; i < mazeFunctionB.length; i++){
                        for (int j = 0; j < mazeFunctionB[0].length; j++){
                            if (mazeFunctionB[i][j] == 2) {
                                jerryLocation[0] = i;
                                jerryLocation[1] = j;
                            }
                            if (mazeFunctionB[i][j] == 3) {
                                tomLocation[0] = i;
                                tomLocation[1] = j;
                            }
                        }
                    }
                    AStarAlgorithm aStarObject = new AStarAlgorithm(tomLocation, jerryLocation);
                    CSVOutputForGUI.outputCSVFile(aStarObject.pathGeneratorByAStar(), "maze_map_with_path.csv");
                    MazeWithShortestPathGUI.makeGUI(args);
                    CSVOutput.outputCSVFile(aStarObject.pathGeneratorByAStar(), "path_coordinates.csv");
                    System.exit(0);
                case 3: // Test Function C
                    GameMain.main(args);
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
