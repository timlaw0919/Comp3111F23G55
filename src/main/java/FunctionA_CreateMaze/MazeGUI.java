package FunctionA_CreateMaze;

import FunctionA_CreateMaze.constant.CellState;
import Main.BigMainGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
                if (mazeData[i][j] == CellState.BLOCK.ordinal()) {
                    cell.setFill(Color.GRAY); // Block
                }
                else if(mazeData[i][j] == CellState.PATH.ordinal()){
                    cell.setFill(Color.WHITE); // Path
                }
                else if(mazeData[i][j]==CellState.ENTRY.ordinal()){
                    cell.setFill(Color.YELLOW); // Entry
                }
                else if(mazeData[i][j]==CellState.EXIT.ordinal()){
                    cell.setFill(Color.LIGHTGREEN); //Exit
                }

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
        primaryStage.setTitle("Maze GUI");
        primaryStage.show();
    }
}
