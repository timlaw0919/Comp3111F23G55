package FunctionB_ShortestPath;

import FunctionA_CreateMaze.MazeLoader;
import  Main.BigMainGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * The MazeWithShortestPathGUI class is a JavaFX application that displays a maze with the shortest path highlighted.
 * It loads the maze data from a CSV file (read by Function A's MazeLoader), creates a graphical representation of the maze, and colors the cells.
 * The class provides a GUI interface with a GridPane to hold the maze cells and a button to navigate back to the testing menu.
 *
 * @see FunctionB_ShortestPath.AStarAlgorithm
 * @see FunctionA_CreateMaze.MazeLoader
 * @see Main.BigMainGUI
 */

public class MazeWithShortestPathGUI extends Application {
    private static final int CELL_SIZE = 10;

    @Override
    public void start(Stage primaryStage) throws Exception {
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
                    cell.setFill(Color.WHITE); // Movable Cell
                }
                else if (mazeData[i][j] == 4) {
                    cell.setFill((Color.LIGHTBLUE)); // Path
                }
                else if (mazeData[i][j] == 2){
                    cell.setFill(Color.YELLOW); // Starting Point
                }
                else
                    cell.setFill(Color.LIGHTGREEN); // Ending Point

                // Add the cell to the GridPane
                gridPane.add(cell, j, i);
            }
        }

        Pane pane = new Pane();
        Button backTestingMenuButton = new Button("Back to Testing Menu");
        backTestingMenuButton.setLayoutX(CELL_SIZE * (mazeData.length ) + 10);
        backTestingMenuButton.setLayoutY(125);
        pane.getChildren().addAll(gridPane, backTestingMenuButton);

        backTestingMenuButton.setOnAction(actionEvent -> {
            BigMainGUI bigMainGUI = new BigMainGUI();
            try {
                bigMainGUI.start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Create the scene and set it on the stage
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze With Shortest Path GUI");
        primaryStage.show();
    }
}
