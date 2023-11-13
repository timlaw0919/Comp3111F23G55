package FunctionC_TomCatchJerry;

import FunctionB_ShortestPath.AStarAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
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

import static FunctionC_TomCatchJerry.GameMazeGUI.*;
public class MainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image jerry = new Image("file:CatchesJerry.png");
        ImageView jerry_view = new ImageView(jerry);
        jerry_view.setFitHeight(146);
        jerry_view.setFitWidth(238);
        jerry_view.setLayoutX(132);
        jerry_view.setLayoutY(160);

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
        title.setLayoutY(36);
        title.setFont(new Font("Bauhaus 93",27.0));
        Pane pane = new Pane();
        pane.getChildren().addAll(jerry_view,start,mazeLength,title,sizeOfMaze);

        start.setOnAction(actionEvent -> {
            mazeSize = mazeLength.getValue();
            maze = newMaze();
            shortestPath = new AStarAlgorithm(Tom.getLocation(), Jerry.getLocation());
            GameMazeGUI gameMazeGUI = new GameMazeGUI();
            gameMazeGUI.start(stage);
        });

        Scene startPage = new Scene(pane,300,300);
        stage.setScene(startPage);
        stage.show();
    }
}
