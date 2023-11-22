package FunctionA_CreateMaze;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The CSVOutput class is to output a CSV file representing the maze.
 */

public class CSVOutput {

    /**
     * Outputs the maze as a CSV file.
     *
     * @param maze     The maze represented as a 2D array of cells.
     * @param filename The name of the CSV file to be created.
     */
    public static void outputCSVFile(Cell[][] maze, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Cell[] row : maze) {
                StringBuilder sb = new StringBuilder();
                for (Cell cell : row) {
                    int value = cell.value.ordinal();
                    sb.append(value).append(",");
                }
                sb.deleteCharAt(sb.length() - 1); // Remove the trailing comma
                writer.write(sb.toString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {e.printStackTrace();}
    }

}