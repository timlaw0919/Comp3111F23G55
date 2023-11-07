package FunctionB_ShortestPath;

import FunctionA_CreateMaze.MazeLoader;

public class Main {
    public static void main(String[] args) {

        // Find start & end point + Create Object
        int[] tomLocation = {0,0};
        int[] jerryLocation = {0,0};
        int[][] maze = MazeLoader.loadMazeFromCSV("maze_map.csv");
        for (int i = 0; i < 30; i++){
            for (int j = 0; j < 30; j++){
                if (maze[i][j] == 2) {
                    jerryLocation[0] = i;
                    jerryLocation[1] = j;
                }
                if (maze[i][j] == 3) {
                    tomLocation[0] = i;
                    tomLocation[1] = j;
                }
            }
        }
        AStarAlgorithm obj1 = new AStarAlgorithm(tomLocation, jerryLocation);

        // GUI with Shortest Path
        CSVOutputForGUI.outputCSVFile(obj1.pathGeneratorByAStar(), "maze_map_with_path.csv");
        MazeWithShortestPathGUI.makeGUI(args);

        // Shortest Path Coordinate output to csv file
        CSVOutput.outputCSVFile(obj1.pathGeneratorByAStar(), "path_coordinates.csv");
    }
}
