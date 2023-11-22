package Main;
import FunctionA_CreateMaze.*;
import static FunctionA_CreateMaze.CSVOutput.outputCSVFile;
import FunctionB_ShortestPath.AStarAlgorithm;
import FunctionB_ShortestPath.CSVOutputForGUI;
import FunctionB_ShortestPath.MazeWithShortestPathGUI;
import FunctionC_TomCatchJerry.MainGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The BigMainGUI class is the main entry point for the G55 Tom and Jerry Maze Game Testing Menu.
 * It extends the Application class from JavaFX and provides the GUI functionality for the testing menu.
 * The testing menu allows users to generate a new maze, display the shortest path in the maze, play the game, and exit the application by clicking the corresponding button.
 * Users can test different functions related to maze generation, shortest path finding, and playing the game.
 */

public class BigMainGUI extends Application {
    @Override
    public void start (Stage stage) throws Exception{
        Pane pane = new Pane();

        Image tomAndJerryHead = new Image("file:TomAndJerryHead.png");
        ImageView tomAndJerryHeadView = new ImageView(tomAndJerryHead);
        tomAndJerryHeadView.setFitHeight(91.2);
        tomAndJerryHeadView.setFitWidth(109.4);
        tomAndJerryHeadView.setLayoutX(470);
        tomAndJerryHeadView.setLayoutY(200);

        Rectangle box = new Rectangle(600, 300);
        box.setFill(Color.web("#FFC97A"));
        box.setStroke(Color.web("#F4A594"));
        box.setStrokeWidth(15);
        box.setArcHeight(50);
        box.setArcWidth(50);

        Label welcomeLabel = new Label("Welcome to G55 Tom and Jerry Maze Game Testing Menu!");
        welcomeLabel.setId("welcomeLabel");
        welcomeLabel.setFont(new Font("Jokerman", 20));
        welcomeLabel.setTextFill(Color.web("#e61c1c"));
        welcomeLabel.setLayoutX(10);
        welcomeLabel.setLayoutY(15);

        Label generateMazeLabel = new Label("Generating a new maze -> ");
        generateMazeLabel.setFont(new Font("Papyrus", 20));
        generateMazeLabel.setTextFill(Color.web("#1e4631"));
        generateMazeLabel.setLayoutX(10);
        generateMazeLabel.setLayoutY(50);

        Label generateShortestPathMazeLabel = new Label("Showing the shortest path from Entry to Exit -> ");
        generateShortestPathMazeLabel.setFont(new Font("Chiller", 30));
        generateShortestPathMazeLabel.setTextFill(Color.web("#1e4631"));
        generateShortestPathMazeLabel.setLayoutX(10);
        generateShortestPathMazeLabel.setLayoutY(110);

        Label playTheGameLabel = new Label("Having Fun by playing the game -> ");
        playTheGameLabel.setFont(new Font("Stencil", 20));
        playTheGameLabel.setTextFill(Color.web("#1e4631"));
        playTheGameLabel.setLayoutX(10);
        playTheGameLabel.setLayoutY(180);

        Label exitLabel = new Label("Exit -> ");
        exitLabel.setFont(new Font("Harrington", 20));
        exitLabel.setTextFill(Color.web("#1e4631"));
        exitLabel.setLayoutX(10);
        exitLabel.setLayoutY(240);

        Button functionAButton = new Button("Test Function A");
        functionAButton.setLayoutX(240);
        functionAButton.setLayoutY(60);

        Button functionBButton = new Button("Test Function B");
        functionBButton.setLayoutX(450);
        functionBButton.setLayoutY(120);

        Button functionCButton = new Button("Test Function C");
        functionCButton.setLayoutX(370);
        functionCButton.setLayoutY(180);

        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(70);
        exitButton.setLayoutY(240);

        pane.getChildren().addAll(box, welcomeLabel, generateMazeLabel, generateShortestPathMazeLabel,
        playTheGameLabel, exitLabel, functionAButton, functionBButton, functionCButton, exitButton,
        tomAndJerryHeadView);

        // Set the Click action
        functionAButton.setOnAction(actionEvent -> {
            FunctionA_CreateMaze.MazeGUI mazeGUI = new MazeGUI();
            try {
                int rows = 30; // Number of rows in the maze
                int cols = 30; // Number of columns in the maze
                // Generator the maze
                MazeGenerator mazeGenerator = new MazeGenerator(rows, cols);
                mazeGenerator.generateMaze();
                // Output the maze as csv file
                Cell[][] maze = mazeGenerator.getMaze();
                outputCSVFile(maze, "maze_map.csv");
                mazeGUI.start(stage);} catch (Exception e) {throw new RuntimeException(e);}
        });
        functionBButton.setOnAction(actionEvent -> {
            MazeWithShortestPathGUI mazeWithShortestPathGUI = new MazeWithShortestPathGUI();
            try {
                int[] tomLocation = {0,0};
                int[] jerryLocation = {0,0};
                int[][] maze = MazeLoader.loadMazeFromCSV("maze_map.csv");
                for (int i = 0; i < 30; i++){
                    for (int j = 0; j < 30; j++){
                        if (maze[i][j] == 2) {
                            jerryLocation[0] = i;
                            jerryLocation[1] = j;
                        }
                        if (maze[i][j] == 3) {
                            tomLocation[0] = i;
                            tomLocation[1] = j;
                        }
                    }
                }
                AStarAlgorithm obj1 = new AStarAlgorithm(tomLocation, jerryLocation, "maze_map.csv");
                CSVOutputForGUI.outputCSVFile(obj1.pathGeneratorByAStar(), "maze_map_with_path.csv", "maze_map.csv");
                FunctionB_ShortestPath.CSVOutput.outputCSVFile(obj1.pathGeneratorByAStar(), "path_coordinates.csv");
                mazeWithShortestPathGUI.start(stage);} catch (Exception e) {throw new RuntimeException(e);}
        });
        functionCButton.setOnAction(actionEvent -> {
            FunctionC_TomCatchJerry.MainGUI mainGUI = new MainGUI();
            try {
                mainGUI.start(stage);} catch (IOException e) {throw new RuntimeException(e);}
        });
        exitButton.setOnAction(actionEvent -> System.exit(0));

        Scene startPage = new Scene(pane,600,300);
        stage.setScene(startPage);
        stage.setTitle("Testing Menu");
        stage.show();
    }
}