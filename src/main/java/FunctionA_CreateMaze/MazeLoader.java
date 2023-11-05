package FunctionA_CreateMaze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MazeLoader {
    public static int[][] loadMazeFromCSV(String filePath) {
        int[][] maze = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int rowCount = 0;
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");
                if (maze == null) {
                    maze = new int[cells.length][cells.length];
                }
                for (int colCount = 0; colCount < cells.length; colCount++) {
                    maze[rowCount][colCount] = Integer.parseInt(cells[colCount]);
                }
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maze;
    }
}