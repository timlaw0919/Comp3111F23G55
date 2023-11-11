package FunctionB_ShortestPath;

import FunctionA_CreateMaze.MazeLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MazeWithShortestPathGUI extends Application {
    private static final int CELL_SIZE = 10;

    @Override
    public void start(Stage primaryStage) {
        // Load the maze data from the CSV file
        int[][] mazeData = MazeLoader.loadMazeFromCSV("maze_map_with_path.csv");

        // Create a GridPane to hold the maze cells
        GridPane gridPane = new GridPane();

        // Populate the GridPane with rectangles representing the maze cells
        for (int i = 0; i < mazeData.length; i++) {
            for (int j = 0; j < mazeData[i].length; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);

                // Set the color of the cell based on the value in the maze data
                if (mazeData[i][j] == 1) {
                    cell.setFill(Color.GRAY); // Wall
                }else if(mazeData[i][j] == 0){
                    cell.setFill(Color.WHITE); // Path
                }
                else if (mazeData[i][j] == 4) {
                    cell.setFill((Color.LIGHTBLUE));
                }
                else if (mazeData[i][j] == 2){
                    cell.setFill(Color.YELLOW); // Starting/Ending Point
                }
                else
                    cell.setFill(Color.LIGHTGREEN);

                // Add the cell to the GridPane
                gridPane.add(cell, j, i);
            }
        }

        // Create the scene and set it on the stage
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze With Shortest Path GUI");
        primaryStage.show();
    }

    public static void makeGUI(String[] args) {
        launch(args);
    }

}
