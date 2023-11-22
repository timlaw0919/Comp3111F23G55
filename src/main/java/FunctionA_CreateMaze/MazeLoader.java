package FunctionA_CreateMaze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The MazeLoader class output the int array version of the maze which is loaded from the CSV file created by CSVOutput class
 *
 * @see CSVOutput
 */
public class MazeLoader {
    /**
     * Loads a maze from a CSV file.
     *
     * @param filePath The path of the CSV file to load.
     * @return A 2D array representing the loaded maze.
     */
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
        } catch (IOException e) {e.printStackTrace();}

        return maze;
    }
}