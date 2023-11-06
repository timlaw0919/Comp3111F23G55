package FunctionA_CreateMaze;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class MazeGUI extends Application {

    private static final int CELL_SIZE = 10;

    @Override
    public void start(Stage primaryStage) {
        // Load the maze data from the CSV file
        int[][] mazeData = MazeLoader.loadMazeFromCSV("maze_map.csv");

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
                else {
                    cell.setFill(Color.RED); // Starting/Ending Point
                }

                // Add the cell to the GridPane
                gridPane.add(cell, j, i);
            }
        }

        // Create the scene and set it on the stage
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze GUI");
        primaryStage.show();
    }

    public static void makeGUI(String[] args) {
        launch(args);
    }
}
