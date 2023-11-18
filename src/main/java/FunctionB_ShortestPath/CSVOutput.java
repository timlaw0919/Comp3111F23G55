package FunctionB_ShortestPath;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.*;
import java.io.*;

public class CSVOutput {
    public static void outputCSVFile(List<int[]> path, String filename) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Shortest Path (Read the coordinate line by line)");
            writer.newLine();
            writer.write("Start-> ");
            writer.write(",");
            int j = 0;
            for (int k = path.size() - 1; k >= 0; k--) {
                int[] array = path.get(k);
                for (int i = 0; i < array.length; i++) {
                    writer.write(Integer.toString(array[i]));
                    if (i == 0)
                        writer.write(';');
                }
                writer.write(',');
                if ((j + 1) % 10 == 0){
                    writer.newLine();
                    writer.write(",");
                }
                j++;
            }
            writer.write(" -> End");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
