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
import static FunctionC_TomCatchJerry.Game.*;
import static java.lang.Thread.sleep;


public class GameMazeGUI extends Application {

    private static final int CELL_SIZE = 10;
    private int entryindex = 0;
    private int exitindex = 0;
    GridPane gridPane = new GridPane();
    List<Rectangle> cells = new ArrayList<>();
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
                    Jerry.setLocation(i, j);
                    entryindex = i*30 + j;
                } else if (maze[i][j] == CellState.EXIT.ordinal()) {
                    cell.setFill(Color.LIGHTGREEN); //Exit
                    Tom.setLocation(i, j);
                    exitindex = i*30 + j;
                }
                // Add the cell to the GridPane
                gridPane.add(cell, j, i);
                cells.add(cell);
            }
        }
        Tom.setSpeed(500);
        Jerry.setSpeed(100);
    }
    public void updatedGridPane(Character c, Color color) {
        if (cells.get(c.getLastPos()).getFill() == Color.GRAY);
        else if (c.getLastPos() == entryindex){
            cells.get(c.getLastPos()).setFill(Color.YELLOW);
        }
        else if (c.getLastPos() == exitindex){
            cells.get(c.getLastPos()).setFill(Color.LIGHTGREEN);
        }
        else {
            cells.get(c.getLastPos()).setFill(Color.WHITE);
        }
        cells.get(toIndex(c.getLocation())).setFill(color);
    }

    @Override
    public void start(Stage primaryStage) {

        SetGridPane();

        // Create the scene and set it on the stage
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze GUI");
        primaryStage.show();
        KeyBoardListener JerryMove = new KeyBoardListener(Jerry);
        scene.setOnKeyPressed(JerryMove::keyPressed);
        CheckEndGame endGame = new CheckEndGame(Tom, Jerry);

        Thread Player = new Thread(() -> {
            while (true){
                PlayerMove();
                if (endGame.isEndGame()) while (true) pauser(Jerry);

            }
        });
        Thread Computer = new Thread(() -> {
            while (true){
                ComputerMove();
                if (endGame.isEndGame()) while (true) pauser(Jerry);
            }
        });

        Player.start();
        Computer.start();


    }
    private void pauser(Character character){
        try {
            sleep(character.getSpeed());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void startGameLoop(){
        Jerry.move();
        updatedGridPane(Jerry,Color.YELLOWGREEN);
        pauser(Jerry);
        Tom.MoveWithShortestPath();
        updatedGridPane(Tom,Color.BLUEVIOLET);
        pauser(Tom);

    }
    private void PlayerMove(){
        Jerry.move();
        updatedGridPane(Jerry, Color.YELLOWGREEN);
        pauser(Jerry);
    }
    private void ComputerMove(){
        Tom.MoveWithShortestPath();
        updatedGridPane(Tom, Color.BLUEVIOLET);
        pauser(Tom);
    }

    public static void makeGUI(String[] args) {
        launch(args);
    }

}
