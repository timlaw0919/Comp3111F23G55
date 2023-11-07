package FunctionB_ShortestPath;

import FunctionA_CreateMaze.MazeLoader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVOutputForGUI {
    public static void outputCSVFile(List<int[]> path, String filename) {
        int[][] maze = MazeLoader.loadMazeFromCSV("maze_map.csv");
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < 30 ; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 30; j++) {
                    int value = 0;
                    boolean check = false;
                    for(int[] node : path){
                        if (node[0] == i && node[1] == j && maze[i][j] != 2 && maze[i][j] != 3){
                            value = 4;
                            check = true;
                            break;
                        }
                    }
                    if (!check)
                        value = maze[i][j];
                    sb.append(value).append(",");
                }
                sb.deleteCharAt(sb.length() - 1); // Remove the trailing comma
                writer.write(sb.toString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
