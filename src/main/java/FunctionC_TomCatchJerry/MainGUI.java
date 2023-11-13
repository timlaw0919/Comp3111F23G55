package FunctionC_TomCatchJerry;

import FunctionB_ShortestPath.AStarAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static FunctionC_TomCatchJerry.GameMain.*;
import java.io.IOException;
import java.util.ArrayList;

import static FunctionC_TomCatchJerry.GameMazeGUI.*;
public class MainGUI extends Application {
    enum Speed {
        FAST(80),
        MODERATE(100),
        SLOW(120);
        ;
        public final int value;
        Speed(int i) {
            this.value = i;
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        Image jerry = new Image("file:CatchesJerry.png");
        ImageView jerry_view = new ImageView(jerry);
        jerry_view.setFitHeight(146);
        jerry_view.setFitWidth(238);
        jerry_view.setLayoutX(132);
        jerry_view.setLayoutY(160);

        Image tom = new Image("file:TomCatches.png");
        ImageView tom_view = new ImageView(tom);
        tom_view.setFitHeight(146);
        tom_view.setFitWidth(238);
        tom_view.setLayoutX(-56);
        tom_view.setLayoutY(-2);

        Button start = new Button("Start");
        start.setLayoutX(129);
        start.setLayoutY(208);
        Label sizeOfMaze = new Label("Size of Maze:");
        sizeOfMaze.setLayoutX(65);
        sizeOfMaze.setLayoutY(128);
        Spinner<Integer> mazeLength = new Spinner<>(30,50,30,5);

        mazeLength.setLayoutX(140);
        mazeLength.setLayoutY(125);
        mazeLength.setPrefWidth(80);
        Label title = new Label("Tom and Jerry");
        title.setLayoutX(63);
        title.setLayoutY(56);
        title.setFont(new Font("Bauhaus 93",27.0));
        ChoiceBox<Speed> Speed = new ChoiceBox<>();
        Speed.getItems().addAll(MainGUI.Speed.FAST, MainGUI.Speed.MODERATE, MainGUI.Speed.SLOW);
        Speed.setValue(MainGUI.Speed.MODERATE);
        Speed.setLayoutX(140);
        Speed.setLayoutY(160);

        Label movingSpeed = new Label("Moving Speed:");
        movingSpeed.setLayoutX(55);
        movingSpeed.setLayoutY(162);
        Pane pane = new Pane();
        pane.getChildren().addAll(jerry_view,tom_view,start,mazeLength,title,sizeOfMaze,Speed,movingSpeed);


        start.setOnAction(actionEvent -> {
            mazeSize = mazeLength.getValue();
            maze = newMaze();
            System.out.println(Speed.getValue().value);
            Tom.setSpeed(Speed.getValue().value + 120);
            Jerry.setSpeed(Speed.getValue().value +120);
            shortestPath = new AStarAlgorithm(Tom.getLocation(), Jerry.getLocation());
            GameMazeGUI gameMazeGUI = new GameMazeGUI();
            gameMazeGUI.start(stage);
        });

        Scene startPage = new Scene(pane,300,300);
        stage.setScene(startPage);
        stage.show();
    }
}
