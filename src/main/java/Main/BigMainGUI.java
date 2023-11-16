package Main;

import FunctionA_CreateMaze.*;
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
                FunctionA_CreateMaze.Main.main();
                mazeGUI.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        functionBButton.setOnAction(actionEvent -> {
            MazeWithShortestPathGUI mazeWithShortestPathGUI = new MazeWithShortestPathGUI();
            try {
                FunctionB_ShortestPath.Main.main();
                mazeWithShortestPathGUI.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        functionCButton.setOnAction(actionEvent -> {
            FunctionC_TomCatchJerry.MainGUI mainGUI = new MainGUI();
            try {
                mainGUI.start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        exitButton.setOnAction(actionEvent -> System.exit(0));

        Scene startPage = new Scene(pane,600,300);
        stage.setScene(startPage);
        stage.setTitle("Testing Menu");
        stage.show();
    }
}