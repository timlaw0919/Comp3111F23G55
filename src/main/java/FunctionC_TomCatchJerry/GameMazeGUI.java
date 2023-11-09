package FunctionC_TomCatchJerry;

import FunctionA_CreateMaze.constant.CellState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static FunctionC_TomCatchJerry.Character.toIndex;
import static FunctionC_TomCatchJerry.GameMain.*;
import static java.lang.Thread.sleep;


public class GameMazeGUI extends Application {

    private static final int CELL_SIZE = 10;
    private int entryIndex = 0;
    private int exitIndex = 0;
    GridPane gridPane = new GridPane();
    List<Rectangle> cells = new ArrayList<>();

    /**
     * Set up the scene of the maze and the spawn point of Jerry and Tom
     */
    public void SetGridPane() {
        // Populate the GridPane with rectangles representing the maze cells
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);

                // Set the color of the cell based on the value in the maze data
                if (maze[i][j] == CellState.BLOCK.ordinal()) {
                    cell.setFill(Color.GRAY); // Block
                } else if (maze[i][j] == CellState.PATH.ordinal()) {
                    cell.setFill(Color.WHITE); // Path
                } else if (maze[i][j] == CellState.ENTRY.ordinal()) {
                    cell.setFill(Color.YELLOW); // Entry
                    Jerry.setLocation(i, j);    // Set the spawn point of Jerry
                    entryIndex = i*maze.length + j; // Store the index of the entry point
                } else if (maze[i][j] == CellState.EXIT.ordinal()) {
                    cell.setFill(Color.LIGHTGREEN); //Exit
                    Tom.setLocation(i, j);  // Set the spawn point of Tom
                    exitIndex = i*maze.length + j;  // Store the index of the exit point
                }
                // Add the cell to the GridPane
                gridPane.add(cell, j, i);
                cells.add(cell);    // Store the color of the cell accordingly
            }
        }
        Tom.setSpeed(300);  // Set the speed of Tom
        Jerry.setSpeed(100);    // Set the speed of Jerry
    }

    /**
     * Update the appearance of the maze to show the object movement
     * @param c The character moving on the maze
     * @param color The color which represents the corresponding character on the maze
     */
    public void updatedGridPane(Character c, Color color) {
        if (cells.get(c.getLastPos()).getFill() == Color.GRAY); // Avoid refilling the BLOCK
        else if (c.getLastPos() == entryIndex){     // Always keep the entry point being YELLOW
            cells.get(c.getLastPos()).setFill(Color.YELLOW);
        }
        else if (c.getLastPos() == exitIndex){      // Always keep the exit point being LIGHT GREEN
            cells.get(c.getLastPos()).setFill(Color.LIGHTGREEN);
        }
        else {      // Repaint the path to WHITE when character leaves
            cells.get(c.getLastPos()).setFill(Color.WHITE);
        }
        cells.get(toIndex(c.getLocation())).setFill(color);     // Paint the cell when the character positions on it
    }

    /**
     * Setup the ...
     * @param primaryStage The designed ...
     */
    @Override
    public void start(Stage primaryStage) {

        SetGridPane();

        // Create the scene and set it on the stage
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tom n Jerry In Maze");
        primaryStage.show();
        KeyBoardListener JerryMove = new KeyBoardListener(Jerry);
        scene.setOnKeyPressed(JerryMove::keyPressed);
        CheckEndGame endGame = new CheckEndGame(Tom, Jerry);
        // Create a thread for Jerry and Tom respectively to make them run simultaneously
        Thread Player = new Thread(() -> {
            while (true){
                PlayerMove();
                if (endGame.isEndGame()) while (true) pauser(Jerry); // Stop the movement of Jerry when the game is ended
            }
        });
        Thread Computer = new Thread(() -> {
            while (true){
                ComputerMove();
                if (endGame.isEndGame()) while (true) pauser(Jerry); // Stop the movement of Tom when the game is ended
            }
        });
        // Start the thread
        Player.start();
        Computer.start();

    }

    /**
     * Set the speed for the corresponding character
     * @param character
     */
    private void pauser(Character character){
        try {
            sleep(character.getSpeed());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Player movement in the maze
     * 1. Jerry moves under player's control
     * 2. Update the maze for the movement
     * 3. Control the speed of the movement of Jerry
     */
    private void PlayerMove(){
        Jerry.move();
        updatedGridPane(Jerry, Color.YELLOWGREEN);
        pauser(Jerry);
    }

    /**
     * Computer movement in the maze
     * 1. Tom moves under computer's control
     * 2. Update the maze for the movement
     * 3. Control the speed of the movement of Tom
     */
    private void ComputerMove(){
        Tom.MoveWithShortestPath();
        updatedGridPane(Tom, Color.BLUEVIOLET);
        pauser(Tom);
    }

    public static void makeGUI(String[] args) {
        launch(args);
    }

}
