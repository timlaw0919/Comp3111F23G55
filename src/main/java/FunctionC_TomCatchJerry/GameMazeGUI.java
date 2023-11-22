package FunctionC_TomCatchJerry;

import FunctionA_CreateMaze.constant.CellState;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static FunctionC_TomCatchJerry.Character.toIndex;
import static FunctionC_TomCatchJerry.GameMain.*;
import static java.lang.Thread.sleep;


/**
 * GameMazeGUI class sets up the interface and the background music for the game.
 * The maze of initial state will be set up in SetGridPane method.
 * When the game is started, each character (Tom and Jerry) is worked with its own thread.
 * Multithreading ensures that Tom and Jerry can move simultaneously with different speeds.
 * The updated movement of the character is shown on the screen by updateGridPane method.
 * Interactions with the player are achieved by message display and the button.
 */
public class GameMazeGUI extends Application {

    public static final int CELL_SIZE = 15;
    public int entryIndex = 0;
    public int exitIndex = 0;
    public GridPane gridPane = new GridPane();
    public List<Rectangle> cells = new ArrayList<>();
    public ImagePattern TomTom = new ImagePattern(new Image("file:tom.png"));
    public ImagePattern JerryJerry = new ImagePattern(new Image("file:Jerry.png"));
    public ImagePattern block = new ImagePattern(new Image("file:block.jpg"));

    /**
     * Set up the scene of the maze and store the spawn point of Jerry and Tom to their location respectively
     * Update the grid pane after initialising the location of Tom and Jerry
     */
    public void SetGridPane() {
        // Populate the GridPane with rectangles representing the maze cells
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);

                // Set the color of the cell based on the value in the maze data
                if (maze[i][j] == CellState.BLOCK.ordinal()) {
                    cell.setFill(block); // Block
                } else if (maze[i][j] == CellState.PATH.ordinal()) {
                    cell.setFill(Color.WHITE); // Path
                } else if (maze[i][j] == CellState.ENTRY.ordinal()) {
                    cell.setFill(Color.web("#F1CD85")); // Entry
                    Jerry.location[0] = i;
                    Jerry.location[1] = j;    // Set the spawn point of Jerry
                    entryIndex = i*maze.length + j; // Store the index of the entry point
                } else if (maze[i][j] == CellState.EXIT.ordinal()) {
                    cell.setFill(Color.web("#808990")); //Exit
                    Tom.location[0] = i;
                    Tom.location[1] = j;  // Set the spawn point of Tom
                    exitIndex = i*maze.length + j;  // Store the index of the exit point
                }
                // Add the cell to the GridPane
                gridPane.add(cell, j, i);
                cells.add(cell);    // Store the color of the cell accordingly
            }
        }
        updatedGridPane(Tom,TomTom);
        updatedGridPane(Jerry,JerryJerry);
    }

    /**
     * Update the interface of the maze game to show the object movement
     * Fill the grid where the character located with the corresponding image
     * Repaint the last position of the character back to the original color of that grid
     * If the lastPos is an BLOCK, do nothing
     * Else if the lastPos is entry point, fill the grid with #F1CD85
     * Else if the lastPos is exit point, fill the grid with #808990
     * Else, fill the grid with white color which refers to a path
     * @param c The character moving on the maze
     * @param imagePattern The image which represents the corresponding character on the maze
     */
    public void updatedGridPane(Character c, ImagePattern imagePattern) {
        if (cells.get(c.lastPos).getFill() == block || c.lastPos == 0); // Avoid refilling the BLOCK
        else if (c.lastPos == entryIndex){     // Always keep the entry point being YELLOW
            cells.get(c.lastPos).setFill(Color.web("#F1CD85"));
        }
        else if (c.lastPos == exitIndex){      // Always keep the exit point being LIGHT GREEN
            cells.get(c.lastPos).setFill(Color.web("#808990"));
        }
        else {      // Repaint the path to WHITE when character leaves
            cells.get(c.lastPos).setFill(Color.WHITE);
        }
        cells.get(toIndex(c.location)).setFill(imagePattern);     // Paint the cell when the character positions on it
    }

    /**
     * Build up the GUI for the maze game.
     * Multithreading allows different events can work on different characters concurrently.
     * The game interface has to update once the character's movement is finished.
     * The thread should keep track on the game state. When the game is ended, no further actions on the corresponding characters are allowed.
     * Home button always exist on the GUI. The game is forced to terminate if the game does not ended yet.
     * The restart button and the end game message will only appear when the game ends.
     * Reset the status of all characters and the game interface as initial state.
     * @param primaryStage The stage shown on the screen
     */
    @Override
    public void start(Stage primaryStage) {
        String musicFilePath = "TomAndJerryBackgroundMusic.mp3";
        Media media = new Media(new File(musicFilePath).toURI().toString());

        // Create a MediaPlayer with the Media object
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

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

        Pane pane = new Pane();
        Button home = new Button("Home");
        home.setLayoutX(CELL_SIZE*(mazeSize-3));
        home.setLayoutY(0);
        stackPane.setLayoutY(30);
        pane.getChildren().addAll(stackPane,home);

        Scene scene = new Scene(pane);
        KeyBoardListener JerryMove = new KeyBoardListener(Jerry);
        CheckEndGame endGame = new CheckEndGame();
        // Create a thread for Jerry and Tom respectively to make them run simultaneously
        Runnable startGame = () -> {
            Thread Player = new Thread(() -> {
                while (!endGame.isEndGame()){
                    Jerry.move();
                    updatedGridPane(Jerry, JerryJerry);
                    try {
                        sleep(Jerry.speed);} catch (InterruptedException e) {e.printStackTrace();}
                }
                if (Jerry.Game_state)  {
                    J_Win.setVisible(true);
                }
            });

            Thread Computer = new Thread(() -> {
                try {
                    sleep(1500);} catch (InterruptedException e) {throw new RuntimeException(e);}
                while (!endGame.isEndGame()){
                    Tom.MoveWithShortestPath();
                    updatedGridPane(Tom, TomTom);
                    try {
                        sleep(Tom.speed);} catch (InterruptedException e) {e.printStackTrace();}
                }
                if (Tom.Game_state) {
                    T_Win.setVisible(true);
                }
            });
            Player.start();
//        Computer starts moving when player starts to play
            scene.setOnKeyPressed(keyEvent -> {;
                JerryMove.keyPressed(keyEvent);
                if (Computer.getState() == Thread.State.NEW) {
                    Computer.start();
                }
            });
        };
        startGame.run();
//      Function of restart button
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
//      Function of Home button
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediaPlayer.stop();
                Tom.Game_state = true;
                try {
                    sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}

                Tom = new Character();
                Jerry = new Character();

                primaryStage.close();
                MainGUI mainGUI = new MainGUI();
                Stage mainStage = new Stage();
                try {
                    mainGUI.start(mainStage);} catch (IOException e) {throw new RuntimeException(e);}
            }
        });


        primaryStage.setScene(scene);
        primaryStage.setTitle("Tom n Jerry In Maze");
        primaryStage.show();


    }

}