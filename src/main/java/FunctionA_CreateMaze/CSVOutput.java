package FunctionA_CreateMaze;

import java.io.FileWriter;
import java.io.IOException;

public class CSVOutput {

    public static void outputCSVFile(Cell[][] maze, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Cell[] row : maze) {
                StringBuilder sb = new StringBuilder();
                for (Cell cell : row) {
                    int value = cell.value;
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