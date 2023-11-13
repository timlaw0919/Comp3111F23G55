package FunctionC_TomCatchJerry;

import FunctionA_CreateMaze.constant.CellState;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static FunctionC_TomCatchJerry.Character.toIndex;
import static FunctionC_TomCatchJerry.GameMain.*;
import static java.lang.Thread.sleep;


public class GameMazeGUI extends Application {

    private static final int CELL_SIZE = 15;
    private int entryIndex = 0;
    private int exitIndex = 0;
    GridPane gridPane = new GridPane();
    List<Rectangle> cells = new ArrayList<>();
    ImagePattern TomTom = new ImagePattern(new Image("file:tom.png"));
    ImagePattern JerryJerry = new ImagePattern(new Image("file:Jerry.png"));

    /**
     * Set up the scene of the maze and the spawn point of Jerry and Tom
     */
    public void SetGridPane() {
        // Populate the GridPane with rectangles representing the maze cells
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
    // HAHA
                // Set the color of the cell based on the value in the maze data
                if (maze[i][j] == CellState.BLOCK.ordinal()) {
                    cell.setFill(Color.web("#C0C0C0")); // Block
                } else if (maze[i][j] == CellState.PATH.ordinal()) {
                    cell.setFill(Color.WHITE); // Path
                } else if (maze[i][j] == CellState.ENTRY.ordinal()) {
                    cell.setFill(Color.web("#F1CD85")); // Entry
                    Jerry.setLocation(i, j);    // Set the spawn point of Jerry
                    entryIndex = i*maze.length + j; // Store the index of the entry point
                } else if (maze[i][j] == CellState.EXIT.ordinal()) {
                    cell.setFill(Color.web("#808990")); //Exit
                    Tom.setLocation(i, j);  // Set the spawn point of Tom
                    exitIndex = i*maze.length + j;  // Store the index of the exit point
                }
                // Add the cell to the GridPane
                gridPane.add(cell, j, i);
                cells.add(cell);    // Store the color of the cell accordingly
            }
        }
        updatedGridPane(Tom,TomTom);
        updatedGridPane(Jerry,JerryJerry);
        Tom.setSpeed(200);  // Set the speed of Tom
        Jerry.setSpeed(100);    // Set the speed of Jerry
    }

    /**
     * Update the appearance of the maze to show the object movement
     * @param c The character moving on the maze
     * @param imagePattern The color which represents the corresponding character on the maze
     */
    public void updatedGridPane(Character c, ImagePattern imagePattern) {
        if (cells.get(c.getLastPos()).getFill() == Color.web("#C0C0C0") || c.getLastPos() == 0); // Avoid refilling the BLOCK
        else if (c.getLastPos() == entryIndex){     // Always keep the entry point being YELLOW
            cells.get(c.getLastPos()).setFill(Color.web("#F1CD85"));
        }
        else if (c.getLastPos() == exitIndex){      // Always keep the exit point being LIGHT GREEN
            cells.get(c.getLastPos()).setFill(Color.web("#808990"));
        }
        else {      // Repaint the path to WHITE when character leaves
            cells.get(c.getLastPos()).setFill(Color.WHITE);
        }
        cells.get(toIndex(c.getLocation())).setFill(imagePattern);     // Paint the cell when the character positions on it
    }

    /**
     * Setup the ...
     * @param primaryStage The designed ...
     */
    @Override
    public void start(Stage primaryStage) {
//        String musicFile = "path/to/music.mp3"; // Provide the path to your audio file

        SetGridPane();
        updatedGridPane(Jerry, JerryJerry);
        updatedGridPane(Tom, TomTom);
        // Create the scene and set it on the stage
        StackPane stackPane = new StackPane();
        Label TomWin = new Label("You Are Caught by Tom!");
        Label JerryWin = new Label("Successfully Escape!");
        TomWin.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        JerryWin.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");


        Button reT = new Button("Restart");
        Button reJ = new Button("Restart");
        VBox T_Win = new VBox(20,TomWin,reT);
        VBox J_Win = new VBox(20,JerryWin,reJ);
        T_Win.setAlignment(Pos.CENTER);
        J_Win.setAlignment(Pos.CENTER);

        T_Win.setVisible(false);
        J_Win.setVisible(false);
        stackPane.getChildren().addAll(gridPane,T_Win,J_Win);

        Scene scene = new Scene(stackPane);
        KeyBoardListener JerryMove = new KeyBoardListener(Jerry);
        CheckEndGame endGame = new CheckEndGame(Tom, Jerry);
        // Create a thread for Jerry and Tom respectively to make them run simultaneously
        Runnable startGame = () -> {
            Thread Player = new Thread(() -> {
                while (!endGame.isEndGame()){
                    PlayerMove();
                }
                if (Jerry.getGame_state())  J_Win.setVisible(true);
            });

            Thread Computer = new Thread(() -> {
                while (!endGame.isEndGame()){
                    ComputerMove();
                }
                if (Tom.getGame_state()) T_Win.setVisible(true);
            });
            Player.start();
//        Computer starts moving when player starts to play
            scene.setOnKeyPressed(keyEvent -> {;
                JerryMove.keyPressed(keyEvent);
                if (Computer.getState() == Thread.State.NEW) Computer.start();
            });
        };
        startGame.run();
        EventHandler handler = event -> {
            T_Win.setVisible(false);
            J_Win.setVisible(false);

            Jerry.reset(entryIndex/maze.length,entryIndex%maze.length);
            Tom.reset(exitIndex/maze.length,exitIndex%maze.length);
            updatedGridPane(Jerry, JerryJerry);
            updatedGridPane(Tom, TomTom);
            startGame.run();
        };
        reT.setOnAction(handler);
        reJ.setOnAction(handler);



        primaryStage.setScene(scene);
        primaryStage.setTitle("Tom n Jerry In Maze");
        primaryStage.show();


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
        updatedGridPane(Jerry, JerryJerry);
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
        updatedGridPane(Tom, TomTom);
        pauser(Tom);
    }

    public static void makeGUI(String[] args) {
        launch(args);
    }

}
